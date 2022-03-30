import java.util.EnumMap;

public enum Direction {

    UP, DOWN, RIGHT, LEFT;

    public static EnumMap<Direction, Direction> getDiagonales() {
	EnumMap<Direction, Direction> diagonales = new EnumMap<>(Direction.class);
	diagonales.put(Direction.UP, Direction.LEFT);
	diagonales.put(Direction.DOWN, Direction.RIGHT);
	diagonales.put(Direction.LEFT, Direction.DOWN);
	diagonales.put(Direction.RIGHT, Direction.UP);
	return diagonales;
    }
}
