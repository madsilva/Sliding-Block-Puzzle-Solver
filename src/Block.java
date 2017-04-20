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
        
        public ArrayList<int[]> findMoves(int[][] tray) {
            ArrayList<int[]> moves = new ArrayList();
            
            //called on the block
            //check if the block can move up, down, left, or right
            //add to moves if possible
          System.out.println(toString());
            if (checkMoveLeft(tray)){
                System.out.println(checkMoveLeft(tray));
                int[] move = {getRowPos(), getColPos(), getRowPos(), getColPos()-1};
                moves.add(move);
            }
            
            if (checkMoveRight(tray)){
                System.out.println(checkMoveRight(tray));
                int[] move = {getRowPos(), getColPos(), getRowPos(), getColPos()+1};
                moves.add(move);
            }
            
            if (checkMoveUp(tray)){
                System.out.println(checkMoveUp(tray));
                int[] move = {getRowPos(), getColPos(), getRowPos()-1, getColPos()};
                moves.add(move);
            }
            
            if (checkMoveDown(tray)){
                System.out.println(checkMoveDown(tray));
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
            System.out.println(this.getColPos() + " " + this.getCols() + " " + tray[0].length);
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
        
        public String toString() {
            return rows + " " + cols + " " + rowpos + " " + colpos;
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
