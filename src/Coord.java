/**
 *
 * @author madsilva
 * @author jgeati
 */
public class Coord {
    private int row;
    private int col;

    public Coord(int r, int c) {
        row = r;
        col = c;
    }

    public Coord copy() {
        return new Coord(row, col);
    }
    
    // aaaaaaaaa
    // probably not good
    @Override
    public int hashCode() {
        return row*17 + col*13;
    }
    
    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Coord)) return false;
        
        Coord coord = (Coord) o; 
        return (coord.getRow() == this.getRow() && coord.getCol() == this.getCol());
        
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
