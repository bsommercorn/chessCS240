package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    int myrank = 1;
    int myfile = 1;

    public ChessPosition(int row, int col) {
        myrank = row - 1;
        myfile = col - 1;
    }

    public ChessPosition(String algebraicNote) {
        switch (algebraicNote.charAt(0)) {
            case 'a':
                myfile = 0;
                break;
            case 'b':
                myfile = 1;
                break;
            case 'c':
                myfile = 2;
                break;
            case 'd':
                myfile = 3;
                break;
            case 'e':
                myfile = 4;
                break;
            case 'f':
                myfile = 5;
                break;
            case 'g':
                myfile = 6;
                break;
            case 'h':
                myfile = 7;
                break;
        }
        myrank = Character.getNumericValue(algebraicNote.charAt(1)) - 1;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return myrank;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return myfile;
        //throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition position = (ChessPosition) o;
        return myrank == position.myrank && myfile == position.myfile;
    }

    @Override
    public String toString() {
        return "(" + (myfile + 1) + "," + (myrank + 1) + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(myrank, myfile);
    }
}
