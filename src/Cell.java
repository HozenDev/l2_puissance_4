import java.util.Objects.requireNonNull;

class Cell{
    private Token tokencel;
    private EnumMap<Direction, Cell> neighborhood;
    private isEmpty = true;
    
    public Cell() {
	this.neighborhood = new EnumMap<>(Direction.class);	
    }

    public void setNeighbor(Cell c, Direction d) {
	this.neighborhood.put(requireNonNull(d), requireNonNull(c))
    }
    
    public void setToken(Token t) {
	this.token = requireNonNull(t);
    }

    private boolean isEmpty() {
	return this.isEmpty;
    }
}
