import static java.util.Objects.requireNonNull;
import java.util.EnumMap;

class Cell implements Comparable<Cell> {

    public final static Cell outOfBoundCell = new Cell(Token.emptyToken);

    private Token token;
    private EnumMap<Direction, Cell> neighborhood;

    public Cell(Token token) {
	this.neighborhood = new EnumMap<>(Direction.class);
	if (token.getColor() == Color.EMPTY) this.token = Token.emptyToken;
    }

    public void setNeighbor(Cell c, Direction d) {
	this.neighborhood.put(requireNonNull(d), requireNonNull(c));
    }

    public Cell getNeighbor(Direction d) {
	Cell get;
	get = this.neighborhood.get(requireNonNull(d));
	if (get == null) {
	    get = this.outOfBoundCell;
	}
	return get;
    }

    public void setToken(Token t) {
	if (this.token.getColor() != Color.EMPTY) {
	    throw new IllegalArgumentException("There is already a token to this cell");
	}
	this.token = requireNonNull(t);
    }

    public Token getToken() {
	return this.token;
    }

    public boolean isEmpty() {
	return this.token.toString() == "EMPTY";
    }

    public boolean check() {
	EnumMap<Direction, Direction> diagonales = Direction.getDiagonales();
	int count;

	for (Direction d: Direction.values()) {
	    /* Horizontal and vertical check */
	    count = this.numberOfSameNeighbor(d);
	    count += this.numberOfSameNeighbor(Direction.getOpposite(d));
	    count ++; // Pour compter le pion actuel

	    if (count >= Game.numberOfTokenToWin) return true;

	    /* Diagonales check */
	    count = this.numberOfSameNeighbor(d, diagonales.get(d));
	    count += this.numberOfSameNeighbor(Direction.getOpposite(d),
					       Direction.getOpposite(diagonales.get(d)));
	    count ++;

	    if (count >= Game.numberOfTokenToWin) return true;
	}
	return false;
    }

    public int numberOfSameNeighbor(Direction d) {
	Cell next = this.getNeighbor(d);
	if (next == this.outOfBoundCell ||
	    this.getToken() != next.getToken()) {
	    return 0;
	}
	else return 1 + next.numberOfSameNeighbor(d);
    }

    public int numberOfSameNeighbor(Direction d1, Direction d2) {
	Cell next = this.getNeighbor(d1).getNeighbor(d2);
	if (next == this.outOfBoundCell ||
	    this.getToken() != next.getToken()) {
	    return 0;
	}
	else return 1 + next.numberOfSameNeighbor(d1, d2);
    }

    public Color getColor() {
	return this.getToken().getColor();
    }

    @Override
    public int compareTo(Cell other) {
	if (this.getToken() == other.getToken()) return 0;
	return 1;
    }

    @Override
    public boolean equals(Object other) {
	if (other == null) return false;
	if (other == this) return true;
	if (!(other instanceof Cell)) return false;
	return this.compareTo((Cell) other) == 0;
    }

    @Override
    public String toString() {
	return this.getToken().getColor().toString();
    }
}
