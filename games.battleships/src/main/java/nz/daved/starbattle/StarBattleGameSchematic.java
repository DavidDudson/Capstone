package nz.daved.starbattle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David J. Dudson on 13/08/15.
 * <p>
 * Schematic for generating a game
 */
public class StarBattleGameSchematic {

    private List<Integer> ships = new ArrayList<>();
    private int width;
    private int height;
    private long seed;

    /**
     * Custom Game Schematic
     *
     * @param ships  A list of ship lengths
     * @param width  The Grid width
     * @param height The grid height
     * @param seed   The seed used for the random generator
     */
    public StarBattleGameSchematic(List<Integer> ships, int width, int height, long seed) {
        this.ships  = ships;
        this.width  = width;
        this.height = height;
        this.seed   = seed;
    }

    /**
     * Default settings for Mison Bradley Battleships game
     */
    public StarBattleGameSchematic() {
        Collections.addAll(ships, 2,2,3,3,4,4,5);
        this.width  = 10;
        this.height = 10;
        this.seed   = 1L;
    }

    /**
     * Copy contructor
     *
     * @param sbgc What to base the copy off
     */
    public StarBattleGameSchematic(StarBattleGameSchematic sbgc) {
        this.ships  = sbgc.getShips();
        this.width  = sbgc.getWidth();
        this.height = sbgc.getHeight();
        this.seed   = sbgc.getSeed();
    }

    public long getSeed() {
        return seed;
    }

    public List<Integer> getShips() {
        return ships.stream()
                .map(Integer::new)
                .collect(Collectors.toList());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
