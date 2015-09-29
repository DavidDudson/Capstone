package nz.daved.starbattle.game;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by David J. Dudson on 4/08/15.
 * <p>
 * Coordinate in an integer based grid system indexes stating at 0
 */
public class Coordinate {

    private int x;
    private int y;

    /**
     * Makes a coordinate tuple from 2 ints.
     *
     * @param _x which x value
     * @param _y which column Value
     */
    public Coordinate(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    /**
     * Copy Contructor
     * @param coordinate coordinate to copy
     */
    public Coordinate(Coordinate coordinate){
        this(coordinate.getX(),coordinate.getY());
    }

    /**
     * Add amount to X value of coord
     * @param amount the amount to increment x by
     * @return The new coordinate
     */
    public Coordinate addToX(int amount){
        Coordinate coord = new Coordinate(this);
        coord.x += amount;
        return coord;
    }

    /**
     * Minus amount from X value of coord
     * @param amount the amount to decrement x by
     * @return The new coordinate
     */
    public Coordinate takeFromX(int amount){
        Coordinate coord = new Coordinate(this);
        coord.x -= amount;
        return coord;
    }

    /**
     * Add amount to Y value of coord
     * @param amount The amount to increment Y by
     * @return The new Coordinate
     */
    public Coordinate addToY(int amount){
        Coordinate coord = new Coordinate(this);
        coord.y += amount;
        return coord;
    }

    /**
     * Minus amount from Y value of coord
     * @param amount The amount to decrement Y by
     * @return The new Coordinate
     */
    public Coordinate takeFromY(int amount){
        Coordinate coord = new Coordinate(this);
        coord.y -= amount;
        return coord;
    }

    public List<Coordinate> getNeighbours(){
        List<Coordinate> neighbours = new LinkedList<>();
        neighbours.add(this.addToY(1));
        neighbours.add(this.addToX(1));
        neighbours.add(this.takeFromY(1));
        neighbours.add(this.takeFromX(1));
        return neighbours;
    }

    /**
     * Get the which x in the array
     *
     * @return the x number
     */
    public int getX() {
        return x;
    }

    /**
     * Get the which column in the array
     *
     * @return the column number
     */
    public int getY() {
        return y;
    }

    /**
     * Prints out the coordinate like this: (x,y)
     *
     * @return the String format of the coordinate
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return x == that.x && y == that.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
