import java.util.Scanner;
import java.util.InputMismatchException;


class Game {
    private OptionGame optionGame;
    private Player[] arrayPlayer;
    private Grid grid;

    private boolean end;

    private final static numberOfPlayers = 2;
    
    public Game() {
	welcomeMessage();
	setOptionGame();
	this.arrayPlayer = new Player[numberOfPlayers];

	initPlayer(0) ;  // premier joueur toujours humain

	if(this.optionGame == OptionGame.LOCAL) {
	    initPlayer(1);
	}
	else {
	    this.arrayPlayer[1] = new Player("Ordinateur", 1);
	}

	//************************//
	// Init Grid //
	//************************//	
	
	this.grid = new Grid();

	//************************//
	// Init Game State //
	//************************//		

	this.end = false;
    }


    //**************************************************************//
    //																//
    	// fonction pour l'affichage début partie dans le constructeur  //
    //																//
    //**************************************************************//
    private void welcomeMessage() {
	System.out.println("Bienvenu au jeu du puissance 4.\n");
    }

    private void printPartyChoice() {
	System.out.println("Vous avec 2 options de jeu :");
	System.out.println("-[1] 1 VS 1 avec 2 joueurs en local");
	System.out.println("-[2] 1 VS 1 contre une IA\n");
    }

    private void correctInput() throws IllegalArgumentException {
	Scanner input = new Scanner(System.in);
	int option ;
	printPartyChoice();
	System.out.println("A quel mode de jeu voulez-vous jouer ? ");
	option = input.nextInt();
	if ( option != 1 && option != 2) 
	    throw new IllegalArgumentException();
	else {
	    if (option == 1)  {
		this.optionGame = OptionGame.LOCAL;
	    }
	    else {
		this.optionGame = OptionGame.IA;
	    }
	}
    }

    private void setOptionGame() {
		
	while (true) {
	    try {
		correctInput();
		break;
	    }

	    catch (IllegalArgumentException e) {
		System.out.println("Erreur lors de la saisie, veuillez saisir l'entier '1' ou '2'\n");
	    }
			
	    catch (InputMismatchException e) {
		System.out.println("Erreur lors de la saisie, veuillez saisir l'entier '1' ou '2'\n");
	    }
	}

    }

    @Override
    public String toString() {
	return arrayPlayer[0].toString() + " " + arrayPlayer[1].toString() + " " + optionGame.toString();
    }

    private void initPlayer(int position) {
	Scanner inputPseudo = new Scanner(System.in);
	System.out.println("Veuillez saisir le pseudo du joueur " + position + " :");
	String pseudo = inputPseudo.nextLine();
	this.arrayPlayer[position] = new Player(pseudo, position);
    }
		
    public static void run() {
    	Game game = new Game();
    	System.out.println(game);
    }

    public boolean playAToken(Token token, int column) {
	if (token == Token.emptyToken) {
	    throw new IllegalArgumentException("You can't play an empty Token");
	}
	if (this.grid.getNextEmptyCellAt(column).getToken() != Token.emptyToken) {
	    System.out.println("La colonne est pleine");
	    return false;
	}
	
	this.grid.getNextEmptyCellAt(column).setToken(requireNonNull(token));

	int win = this.grid.hasWin(this.grid.getNextEmptyCellAt(column));
	if (win == 1) {

	    this.end = true;

	    Player winner; 
	    
	    for (int i=0;i<this.numberOfPlayers;i++) {
		if (token.getColor() == this.arrayPlayer[i].getColor()) {
		    winner = this.arrayPlayer[i];
		}
	    }
	    
	    System.out.println("Gagnant est : " + winner);
	}
	
	if (this.grid.getNextEmptyCellAt(column).getNeighbor(Direction.UP) != Cell.outOfBoundCell) {
	    this.grid.UpToNextEmptyCellAt(column);     
	}

	return true;
    }

    private int hasWin(Cell lastPlayed) {
	if (lastPlayed == Cell.outOfBoundCell) {
	    System.out.println("-1");
	    return -1;
	}

	/***********************************/
	/** Vérifier si partie égalité !! **/
	/***********************************/

	EnumMap<Direction, Direction> diagonales = Direction.getDiagonales();
	
	for (Direction d: Direction.values()) {
	    if (lastPlayed.numberOfSameNeighbor(d, 1) >= 4) {
		System.out.println("1");
		return 1;
	    }
	    if (lastPlayed.numberOfSameNeighbor(d, diagonales.get(d), 1) >= 4 ) {
		System.out.println("diago 1");
		return 1;
	    }
	}
	System.out.println("-1");
	return -1;
    }    
}
