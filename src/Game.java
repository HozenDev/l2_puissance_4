import java.util.Scanner;
import java.util.InputMismatchException;
import static java.util.Objects.requireNonNull;
import java.util.EnumMap;

class Game {
    private OptionGame optionGame;
    private Player[] arrayPlayer;
    private Grid grid;

    private boolean end;

    private final static int numberOfPlayers = 2;
    private final static int numberOfTokenToWin = 4;
    
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
    //								    //
    // fonction pour l'affichage début partie dans le constructeur  //
    //								    //
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
	game.play();
    }

    private Player getPlayerFromId(int id) {
	if (id < 0 || id > this.numberOfPlayers) {
	    throw new IllegalArgumentException("Bad player id");
	}
	return this.arrayPlayer[id];
    }

    private void play() {

	int currentPlayer = 0;
	boolean playerHasPlay;
	
	while (!this.end) {

	    this.grid.printGrid();

	    playerHasPlay = false;	    
	    currentPlayer = (currentPlayer+1)%this.numberOfPlayers;

	    int columnChosen = this.askForColumn();
	    
	    while (!playerHasPlay) {
		playerHasPlay = this.playAToken(this.getPlayerFromId(currentPlayer).getToken(),
						columnChosen);
	    }
	}
	System.out.println("Game Ended.");
	System.out.println("*************************");	
	this.grid.printGrid();
    }

    private int askForColumn() {
	int column = 0;
	while (true) {
	    try {
		System.out.println("Chosir une colonne : ");
		Scanner input = new Scanner(System.in);
		column = valideColumn(input.nextInt()-1);
		break;
	    }
	    catch (Exception e) {
		System.out.println("Colonne invalide, un entier en 1 et " + this.grid.getWidth());
	    }
	}
	return column;	
    }

    private int valideColumn(int column) {
	return this.grid.valideColumn(column);
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

	switch (this.hasWin(this.grid.getNextEmptyCellAt(column))) {
	case 1:
	    this.end = true;
	    Player winner = this.arrayPlayer[0]; 
	    for (int i=0;i<this.numberOfPlayers;i++) {
		if (token.getColor() == this.arrayPlayer[i].getColor()) {
		    winner = this.arrayPlayer[i];
		}
	    }
	    System.out.println("Gagnant est : " + winner);
	    break;
	case 0:
	    System.out.println("Egalité");
	    this.end = true;
	    break;
	}
	
	if (this.grid.getNextEmptyCellAt(column).getNeighbor(Direction.UP) != Cell.outOfBoundCell) {
	    this.grid.UpToNextEmptyCellAt(column);     
	}

	return true;
    }

    private int hasWin(Cell lastPlayed) {

	// -1 : Nobody win
	// 0 : Egality
	// 1 : Someone win
	
	if (lastPlayed == Cell.outOfBoundCell) {
	    System.out.println("-1");
	    return -1;
	}

	// Test egality
	
	boolean sat = true;
	
	for (int i = 0 ; i < this.grid.getWidth() ; i++) {
	    if (this.grid.getNextEmptyCellAt(i) != Cell.outOfBoundCell) {
		sat = false;
		break;
	    }
	}

	if (sat) return 0;

	// Test Win in 8 directions
	
	EnumMap<Direction, Direction> diagonales = Direction.getDiagonales();
	
	for (Direction d: Direction.values()) {
	    if (lastPlayed.numberOfSameNeighbor(d, 1) >= this.numberOfTokenToWin) {
		return 1;
	    }
	    if (lastPlayed.numberOfSameNeighbor(d, diagonales.get(d), 1) >=
		this.numberOfTokenToWin) {
		return 1;
	    }
	}

	return -1;
    } 
}
