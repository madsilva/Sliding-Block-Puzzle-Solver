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

    // aaaaaaaaa
    // probably not good
    public int hashCode() {
        return row*17 + col*13;
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
