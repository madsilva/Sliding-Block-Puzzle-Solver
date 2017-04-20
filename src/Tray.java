import java.util.ArrayList;
import java.util.HashMap;

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
If the dimensions of the tray are 2 digits or more, parseInt/substring doesn't work
If requires more than one move, makes the same two moves over and over
*/
public class Tray {
    private int rows, cols;
    private int[][] tray;
    private HashMap<Coord, Block> blocks;
    
    public Tray(int r, int c) {
        rows = r;
        cols = c;
        // note that tray is already filled with 0s
        tray = new int[r][c]; 
        blocks = new HashMap();
    }
    
    // this method returns a list of moves to solve the tray given a goal
    // returns empty list if the tray is unsolvable
    // Moves are represented as int arrays and are formatted:
    // start row, start col, end row, end col
    public ArrayList<int[]> solve(Tray goal) {
        // while not solved or not shown impossible
        // get possible moves
        // make a move
        // check if solved
        ArrayList<int[]> solution = new ArrayList(); 
        boolean solved = false;
        // junk here to test how findMoves was working
//        ArrayList<int[]> myMoves = findMoves();
//        for (int[] m : myMoves) {
//            for (int i=0;i<m.length;i++) {
//                System.out.print(m[i]);
//            }
//            System.out.println();
//        }
        //solved = true; // added to prevent infinite loop during testing of other things
        while (!solved) {
            ArrayList<int[]> moves = findMoves();
            if (moves.isEmpty()) { // no possible moves were found
                return null; // or empty list idk
            }
            // moves can somehow be used to make a tree of moves
            int[] myMove = moves.get(0);
            Block moveBlock = blocks.get(new Coord(myMove[0], myMove[1])); // findng the block in the dictionary by coord key
            moveBlock(moveBlock, myMove);
            solution.add(myMove);
            if (checkGoal(goal)) {
                System.out.println("solved");
                solved = true;
            }
        }
        return solution;
    }
    
    // this method returns an arraylist of every possible move for each block
    private ArrayList<int[]> findMoves() {
        ArrayList<int[]> moves = new ArrayList();
        for (Block b : blocks.values()) {
            moves.addAll(b.findMoves(tray));
        }
        return moves;
    }
    
    private void moveBlock(Block b, int[] move) {
        blocks.remove(new Coord(b.getRowPos(), b.getColPos()));
        b.setRowPos(move[2]);
        b.setColPos(move[3]);
        blocks.put(new Coord(b.getRowPos(), b.getColPos()), b);
        // how the fuck do we update the tray
    }
    
    // this method checks if the tray satisfies a given goal, ie the blocks in
    // the goal tray are in the correct configuration in the game tray
    // this does NOT check if two trays are equal
    public boolean checkGoal(Tray goal) {
        // go thru this.blocks and then go thru goal and see if each block in goal matches a block in this
        // how is this not going to run in n*m lmao
        for (Block g : goal.getBlocks().values()) {
            boolean found = false;
            for (Block b : blocks.values()) {
                if (b.equals(g)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
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

        // create block and add it to list
        Block b = new Block(rows, cols, rowsPos, colsPos);
        Coord bCoord = new Coord(rowsPos, colsPos);
        blocks.put(new Coord(rowsPos, colsPos), b);
        if (!isOk()) {
            blocks.remove(bCoord);
            return false;
        }
        
        // fill in tray with 1s in blocks position
        for (int r = b.getRowPos(); r<b.getRows()+b.getRowPos(); r++) {
            for (int c = b.getColPos(); c<b.getCols() + b.getColPos(); c++) {
                // we should not have to check if the spot is free bc isOk should have already done that
                tray[r][c] = 1;
            }
        }
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
        for (int r=0; r<rows;r++) {
            for (int c=0; c<cols; c++) {
                output += tray[r][c] + " ";
            }
            output += "\n";
        }
        return output;
    }
    
    public HashMap<Coord, Block> getBlocks() {
        return blocks;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
}
