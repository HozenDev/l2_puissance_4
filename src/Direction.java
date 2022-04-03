import java.util.EnumMap;

/**
 * Represent different direction with diagonales
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public enum Direction {

    UP, DOWN, RIGHT, LEFT;

    public static EnumMap<Direction, Direction> getDiagonales() {
	/**
	 * Give an EnumMap of Direction which each key is a Direction and its value
	 * the diagonale Direction corresponding.
	 *
	 * @return EnumMap representing diagonales
	 */
	EnumMap<Direction, Direction> diagonales = new EnumMap<>(Direction.class);
	diagonales.put(Direction.UP, Direction.LEFT);
	diagonales.put(Direction.DOWN, Direction.RIGHT);
	diagonales.put(Direction.LEFT, Direction.DOWN);
	diagonales.put(Direction.RIGHT, Direction.UP);
	return diagonales;
    }

    public static Direction getOpposite(Direction d) {
	/**
	 * Give the opposite Direction to a Direction given
	 * 
	 * @param d Direction source
	 * @return the opposite of d Direction
	 */
	switch (d) {
	case UP:
	    return DOWN;
	case DOWN:
	    return UP;
	case RIGHT:
	    return LEFT;
	case LEFT:
	    return RIGHT;
	}
	return UP;
    }
}
