import java.util.ArrayList;

/**
 *
 * @author madsilva
 * @author jgeati
 */
    public class Block {
        private int rows, cols, rowpos, colpos;
        
        public Block(int r, int c, int rp, int cp) {
            rows = r;
            cols = c;
            rowpos = rp;
            colpos = cp;
        }
        
        public Block copy() {
            return new Block(rows, cols, rowpos, colpos);
        }
        
        public ArrayList<int[]> findMoves(int[][] tray) {
            ArrayList<int[]> moves = new ArrayList();
            
            //called on the block
            //check if the block can move up, down, left, or right
            //add to moves if possible
            if (checkMoveLeft(tray)){
                int[] move = {getRowPos(), getColPos(), getRowPos(), getColPos()-1};
                moves.add(move);
            }
            
            if (checkMoveRight(tray)){
                int[] move = {getRowPos(), getColPos(), getRowPos(), getColPos()+1};
                moves.add(move);
            }
            
            if (checkMoveUp(tray)){
                int[] move = {getRowPos(), getColPos(), getRowPos()-1, getColPos()};
                moves.add(move);
            }
            
            if (checkMoveDown(tray)){
                int[] move = {getRowPos(), getColPos(), getRowPos()+1, getColPos()};
                moves.add(move);
            }
            
            return moves;
        }
        
        private boolean checkMoveLeft(int[][] tray){
            //returns false if in the leftmost row
            if (getColPos() == 0) return false;
            //goes through all the rows the block is in and returns false if the space left of it is occupied
            for (int i = getRowPos(); i < getRowPos() + getRows(); i++){
                if (tray[i][getColPos()-1] != 0) return false;
            }
            //returns true otherwise
            return true;
        }
        
        private boolean checkMoveRight(int[][] tray){
            //returns false if in the rightmost column
            if (this.getColPos() + this.getCols() >= tray[0].length) return false;
            //goes through all the rows the block is in and returns false if the space right of it is occupied
            for (int i = getRowPos(); i < getRowPos() + getRows(); i++){
                if (tray[i][getColPos()+1] != 0) return false;
            }
            //returns true otherwise
            return true;
        }
        
        private boolean checkMoveUp(int[][] tray){
            //returns false if in the highest row
            if (getRowPos() == 0) return false;
            //goes through all the columns the block is in and returns false if the space above it is occupied
            for (int i = getColPos(); i < getColPos() + getCols(); i++){
                if (tray[getRowPos()-1][i] != 0) return false;
            }
            //returns true otherwise
            return true;
        }
        
        private boolean checkMoveDown(int[][] tray){
            //returns false if in the lowest row
            if (getRowPos() + getRows() >= tray.length) return false;
            //goes through all the columns the block is in and returns false if the space below it is occupied
            for (int i = getColPos(); i < getColPos() + getCols(); i++){
                if (tray[getRowPos()+1][i] != 0) return false;
            }
            //returns true otherwise
            return true;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj==this) {
                return true;
            }
            if (obj.getClass() != getClass() || obj==null) {
                return false;
            }
            Block other = (Block)obj; // this is prob bad but i got it from stackexchange
            if (rows==other.rows && cols==other.cols && rowpos==other.rowpos && colpos==other.colpos) {
                return true;
            }
            return false;
        }
        
        @Override
        // bad
        public int hashCode() {
            int hash;
            hash = 13*rows + 17*cols + 19*rowpos + 29*colpos;
            return hash;
        }
        
        public String toString() {
            return rows + " " + cols + " " + rowpos + " " + colpos;
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
