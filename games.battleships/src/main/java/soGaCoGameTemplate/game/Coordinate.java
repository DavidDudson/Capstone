package soGaCoGameTemplate.game;

/**
 * Created by David J. Dudson on 4/08/15.
 *
 * Coordinate in an integer based grid system indexes stating at 0
 */
public class Coordinate {

    private int row;
    private int col;

    /**
     * Makes a coordinate tuple from 2 ints.
     * @param row which row value
     * @param col which column Value
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Get the which row in the array
     * @return the row number
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the which column in the array
     * @return the column number
     */
    public int getCol() {
        return col;
    }

    /**
     * Prints out the coordinate like this: (x,y)
     * @return the String format of the coordinate
     */
    @Override
    public String toString() {
        return "(" + row +  "," + col + ")";
    }
}
