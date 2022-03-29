import static java.util.Objects.requireNonNull;
import java.util.EnumMap;

class Cell implements Comparable<Cell> {

    public final static Cell outOfBoundCell = new Cell();

    private Token token;
    private EnumMap<Direction, Cell> neighborhood;
    private boolean isEmpty = true;
    
    public Cell() {
	this.neighborhood = new EnumMap<>(Direction.class);
    }

    public void setNeighbor(Cell c, Direction d) {
	this.neighborhood.put(requireNonNull(d), requireNonNull(c));
    }

    public Cell getNeighbor(Direction d) {
	Cell get;
	get = this.neighborhood.get(d);
	if (get == null) {
	    get = this.outOfBoundCell;
	}
	return get;
    } 
    
    public void setToken(Token t) {
	this.token = requireNonNull(t);
	this.isEmpty = false;
    }

    public Color getToken() {
	return this.token;
    }
    
    public boolean isEmpty() {
	return this.isEmpty;
    }

    public int numberOfSameNeighbor(Direction d, int count) {
	Cell suiv = new Cell();
	suiv = this.getNeighbor(d);

	if (suiv == this.outOfBoundCell) {
	    return count;
	}
	else {
	    return suiv.numberOfSameNeighbor(d, count+1);
	}
    }

    public int numberOfSameNeighbor(Direction d1, Direction d2, count) {
	Cell suiv1;
	Cell suiv;

	tmp = this.getNeighbor(d1);

	if (tmp == this.outOfBoundCell) {
	    return count;
	}
	
	suiv = suiv1.getNeighbor(tmp);
	
	if (suiv == this.outOfBoundCell) {
	    return count;
	}
	else {
	    return suiv.numberOfSameNeighbor(d, count+1);
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

    // @Override
    // public String toString() {
    // 	return "Je suis une cellule";
    // }
}
