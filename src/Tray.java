
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author madsilva
 * @author jgeati
 */

// IDEA DUMP - type things you think of here
// how the hell are we going to efficiently check solutions?
// maybe make a solution tray and check if all the spaces filled in that tray are filled in the game tray
// then if thats true, check if the blocks match

// how should we store goal files?
// maybe: store goal within each tray and call tray.checkgoal to see if goal has been met
// should we just make them their own trays?

// tray grid keeps track of what space is free with 0s and 1s
// block objects keep track of their own areas/coords
// when trying to make a move, iterate thru the list of blocks and see what can be 

// we HAVE to sort the blocks somehow. 

/* Steps for finding moves? 
Either: go thru list of blocks and check which ones have 0s adjacent, then check which one of those can actually be moved
or look at where the 0s are in tray, and find the blocks that are adjacent to the 0s.
once we find blocks that actually can be moved, pick randomly(?) which move to make next
then execute the move and record it
then check whether goal has been met
*/

public class Tray {
    private int rows;
    private int cols;
    private int[][] tray;
    private ArrayList<Block> blocks;
    
    public Tray(int r, int c) {
        rows = r;
        cols = c;
        // note that tray is already filled with 0s
        tray = new int[r][c]; 
        blocks = new ArrayList();
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
        Block b = new Block(rows, cols, rowsPos, colsPos);
        blocks.add(b); 
        
        // fill in tray with 1s in blocks position
        // are the for loop vals correct?
        for (int r = b.getRowPos(); r<b.getRows()+b.getRowPos(); r++) {
            for (int c = b.getColPos(); c<b.getCols() + b.getColPos(); c++) {
                if (tray[r][c]==0) {
                    tray[r][c] = 1;
                }
                else {
                    return false;
                }
            }
        }
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
    }
    
    // should moveBlock be responsible for finding the block to be moved?
    private boolean moveBlock(Block b, int[] move) {
        return false;
    }
    
    // this method checks if the tray satisfies a given goal, ie the blocks in
    // the goal tray are in the correct configuration in the game tray
    // this does NOT check if two trays are equal
    public boolean checkGoal(Tray goal) {
        // go thru this.blocks and then go thru goal and see if each block in goal matches a block in this
        // how is this not going to run in n*m lmao
        for (Block g : goal.getBlocks()) {
            boolean found = false;
            for (Block b : blocks) {
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
    public ArrayList<Block> getBlocks() {
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
    
    private class Block {
        private int rows, cols, rowpos, colpos;
        
        public Block(int r, int c, int rp, int cp) {
            rows = r;
            cols = c;
            rowpos = rp;
            colpos = cp;
        }
        
        @Override
        // oh my god do we have to override hashcode if we do this
        public boolean equals(Object obj) {
            if (obj.getClass() != getClass() || obj==null) {
                return false;
            }
            Block other = (Block)obj; // this is prob bad but i got it from stackexchange
            if (rows==other.rows && cols==other.cols && rowpos==other.rowpos && colpos==other.colpos) {
                return true;
            }
            return false;
        }
        
        // Im not sure which is best - setting row/col vals directly or adding to them to change them.
        // i think once we implement more we'll find out, but i wrote both methods.
        
        // note that r can be negative
        public void moveRow(int r) {
            if (rowpos+r >= 0) {
                rowpos += r;
            }
            else {
                System.out.println("move failed");
            }
        }
        
        public void moveCol(int c) {
            if (colpos+c >= 0) {
                colpos += c;
            }
            else {
                System.out.println("move failed");
            }
        }
        
        // validity checks?
        public void setRowPos(int r) {
            rowpos = r;
        }
        
        public void setColPos(int c) {
            colpos = c;
        }
        
        // should these fields just be public?
        public int getRows() {
            return rows;
        }
        
        public int getCols() {
            return cols;
        }
        
        public int getRowPos() {
            return rowpos;
        }
        
        public int getColPos() {
            return colpos;
        }
    }
}
