import java.util.ArrayList;

/**
 *
 * @author madsilva
 * @author jgeati
 */
    public class Block {
        private final int rows, cols;
        private int rowpos, colpos;
        
        public Block(int r, int c, int rp, int cp) {
            rows = r;
            cols = c;
            rowpos = rp;
            colpos = cp;
        }

        public ArrayList<int[]> findMoves(int[][] tray) {
            ArrayList<int[]> moves = new ArrayList();
            // check if the block can move up, down, left, or right
            // add to moves if possible
            if (checkMoveLeft(tray)){
                int[] lmove = {rowpos, colpos, rowpos, colpos-1};
                moves.add(lmove);
            }
            if (checkMoveRight(tray)){
                int[] rmove = {rowpos, colpos, rowpos, colpos+1};
                moves.add(rmove);
            }
            if (checkMoveUp(tray)){
                int[] umove = {rowpos, colpos, rowpos-1, colpos};
                moves.add(umove);
            }
            if (checkMoveDown(tray)){
                int[] dmove = {rowpos, colpos, rowpos+1, colpos};
                moves.add(dmove);
            }
            return moves;
        }
        
        private boolean checkMoveLeft(int[][] tray){
            // returns false if in the leftmost column
            if (colpos == 0) return false;
            // goes through all the rows the block is in and returns false if the space left of it is occupied
            for (int i = rowpos; i < rowpos + rows; i++){
                if (tray[i][colpos-1] != 0) return false;
            }
            // returns true otherwise
            return true;
        }
        
        private boolean checkMoveRight(int[][] tray){
            // returns false if in the rightmost column
            if (colpos + cols >= tray[0].length) return false;
            // goes through all the rows the block is in and returns false if the space right of it is occupied
            for (int i = rowpos; i < rowpos + rows; i++){
                if (tray[i][colpos+cols] != 0) return false;
            }
            // returns true otherwise
            return true;
        }
        
        private boolean checkMoveUp(int[][] tray){
            // returns false if in the highest row
            if (rowpos == 0) return false;
            // goes through all the columns the block is in and returns false if the space above it is occupied
            for (int i = colpos; i < colpos + cols; i++){
                if (tray[rowpos-1][i] != 0) return false;
            }
            // returns true otherwise
            return true;
        }
        
        private boolean checkMoveDown(int[][] tray){
            // returns false if in the lowest row
            if (rowpos + rows >= tray.length) return false;
            // goes through all the columns the block is in and returns false if the space below it is occupied
            for (int i = colpos; i < colpos + cols; i++){
                if (tray[rowpos+rows][i] != 0) return false;
            }
            // returns true otherwise
            return true;
        }
        
        public Block copy() {
            return new Block(rows, cols, rowpos, colpos);
        }
        
        @Override
        public int hashCode() {
          int sum = 1;
          sum = sum*31 + rows;
          sum = sum*31 + cols;
          sum = sum*31 + rowpos;
          sum = sum*31 + colpos;
          return sum;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj==this) {
                return true;
            }
            if (obj==null || !(obj instanceof Block)) {
                return false;
            }
            Block other = (Block)obj;
            return (this.rows==other.rows && this.cols==other.cols && this.rowpos==other.rowpos && this.colpos==other.colpos);
        }
        
        // debug method
        @Override
        public String toString() {
            return rows + " " + cols + " " + rowpos + " " + colpos;
        }
        
        public void setRowColPos(int r, int c) {
            rowpos = r;
            colpos = c;
        }

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
