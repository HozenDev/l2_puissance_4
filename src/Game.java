import java.util.Scanner;
import java.util.InputMismatchException;
import static java.util.Objects.requireNonNull;
import java.util.EnumMap;
import java.lang.Math;

class Game {

    private static final String ANSI_WHITE  = "\u001B[37m";   // WHITE
    private static final String ANSI_RED    = "\u001B[31m";     // RED    

    private OptionGame optionGame;
    private Player[] arrayPlayer;
    private Grid grid;

    private boolean end;

    private final static int numberOfPlayers = 2;
    private final static int numberOfTokenToWin = 4;

    private int iteration;

    private final Save sauvegarde;
    
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
	this.playAToken(new Token(Color.RED), 0);
	this.playAToken(new Token(Color.RED), 0);
	this.playAToken(new Token(Color.RED), 2);

	this.sauvegarde = new Save("../sauv");
	this.sauvegarde.write(this.grid, this.arrayPlayer[0], this.arrayPlayer[1]);
	String test = this.sauvegarde.read();
	this.grid.initGrid(test);
	this.initFromASauv(test);

	//************************//
	// Init Game State //
	//************************//		

	this.end = false;
	this.iteration = 0;
    }

    private void initFromASauv(String s) {

	/*****************/
	/** A finir     **/
	/*****************/
	
	StringBuilder gridTemplate = new StringBuilder();
	StringBuilder players = new StringBuilder();
	StringBuilder nextPlayerToPlay = new StringBuilder();

	String[] sSplit = s.split(";");

	int sizeGrid = this.grid.getWidth()*this.grid.getHeight();

	for (int i=0; i<sizeGrid; i++) {
	    gridTemplate.append(sSplit[i]);
	}

	for (int i=sizeGrid; i<sizeGrid+this.numberOfPlayers; i++) {
	    players.append(sSplit[i]);
	}	
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

    private Player nextPlayer(Player current) {
	int i;
	for (i=0; i<this.numberOfPlayers; i++) {
	    if (current == this.getPlayerFromId(i)) break;
	}
	return this.getPlayerFromId((i+1)%2);
    }

    private void play() {

	boolean playerHasPlay;
	Player currentPlayer = this.getPlayerFromId((int) Math.round(Math.random()));
	
	while (!this.end) {

	    this.iteration += 1;
	    
	    this.grid.print();	    

	    playerHasPlay = false;;
	    
	    while (!playerHasPlay) {
		System.out.print(currentPlayer + ", ");
		int columnChosen = this.askForColumn();
		playerHasPlay = this.playAToken(currentPlayer.getToken(), columnChosen);
	    }

	    if (!this.end) currentPlayer = nextPlayer(currentPlayer);
	}

	this.grid.print();

	// Print game ended
	System.out.println("*****************************");
	System.out.println("*" + ANSI_RED + "         Game Ended        " + ANSI_WHITE + "*");
	System.out.println("*****************************");

	// Print the winner of if pat
	if (this.iteration == this.grid.getWidth()*this.grid.getHeight())
	    System.out.println("Egalité");
	else System.out.println("Le gagnant est : " + currentPlayer);	
    }

    private int askForColumn() {
	int column = 0;
	while (true) {
	    try {
		System.out.print("choisissez une colonne : ");
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


	// Test if game ended by playing
	this.end = this.isEnd(this.grid.getNextEmptyCellAt(column));
	
	if (this.grid.getNextEmptyCellAt(column).getNeighbor(Direction.UP) != Cell.outOfBoundCell) {
	    this.grid.UpToNextEmptyCellAt(column);     
	}
	return true;
    }

    private boolean isEnd(Cell lastPlayed) {
	
	if (lastPlayed == Cell.outOfBoundCell) {
	    System.out.println("-1");
	    return false;
	}

	// Test egality
	if (this.iteration == this.grid.getWidth()*this.grid.getHeight()) return true;

	// Test Win in 8 directions
	EnumMap<Direction, Direction> diagonales = Direction.getDiagonales();
	
	for (Direction d: Direction.values()) {
	    if (lastPlayed.numberOfSameNeighbor(d, 1) >= this.numberOfTokenToWin) {
		return true;
	    }
	    if (lastPlayed.numberOfSameNeighbor(d, diagonales.get(d), 1) >=
		this.numberOfTokenToWin) {
		return true;
	    }
	}
	return false;
    } 
}
