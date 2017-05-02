import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 *
 * @author madsilva
 * @author jgeati
 */

public class Tray {
    private static int rows, cols, totalTrays;
    private int[][] tray;
    private int[][] lastIsOk;
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
        totalTrays = 0;
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
        TreeNode<Tray> goalNode = breadthFirst(root, goal);
        if (goalNode == null) {
            return new ArrayList();
        }
        return goalNode.getData().appliedMoves;
    }
  
    private TreeNode breadthFirst(TreeNode root, Tray goal) {
        LinkedList<TreeNode<Tray>> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode<Tray> parent = queue.remove();
            totalTrays++;
            
            ArrayList<int[]> possibleMoves = parent.getData().findMoves();
            for (int[] m : possibleMoves) {
                Tray possibleChild = new Tray(parent.getData(), m);
                // returns true if possibleChild is successfully added to set
                // meaning the possibleChild hasn't been visited before
                if (visited.add(possibleChild)) {
                    //returns the child if it matches the goal
                    if (possibleChild.checkGoal(goal)) {                     
                        return new TreeNode(possibleChild);
                    }
                    parent.addChild(possibleChild);
                    
                }
            }
            ArrayList<TreeNode> nodes = parent.getChildren();
            // adds all of the children to the queue
            for (TreeNode t:nodes) {
                queue.add(t);
            }
        }
          return null;  
    }
    
    // Recursive algorithm that generates and searches a tree of trays depth first to find a solution.
    // This was our initial algorithm for finding solutions, but we found the one above 
    // generally worked better.
    private TreeNode depthFirst(TreeNode<Tray> n, Tray goal) {
        totalTrays++;
        ArrayList<int[]> possibleMoves = (n.getData().findMoves());
        for (int[] m : possibleMoves) {
            Tray possibleChild = new Tray(n.getData(), m);
            // returns false if unable to add possibleChild 
            // meaning it is already in the set
            if (visited.add(possibleChild)) {
                n.addChild(possibleChild);
                 // checks if the child satisfies the goal before adding calling it recursively
                 if (possibleChild.checkGoal(goal)) {
                    return new TreeNode(possibleChild);
                }
            }
        }
        ArrayList<TreeNode> nodes = n.getChildren();
            // calls depthFirst on all the children and returns one of them if they solve the solution
            for (TreeNode t : nodes) {
                TreeNode returned = null;
                try {
                    returned = depthFirst(t, goal);
                } catch(StackOverflowError e){
                }
                // this makes sure null is only returned if all options have been checked
                if (returned != null) return returned;
            }
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
            tray = lastIsOk;
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

    // Looks at the current block map of the tray and checks if there is overlap 
    // by creating a 2d array of 0s and attempting to add blocks as 1s - if there 
    // is more than one 1 in a space, the configuration is invalid.
    // if no blocks overlap, lastIsOk is set to the new 2d array to be used to update
    // the tray's 2d array in other methods.
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
        lastIsOk = newTray;
        return true;
    }
    
    // Takes a line of input* representing a block and adds it to the tray.
    // If new block overlaps with other blocks it will not be added.
    // *Block data input format: rows, columns, upper left row, upper left column
    public void addBlock(String line) {
        String [] vals = line.split(" ");
        int rows = Integer.parseInt(vals[0]);
        int cols = Integer.parseInt(vals[1]);
        int rowsPos = Integer.parseInt(vals[2]);
        int colsPos = Integer.parseInt(vals[3]);

        // create block and add it to the map
        Block b = new Block(rows, cols, rowsPos, colsPos);
        Coord bCoord = new Coord(rowsPos, colsPos);
        blocks.put(bCoord, b);
        if (isOk()) {
            // update the tray with the last int[][] created by isOk
            tray = lastIsOk;
        }
        else {
            // if adding the block creates an invalid tray, remove it
            blocks.remove(bCoord);
        }
    }
    
    // debug method
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
    
    // debug method
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
    
    public int getTotalTrays() {
        return totalTrays;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
}
