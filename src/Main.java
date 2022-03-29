
public class Main {
    public static void main(String[] args) {
        Grid grille = new Grid();

	Token tr = new Token(Color.RED);
	Token ty = new Token(Color.YELLOW);

	grille.initGridEmpty();

	grille.playAToken(tr, 0);
	grille.playAToken(ty, 0);
	grille.playAToken(ty, 0);
	grille.playAToken(tr, 0);
	grille.playAToken(ty, 0);
	grille.playAToken(tr, 0);
	grille.playAToken(ty, 0);
	grille.playAToken(ty, 0);	
	grille.playAToken(tr, 4);	

	grille.printGrid();
	//grille.printGrid();
    }
}
