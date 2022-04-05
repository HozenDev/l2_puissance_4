import static java.util.Objects.requireNonNull;
import java.util.EnumMap;

/**
 * Logical representation of a case in the grid of the Game of Puissance 4
 *
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public class Cell implements Comparable<Cell> {

    public final static Cell outOfBoundCell = new Cell(Token.emptyToken);

    private Token token;
    private EnumMap<Direction, Cell> neighborhood;

    public Cell(Token token) {
	/**
	 * Cell constructor
	 *
	 * @param token Token corresponding to the cell
	 */
	this.neighborhood = new EnumMap<>(Direction.class);
	if (token.getColor() == Color.EMPTY) this.token = Token.emptyToken;
    }

    public void setNeighbor(Cell c, Direction d) {
	/**
	 * Set the Cell neighbor to this in a Direction
	 *
	 * @param c Cell to set as a neighbor
	 * @param d Direction where to set the Cell neighbor
	 */
	this.neighborhood.put(requireNonNull(d), requireNonNull(c));
    }

    public Cell getNeighbor(Direction d) {
	/**
	 * Get a Cell neighbor in a direction, if null return an invalide valide cell
	 *
	 * @param d Direction where to get the neighbor
	 * @return the Cell corresponding : if null return outOfBoundCell
	 */
	Cell get;
	get = this.neighborhood.get(requireNonNull(d));
	if (get == null) {
	    get = Cell.outOfBoundCell;
	}
	return get;
    }

    public void setToken(Token t) {
	/**
	 * Set a Token to the Cell, can't set an EMPTY one
	 * 
	 * @param t not EMPTY Token to place in
	 */
	if (this.token.getColor() != Color.EMPTY) {
	    throw new IllegalArgumentException("There is already a token to this cell");
	}
	this.token = requireNonNull(t);
    }

    public Token getToken() {
	/**
	 * Get the Cell Token.
	 *
	 * @return the Token corresponding.
	 */
	return this.token;
    }

    public boolean isEmpty() {
	/**
	 * Test if the cell is EMPTY, if it's token if the emptyToken
	 *
	 * @return a boolean true if it's EMPTY, false if it is not
	 */
	return this.token.toString() == "EMPTY";
    }

    public boolean check() {
	/**
	 * Check if this Cell has the number required of same Cell in a Direction to "win"
	 *
	 * @return a boolean true if there is Game.numberOfTokenToWin neighbor with same
	 * token
	 */
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
	/**
	 * Recursive function count his number of Cell which has same token
	 *
	 * @param d Direction to check
	 * @return the number of same neighbor
	 */
	Cell next = this.getNeighbor(d);
	if (next == this.outOfBoundCell ||
	    this.getToken() != next.getToken()) {
	    return 0;
	}
	else return 1 + next.numberOfSameNeighbor(d);
    }

    public int numberOfSameNeighbor(Direction d1, Direction d2) {
	/**
	 * Recursive functin count his diagonales number of Cell which has same token
	 * 
	 * @param d1 first Direction diagonale
	 * @param d2 second Direction diagonale
	 * @return the number of same neighbor
	 */
	Cell next = this.getNeighbor(d1).getNeighbor(d2);
	if (next == this.outOfBoundCell ||
	    this.getToken() != next.getToken()) {
	    return 0;
	}
	else return 1 + next.numberOfSameNeighbor(d1, d2);
    }

    public Color getColor() {
	/**
	 * Get the Token Color of the Cell's Token.
	 * 
	 * @return the specific Color
	 */
	return this.getToken().getColor();
    }

    @Override
    public int compareTo(Cell other) {
	/**
	 * Override the compareTo function, test same Token reference
	 *
	 * @param other the other Cell to compare
	 * @return the comparaison
	 */
	if (this.getToken() == other.getToken()) return 0;
	return 1;
    }

    @Override
    public boolean equals(Object other) {
	/**
	 * Override the equals method 
	 *
	 * @param other the other object to test equality
	 * @result boolean true if it's equal, else false
	 */
	if (other == null) return false;
	if (other == this) return true;
	if (!(other instanceof Cell)) return false;
	return this.compareTo((Cell) other) == 0;
    }

    @Override
    public String toString() {
	/**
	 * Override toString method, print the Color of the Token of the Cell
	 *
	 * @return a String corresponding to the Color
	 */
	return this.getToken().getColor().toString();
    }
}
