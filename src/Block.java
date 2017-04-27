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
        //check if the block can move up, down, left, or right
        //add to moves if possible
        if (checkMoveLeft(tray)){
            moves.add(new int[]{rowpos, colpos, rowpos, colpos-1});
        }
        if (checkMoveRight(tray)){
            moves.add(new int[]{rowpos, colpos, rowpos, colpos+1});
        }
        if (checkMoveUp(tray)){
            moves.add(new int[]{rowpos, colpos, rowpos-1, colpos});
        }
        if (checkMoveDown(tray)){
            moves.add(new int[]{rowpos, colpos, rowpos+1, colpos});
        }
        return moves;
    }

    private boolean checkMoveLeft(int[][] tray){
        //returns false if in the leftmost row
        if (getColPos() == 0) {
            return false;
        }
        //goes through all the rows the block is in and returns false if the space left of it is occupied
        for (int i = getRowPos(); i < getRowPos() + getRows(); i++){
            if (tray[i][getColPos()-1] != 0) {
                return false;
            }
        }
        //returns true otherwise
        return true;
    }

    private boolean checkMoveRight(int[][] tray){
        //returns false if in the rightmost column
        if (this.getColPos() + this.getCols() >= tray[0].length) {
            return false;
        }
        //goes through all the rows the block is in and returns false if the space right of it is occupied
        for (int i = getRowPos(); i < getRowPos() + getRows(); i++){
            if (tray[i][getColPos()+getCols()] != 0) {
                return false;
            }
        }
        //returns true otherwise
        return true;
    }

    private boolean checkMoveUp(int[][] tray){
        //returns false if in the highest row
        if (getRowPos() == 0) {
            return false;
        }
        //goes through all the columns the block is in and returns false if the space above it is occupied
        for (int i = getColPos(); i < getColPos() + getCols(); i++){
            if (tray[getRowPos()-1][i] != 0) {
                return false;
            }
        }
        //returns true otherwise
        return true;
    }

    private boolean checkMoveDown(int[][] tray){
        //returns false if in the lowest row
        if (getRowPos() + getRows() >= tray.length) {
            return false;
        }
        //goes through all the columns the block is in and returns false if the space below it is occupied
        for (int i = getColPos(); i < getColPos() + getCols(); i++){
            if (tray[getRowPos()+getRows()][i] != 0) {
                return false;
            }
        }
        //returns true otherwise
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
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof Block)) {
            return false;
        }
        Block other = (Block)obj;
        return (this.rows==other.rows && this.cols==other.cols && this.rowpos==other.rowpos && this.colpos==other.colpos);
    }

    // debug method, remove in final possibly
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
