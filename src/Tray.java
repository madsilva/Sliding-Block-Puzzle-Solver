
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author madsilva
 * @author jgeati
 */

// IDEA DUMP - type things you think of here
// how the hell are we going to efficiently check solutions?
// maybe make a solution tray and check if all the spaces filled in that tray are filled in the game tray
// then if thats true, check if the blocks match

/* Steps for finding moves? 
Either: go thru list of blocks and check which ones have 0s adjacent, then check which one of those can actually be moved
or look at where the 0s are in tray, and find the blocks that are adjacent to the 0s.
once we find blocks that actually can be moved, pick randomly(?) which move to make next
then execute the move and record it
then check whether goal has been met
*/

/* Current design of Tray:
int[][] tray holds a grid of 0s ans 1s indicating where blocks are and are not. This is updated when a block is added or a move is made.
I'm not sure about whether this is something we want to keep long term.
blocks will be a Map (HashMap?) of (Coord:Block) pairings. We need to implement coord.hashCode and i THINK coord.equals() for this to work
do we want blocks to have internal Coord objects or just ints? probably coords
Map usage allows for fast lookup of blocks if you have coords.

What we need is a good way to find moveable blocks
should blocks check their own moveability? boolean block.isMoveable, checks edges of block against tray 0s and 1s

maybe representing each block as its own integer isnt the worst idea
each block gets an integer that is its key in the map and its representation on the tray
when looking for moves, start with the 0s in the tray array and look at whats adjacent
look up blocks adjacent to 0s by their integer id and check if they are moveable
this might be more efficient bc we wont waste time looking at blocks that arent even adjacent to blank space
we WILL waste time looking for 0s tho
but this also might allow for more efficent storage of tray configs, because each config could be represented ONLY as a 2d array (maybe)
when checking the goal we would still just iterate over the values in the block map and check if every block in goal has a block in tray - would not have to worry about each block's id

*/

public class Tray {
    private int rows;
    private int cols;
    // count for each block's unique ID
    private int count;
    private int[][] tray;
    private HashMap<Integer, Block> blocks;
    
    public Tray(int r, int c) {
        rows = r;
        cols = c;
        // note that tray is already filled with 0s
        tray = new int[r][c]; 
        blocks = new HashMap();
        count = 1;
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
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
        // checking for valid values
        // other rules for validity?
        // should this even be here?
        if (rows<=0 || cols<=0 || rowsPos<0 || colsPos<0) {
            return false;
        }
        // create block and add it to list
        Block b = new Block(rows, cols, rowsPos, colsPos, count);
        blocks.put(count, b); 
        
        System.out.println(b.toString());
        
        // fill in tray with 1s in blocks position
        // are the for loop vals correct?
        for (int r = b.getRowPos(); r<b.getRows()+b.getRowPos(); r++) {
            for (int c = b.getColPos(); c<b.getCols() + b.getColPos(); c++) {
                if (tray[r][c]==0) {
                    tray[r][c] = b.getID();
                }
                else {
                    return false;
                }
            }
        }
        count++;
        return true;
    }
    
    // this is the method we would use if we went full encapsulation
    // it returns a list of moves to solve the tray given a goal
    // returns empty list if the tray is unsolvable
    // Moves are represented as int arrays and are formatted:
    // start row, start col, end row, end col
    public ArrayList<int[]> solve(Tray goal) {
        // while not solved or not shown impossible
        // get possible moves
        // make a move
        // check if solved
        
        //just returns this for compiling reasons
        return new ArrayList<>();
    }
    

    private ArrayList<> findMoves() {
        ArrayList<int[]> moves = new ArrayList();
        for (Block b : blocks.values()) {
            moves.append(b.getMoves());
        }
    }
    
    // should moveBlock be responsible for finding the block to be moved?
    private boolean moveBlock(Block b, int[] move) {
        System.out.println("Will always false");
        return false;
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

    // this might be questionable...
    // maybe blocks should be public
    public HashMap<Integer, Block> getBlocks() {
        return blocks;
    }

    // im really not sure what this is actually supposed to do
    public boolean isOk(){
        System.out.println("Will always false");
        return false;
    }
    
    // this is prob a good idea for debugging
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
    
    // this will be harder because we should deliniate where each block is - not sure if its worth it
    public String toString() {
        String output = "";
        
        return output;
    }
    
    // This method takes a Block b and a move array and checks the move against 
    // the current tray to see if it's valid.
    // Move data input format: 
    private boolean checkMove(Block b, int[] move) {
        System.out.println("Will always false");
        return false;
    }
    

    
    private class Coord {
        private int row;
        private int col;
        
        public Coord(int r, int c) {
            row = r;
            col = c;
        }
        
        // aaaaaaasa
        public int hashCode() {
            return 0;
        }
        
        // validity checks?
        public void setRow(int r) {
            row = r;
        }
        
        public void setCol(int c) {
            col = c;
        }
        
        // we might not need these, not sure
        public int getRow() {
            return row;
        }
        
        public int getCol() {
            return col;
        }
    }
}
