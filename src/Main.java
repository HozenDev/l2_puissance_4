
public class Main {
    public static void main(String[] args) {
	// Save test = new Save("./log.txt");

	// Integer a = 13412;
	
	// test.write(a);
	// test.read();

	Cell c1 = new Cell();
	Cell c2 = new Cell();

	Token tr = new Token(Color.RED);
	Token ty = new Token(Color.YELLOW);
	
	c1.setNeighbor(c2, Direction.UP);
	c2.setNeighbor(c1, Direction.DOWN);

	c1.setToken(tr);
	c2.setToken(ty);

	//System.out.println(c1.getNeighbor(Direction.RIGHT));

	System.out.println(c1.numberOfSameNeighbor(Direction.UP, 0));

    }
}
