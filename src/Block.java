/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author madsilva
 */
    public class Block {
        private int rows, cols, rowpos, colpos;
        
        public Block(int r, int c, int rp, int cp, int i) {
            rows = r;
            cols = c;
            rowpos = rp;
            colpos = cp;
        }
        
        public ArrayList<int[]> findMoves(int[][] tray) {
            ArrayList<int[]> moves = new ArrayList();
            
            for ()
            
            return moves;
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
        
        public int getID() {
            return id;
        }
    }
