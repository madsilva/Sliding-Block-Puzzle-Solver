
import java.util.LinkedList;

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

/* Steps for finding moves? 
Implement block.canMove() - keeps a boolean val for each block to indicate whether there is free space for it to move or not
*/

public class Tray {
    private int rows; // not sure if w and h should be variables
    private int cols;
    private int[][] tray;
    private LinkedList<Block> blocks; // maybe could be arraylist
    
    public Tray(int r, int c) {
        rows = r;
        cols = c;
        // note that tray is already filled with 0s
        tray = new int[r][c]; 
        blocks = new LinkedList(); // maybe should be above
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
