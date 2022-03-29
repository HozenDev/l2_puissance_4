
public class Main {
    public static void main(String[] args) {
        Grid grille = new Grid();

	Token t = new Token(Color.RED);
	
	grille.initGridEmpty();
	grille.printGrid();
	grille.play(t, 3);
	grille.printGrid();
    }
}
