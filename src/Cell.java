import static java.util.Objects.requireNonNull;
import java.util.EnumMap;

class Cell implements Comparable<Cell> {

    public final static Cell outOfBoundCell = new Cell(Token.emptyToken);

    private Token token;
    private EnumMap<Direction, Cell> neighborhood;
    
    public Cell(Token token) {
	this.neighborhood = new EnumMap<>(Direction.class);
	this.token = token;
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

    public int numberOfSameNeighbor(Direction d, int count) {

	Cell suiv = this.getNeighbor(d);

	if (suiv == this.outOfBoundCell || this.getToken() != suiv.getToken()) {
	    return count;
	}
	else {
	    return suiv.numberOfSameNeighbor(d, count+1);
	}
    }
    
    public int numberOfSameNeighbor(Direction d1, Direction d2, int count) {

	Cell suiv = this.getNeighbor(d1);

	if (suiv == this.outOfBoundCell) {
	    return count;
	}
	
	suiv = suiv.getNeighbor(d2);
	
	if (suiv == this.outOfBoundCell || this.getToken() != suiv.getToken()) {
	    return count;
	}
	else {
	    return suiv.numberOfSameNeighbor(d1, d2, count+1);
	}
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
