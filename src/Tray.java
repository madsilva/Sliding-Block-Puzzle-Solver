import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author madsilva
 * @author jgeati
 */

/* Design of Tray:
The Tray class holds data for the game board in 2 data structures. The first is a 2D integer array of 0s and 1s representing where blocks are and are not.
This allows us to easily find empty space where potential moves could be made.
The second is a HashMap where Coord objects, which hold row and column coords, are keys to each Block object on the tray. This allows for easy lookup of blocks
by their coordinates when it's time to move them.
Goal trays are represented as Trays because of the convenience of the preexisting addBlock method and Block map.  
*/

/*CURRENT ERRORS:
see comments in recurse
*/

/*
some possible effciency improvements for the future when everything isnt fucked
clear data of parent trays once all children are generated and its clear the parent isnt the goal
change our tray 2d array to a bitset
if we could find out some way to calculate degrees of similarity/closeness to the solution, we could implement a priority queue for child nodes?
*/

public class Tray {
    private static int rows, cols;
    private int[][] tray;
    private int[][] lastIsOK;
    private HashMap<Coord, Block> blocks;
    private ArrayList<int[]> appliedMoves;
    private static HashSet<Tray> visited;
    
    // This constructor is used to intialize the "mother tray", which holds the inital configuration
    public Tray(int r, int c) {
        rows = r;
        cols = c;
        // note that tray is already filled with 0s
        tray = new int[r][c]; 
        blocks = new HashMap();
        appliedMoves = new ArrayList();
        visited = new HashSet();
    }
    
    // making a tray that is 1 move different than a parent tray for  
    // creating a tree of trays
    public Tray(Tray parent, int[] move) {
        blocks = new HashMap();
        for (Coord c : parent.blocks.keySet()) {
            Coord newCoord = c.copy();
            Block newBlock = parent.blocks.get(c).copy();
            blocks.put(newCoord, newBlock);
        }
        // moveblock creates the new tray
        moveBlock(move);
        appliedMoves = new ArrayList(parent.appliedMoves);
        appliedMoves.add(move);
    }
    
    // this method returns a list of moves to solve the tray given a goal
    // returns empty list if the tray is unsolvable
    // Moves are represented as int arrays and are formatted:
    // start row, start col, end row, end col
    public ArrayList<int[]> solve(Tray goal) {
        TreeNode<Tray> root = new TreeNode(this);
        visited.add(this);
        TreeNode<Tray> goalNode = recurse(root, goal, 0);
        if (goalNode == null) {
            return new ArrayList();
        }
        return goalNode.getData().getAppliedMoves();
    }
    

    private TreeNode recurse(TreeNode<Tray> n, Tray goal, int c) {
        // if the node contains a tray that meets the goal, return the node
        if (n.getData().checkGoal(goal)) {
            System.out.println("fucking hell");
            return n;
        }
        // i hope its okay that i removed the else here, i dont think its needed
        //System.out.println("parent tray with # of parents: " + c + " " + "hash: " + n.getData().hashCode()+ " " + n.getData().printTray());
        ArrayList<int[]> possibleMoves = (n.getData().findMoves());
        for (int[] m : possibleMoves) {
            Tray possibleChild = new Tray(n.getData(), m);
            //System.out.println("potential child with hash: " + newTray.hashCode() + " " + newTray.printTray());
            if (visited.add(possibleChild)) {
                //System.out.println("child with hash: " + possibleChild.hashCode() + " " + possibleChild.printTray());
                n.addChild(possibleChild);
                // adding the below if statement makes it work for SOME boards, not all
                // although this is probably good because it saves time to check all generated children if they are the goal immedeately 
                // instead of waiting till they come up in the stack
                
                // otherwise the issue is that trays that satify the goal are being looked at, and checked ("fucking hell" prints) but for some reason null is still getting returned???
                // and i really dont know why at this point
                
                // note that when i drew the tree it was making, it seemed to be doing a depth first traversal- dont know if this is desireable or not
                 if (possibleChild.checkGoal(goal)) {
                    return new TreeNode(possibleChild);
                }
            }
        }
        ArrayList<TreeNode> nodes = n.getChildren();
        
            for (TreeNode t : nodes) {
                TreeNode returned = recurse(t, goal, c+1);
                if (returned != null) return returned;
            }
        //System.out.println("Reaching null");
        return null;
    }
    
    @Override
    public int hashCode() {
        return blocks.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj==this) {
            return true;
        }
        if (obj==null || !(obj instanceof Tray)) {
            return false;
        }
        Tray other = (Tray)obj;
        return other.blocks.equals(this.blocks);
    }
     
    // this method returns an arraylist of every possible move for each block
    private ArrayList<int[]> findMoves() {
        ArrayList<int[]> moves = new ArrayList();
        for (Block b : blocks.values()) {
            moves.addAll(b.findMoves(tray));
        }
        return moves;
    }
    
    private void moveBlock(int[] move) {
        // removing the block to be moved from the map so we can update its coords
        // and return it with an updated key
        Block b = blocks.remove(new Coord(move[0], move[1]));
        b.setRowColPos(move[2], move[3]);
        blocks.put(new Coord(move[2], move[3]), b);
        // updating the tray
        if (isOk()) {
            tray = lastIsOK;
        }
        else {
            blocks.remove(new Coord(move[2], move[3]));
        }
    }
    
    // this method checks if the tray satisfies a given goal, ie the blocks in
    // the goal tray are in the correct configuration in the game tray
    // this does NOT check if two trays are equal
    public boolean checkGoal(Tray goal) {
        // go thru this.blocks and then go thru goal and see if each block in goal matches a block in this
        for (Block g : goal.blocks.values()) {
            Coord key = new Coord(g.getRowPos(), g.getColPos());
            Block compBlock = this.blocks.get(key);
            if (!g.equals(compBlock)) {
                return false;
            }
        }
        return true;
    }

    // looks at the current block map of the tray and checks if there is overlap 
    // by creating a 2d array of 0s and attempting to add blocks as 1s - if there 
    // is more than one 1 in a space, the configuration is invalid.
    public boolean isOk(){
        int[][] newTray = new int[rows][cols];
        for (Block b : blocks.values()) {
            for (int r = b.getRowPos(); r<b.getRowPos()+b.getRows(); r++) {
                for (int c = b.getColPos(); c<b.getColPos()+b.getCols(); c++) {
                    if (newTray[r][c]==1) {
                        return false;
                    }
                    newTray[r][c] = 1;
                }
            }
        }
        lastIsOK = newTray;
        return true;
    }
    
    // Takes a line of input* representing a block and adds it to the tray.
    // Returns true or false if the block was successfully added.
    // If new block overlaps with other blocks it will not be added.
    // If any of the given block values are invalid it will not be added.
    // *Block data input format: rows, columns, upper left row, upper left column
    public boolean addBlock(String line) {
        String [] vals = line.split(" ");
        // how to bulk convert string ary to int? this is so ugly
        int rows = Integer.parseInt(vals[0]);
        int cols = Integer.parseInt(vals[1]);
        int rowsPos = Integer.parseInt(vals[2]);
        int colsPos = Integer.parseInt(vals[3]);

        // create block and add it to the map
        Block b = new Block(rows, cols, rowsPos, colsPos);
        Coord bCoord = new Coord(rowsPos, colsPos);
        blocks.put(bCoord, b);
        if (!isOk()) {
            blocks.remove(bCoord);
            return false;
        }
        tray = lastIsOK;
        return true;
    }
    
    // this is prob a good idea for debugging - will not need for final
    public String printTray() {
        String output = "Current game tray: \n";
        for (int r=0; r<rows;r++) {
            for (int c=0; c<cols; c++) {
                output += tray[r][c] + " ";
            }
            output += "\n";
        }
        return output;
    }
    
    public String printGoal() {
        String output = "Goal tray: \n";
        output += hashCode() + "\n";
        for (int r=0; r<rows;r++) {
            for (int c=0; c<cols; c++) {
                output += tray[r][c] + " ";
            }
            output += "\n";
        }
        return output;
    }
    
    public ArrayList<int[]> getAppliedMoves() {
        return appliedMoves;
    }    
    
    public HashMap<Coord, Block> getBlocks() {
        return blocks;
    }
    
    public int[][] getTray() {
        return tray;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
}
