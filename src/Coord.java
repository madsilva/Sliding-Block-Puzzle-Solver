/**
 *
 * @author madsilva
 * @author jgeati
 */

/*
This class is used to store (row,col) coordinate pairs so that they can be used
as keys in a HashMap. 
*/

public class Coord {
    private int row, col;

    public Coord(int r, int c) {
        row = r;
        col = c;
    }

    public Coord copy() {
        return new Coord(row, col);
    }
    
    @Override
    public int hashCode() {
        return row*17 + col*13;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj==this) {
            return true;
        }
        if (obj==null || !(obj instanceof Coord)) {
            return false;
        }
        Coord other = (Coord)obj; 
        return (other.row == this.row && other.col == this.col);
    }

    public void setRow(int r) {
        row = r;
    }

    public void setCol(int c) {
        col = c;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
