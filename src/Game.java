import java.util.Scanner;
import java.util.InputMismatchException;
import static java.util.Objects.requireNonNull;
import java.util.EnumMap;
import java.lang.Math;

class Game {

    private OptionGame optionGame;
    private Player[] arrayPlayer;
    private Grid grid;

    private boolean end;

    private final static int numberOfPlayers = 2;
    private final static int numberOfTokenToWin = 4;

    private int iteration;

    private final Save save;

    private int currentPlayerId;
    private Cell lastCellPlayed;

    private Game() {
	this.welcomeMessage();

	this.arrayPlayer = new Player[numberOfPlayers];

	//************************//
	// Init Grid //
	//************************//

	this.lastCellPlayed = Cell.outOfBoundCell;
	this.grid = new Grid();

	//************************//
	// Init Save //
	//************************//

	this.currentPlayerId = (int) Math.round(Math.random());

	this.save = new Save("../sauv");
	this.askForLoadSave();

	//************************//
	// Init Game State //
	//************************//

	this.end = false;
	this.iteration = 0;
    }

    private void askForLoadSave() {
	if (!this.save.isEmpty()) {
	    boolean done = false;
	    while (!done) {
		System.out.println("Une sauvegarde est existante, voulez-vous la charger ?");
		System.out.println("[1:Oui][2:Non][3:Supprimer]");
		Scanner input = (new Scanner(System.in));
		try {
		    switch (input.nextInt()) {
		    case 1:
			this.loadSave(this.save.read());
			break;
		    case 3:
			this.save.delete();
			this.setOptionGame();
			this.askForPlayers();
			break;
		    default:
			this.setOptionGame();
			this.askForPlayers();
			break;
		    }
		    done = true;
		}
		catch (InputMismatchException e) {
		    System.out.println("Ce choix n'existe pas");
		}
	    }
	}
	else {
	    this.setOptionGame();
	    this.askForPlayers();
	}
    }

    private void askForPlayers() {
	this.initPlayer(0) ;  // premier joueur toujours humain
	if(this.optionGame == OptionGame.LOCAL) initPlayer(1);
	else createPlayer("Ordinateur", 1);
    }

    private void loadSave(String s) {

	String[] sSplit = s.split("&");

	if (sSplit.length < this.numberOfPlayers+2) {
	    this.save.delete();
	    throw new IllegalArgumentException("Sauvegarde corrompue");
	}

	// Initialise the players //

	for (int i=0; i<this.numberOfPlayers; i++) {
	    String playerName = sSplit[i+1].split(" ")[0];
	    this.createPlayer(playerName, i);
	}

	// Initialise the current player //

	this.currentPlayerId = Integer.parseInt(sSplit[this.numberOfPlayers+1]);

	// Initialise the grid //

	Token[] tokenArray = new Token[Color.values().length];

	for (int i=0; i<this.numberOfPlayers; i++) {
	    tokenArray[i] = this.getPlayerFromId(i).getToken();
	}

	tokenArray[Color.values().length-1] = Token.emptyToken;

	this.grid.loadGrid(sSplit[0], tokenArray);
    }

    private void createPlayer(String name, int id) {
	this.arrayPlayer[id] = new Player(name, id);
    }

    //**************************************************************//
    //								    //
    // fonction pour l'affichage début partie dans le constructeur  //
    //								    //
    //**************************************************************//

    private void welcomeMessage() {
	System.out.println("Bienvenu au jeu du puissance 4.\n");
    }

    private void endMessage() {
	// Print game ended
	System.out.println("*****************************");
	System.out.println("*" + Color.ansiColorOf("RED") +
			   "         Game Ended        "
			   + Color.ansiColorOf("WHITE") + "*");
	System.out.println("*****************************");

	// Print the winner or if pat
	if (this.isPat()) {
	    this.save.delete();
	    System.out.println("Egalité");
	}
	else if (this.hasWin()) {
	    this.save.delete();
	    System.out.println("Le gagnant est : " + this.getPlayerFromId(this.currentPlayerId));
	}
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

    private void initPlayer(int position) {
	Scanner inputPseudo = new Scanner(System.in);
	System.out.println("Veuillez saisir le pseudo du joueur " + position + " :");
	String pseudo = inputPseudo.nextLine();
	this.createPlayer(pseudo, position);
    }

    // Fonction de démarrage

    public static void run() {
    	Game game = new Game();
	game.play();
    }

    // Fonction de gestions des joueurs

    private Player getPlayerFromId(int id) {
	if (id < 0 || id > this.numberOfPlayers) {
	    throw new IllegalArgumentException("Bad player id");
	}
	return this.arrayPlayer[id];
    }

    private Player nextPlayer(Player current) {
	this.currentPlayerId = (this.currentPlayerId+1)%this.numberOfPlayers;
	return this.getPlayerFromId(this.currentPlayerId);
    }

    private void printCurrentPlayer() {
	System.out.println(Color.ansiColorOf(this.getPlayerFromId(this.currentPlayerId).getColor())
			   + "["
			   + this.getPlayerFromId(this.currentPlayerId) + "]"
			   + Color.ansiColorOf("WHITE"));
    }

    // Fonction pricipale

    private void play() {

	boolean playerHasPlay;
	Player currentPlayer = this.getPlayerFromId(this.currentPlayerId);

	while (!this.end) {

	    this.iteration += 1;
	    this.grid.print();
	    playerHasPlay = false;

	    while (!playerHasPlay) {
		this.printCurrentPlayer();
		System.out.println("[m: Menu]");
		int columnChosen = this.askForInput();
		if (this.end) break;
		playerHasPlay = this.playAToken(currentPlayer.getToken(),
						columnChosen);
	    }

	    this.isEnd();

	    if (!this.end) {
		currentPlayer = nextPlayer(currentPlayer);
	    }
	}
	this.grid.print();
	this.endMessage();
    }

    // fonction du menu

    private void menu() {
	boolean done = false;

	while (!done) {
	    System.out.println("[1:Continuer][2:Sauvegarder][3:Quitter le jeu]");
	    System.out.print("Choississez une option : ");
	    Scanner input = new Scanner(System.in);
	    try {
		switch (input.nextInt()) {
		case 1:
		    done = true;
		    break;
		case 2:
		    this.save.write(this.grid.toString(),
				    this.arrayPlayer[0].toString(),
				    this.arrayPlayer[1].toString(),
				    String.valueOf(this.currentPlayerId));
		    break;
		case 3:
		    done = true;
		    this.end = true;
		    break;
		default:
		    done = true;
		    break;
		}
	    }
	    catch (Exception e) {
		System.out.println("Ce choix n'existe pas.");
	    }
	}
	System.out.println("Vous avez quitté le menu.");

    }

    // Fonctions de demande de saisi

    private int askForInput() {
	int column = 0;
	while (true) {
	    System.out.print("Choisissez une colonne : ");
	    Scanner input = new Scanner(System.in);
	    String sInput = input.nextLine();
	    if (sInput.equals("m")) {
		this.menu();
		if (this.end) break;
	    }
	    else {
		try {
		    column = valideColumn(Integer.parseInt(sInput)-1);
		    break;
		}
		catch (Exception e) {
		    System.out.println("Colonne invalide, un entier en 1 et " +
				       this.grid.getWidth());
		}
	    }
	}
	return column;
    }

    private int valideColumn(int column) {
	return this.grid.valideColumn(column);
    }

    // Fonction de gameplay

    public boolean playAToken(Token token, int column) {
	if (token == Token.emptyToken) {
	    throw new IllegalArgumentException("You can't play an empty Token");
	}

	Cell played = this.grid.getNextEmptyCellAt(column);

	if (played.getToken() != Token.emptyToken) {
	    System.out.println("La colonne est pleine");
	    return false;
	}

	played.setToken(requireNonNull(token));

	if (played.getNeighbor(Direction.UP) != Cell.outOfBoundCell) {
	    this.grid.UpToNextEmptyCellAt(column);
	}

	this.lastCellPlayed = played;

	return true;
    }

    // Fonction de test de fin de partie

    private boolean isPat() {
	return this.iteration == this.grid.getWidth()*this.grid.getHeight();
    }

    private boolean hasWin() {
	EnumMap<Direction, Direction> diagonales = Direction.getDiagonales();

	for (Direction d: Direction.values()) {
	    if (this.lastCellPlayed.numberOfSameNeighbor(d, 1) >= this.numberOfTokenToWin) {
		return true;
	    }
	    if (this.lastCellPlayed.numberOfSameNeighbor(d, diagonales.get(d), 1) >=
		this.numberOfTokenToWin) {
		System.out.println("text 2");
		return true;
	    }
	}
	return false;
    }

    private void isEnd() {
	// Test egality
	if (this.isPat()) this.end = true;

	// Test Win in 8 directions
	if (this.hasWin()) this.end = true;
    }

    @Override
    public String toString() {
	return arrayPlayer[0].toString() +
	    " " +
	    arrayPlayer[1].toString() +
	    " " +
	    optionGame.toString();
    }
}
