import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;
import static java.util.Objects.requireNonNull;
import java.util.EnumMap;
import java.lang.Math;

class Game {
    // const    
    public final static int numberOfPlayers = 2;
    public final static int numberOfTokenToWin = 4;

    // Gameplay attributs
    private Player[] arrayPlayer;
    private Grid grid;

    // Save
    private final Save save;

    // Game state attributs
    private boolean end;
    private int iteration;
    private String gameplay; // IA or LOCAL    
    
    private int currentPlayerId;
    private int lastMove; // represent the last column played

    private Game() {
		/**
		 * Game class constructor
		 */
		this.welcomeMessage();

		// Init Player physical representation
		this.arrayPlayer = new Player[numberOfPlayers];

		// Init Grid
		this.grid = new Grid();

		// Init random first Player
		this.currentPlayerId = (int) Math.round(Math.random());

		// Init Save 
		this.save = new Save("../sauv");
		this.askForLoadSave();

		// Init Game State
		this.end = false;
		this.iteration = 0;
    }

    private void askForLoadSave() {
		/**
		 * Ask the user if he wants to load an existing save
		 */
		if (!this.save.isEmpty()) {
			boolean done = false;
			while (!done) {
				System.out.println("Sauvegarde est existante, voulez-vous la charger ?");
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
		/**
		 * Initialisation of the Players
		 */
		this.initLocalPlayer(0) ;  // premier joueur toujours humain
		if(this.gameplay.equals("LOCAL")) initLocalPlayer(1);
		else createPlayer("Ordinateur", 1, Entity.IA);
    }

    private void loadSave(String saveText) {
		/**
		 * Initialisation of the game by an existing save
		 *
		 * @param saveText  string representing a save
		 */
		
		String[] saveSplit = saveText.split("&");

		if (saveSplit.length < this.numberOfPlayers+2) {
			this.save.delete();
			throw new IllegalArgumentException("Sauvegarde corrompue");
		}

		// Initialise the players //

		for (int i=0; i<this.numberOfPlayers; i++) {
			Entity og = Entity.of(saveSplit[i+1].split(" ")[0]);
			String playerName = saveSplit[i+1].split(" ")[1];
			this.createPlayer(playerName, i, og);
		}

		// Initialise the current player //

		this.currentPlayerId = Integer.parseInt(saveSplit[this.numberOfPlayers+1]);

		// Initialise the grid //

		Token[] tokenArray = new Token[Color.values().length];

		for (int i=0; i<this.numberOfPlayers; i++) {
			tokenArray[i] = this.getPlayerFromId(i).getToken();
		}

		tokenArray[Color.values().length-1] = Token.emptyToken;

		this.grid.loadGrid(saveSplit[0], tokenArray);
    }

    private void initLocalPlayer(int position) {
		/**
		 * Initialisation of a Player by his id
		 *
		 * @param position player position in his physical representation
		 */
		System.out.println("Veuillez saisir le pseudo du joueur " + position + " :");
		String username = (new Scanner(System.in)).nextLine();
		this.createPlayer(username, position, Entity.LOCAL);
    }    

    private void createPlayer(String name, int id, Entity og) {
		/**
		 * Create a player
		 *
		 * @param name name of the player
		 * @param id index of the player
		 * @param og what is the player (LOCAL or IA)
		 */
		this.arrayPlayer[id] = new Player(name, id, og);
    }

    //**************************************************************//
    //								    //
    // fonction pour l'affichage début partie dans le constructeur  //
    //								    //
    //**************************************************************//

    private void welcomeMessage() {
		/**
		 * Welcome Message
		 */
		System.out.println("Bienvenu au jeu du puissance 4.\n");
    }

    private void endMessage() {
		/**
		 * End Message
		 */
		// Print game ended
		System.out.println("*****************************");
		System.out.println("*" + Color.ansiColorOf("RED") +
						   "         Game Ended        "
						   + Color.ansiColorOf("WHITE") + "*");
		System.out.println("*****************************");

		// Print the winner or if pat
		if (this.isTie()) {
			this.save.delete();
			System.out.println("Egalité");
		}
		else if (this.hasWin()) {
			this.save.delete();
			System.out.println("Le gagnant est : " +
							   this.getPlayerFromId(this.currentPlayerId));
		}
    }

    private void printPartyChoice() {
		/**
		 * Print option game choice
		 */
		System.out.println("Vous avec 2 options de jeu :");
		System.out.println("-[1] 1 VS 1 avec 2 joueurs en local");
		System.out.println("-[2] 1 VS 1 contre une IA\n");
    }

    // Gameplay initialisation

    private int valideGameOption(int option) {
		/**
		 * Validation of game option
		 *
		 * @param option game option to validate
		 * @exception if not a valide game option
		 * @return valide game option
		 */
		if (option <= 0 || option > 2) throw new IllegalArgumentException();
		return option;
    }

    private void setOptionGame() {
		while (true) {
			try {
				printPartyChoice();
				System.out.println("A quel mode de jeu voulez-vous jouer ? ");
				int option = valideGameOption((new Scanner(System.in)).nextInt());
				if (option == 1) this.gameplay = "LOCAL";
				else this.gameplay = "IA";   
				break;
			}
			catch (IllegalArgumentException e) {
				System.out.println("Ce mode de jeu n'existe pas.\n");
			}
			catch (InputMismatchException e) {
				System.out.println("Erreur lors de la saisie," +
								   "veuillez saisir l'entier '1' ou '2'\n");
			}
		}
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
		System.out.println(
			Color.ansiColorOf(this.getPlayerFromId(this.currentPlayerId).getColor())
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
				int columnChosen = chooseAColumn(); 
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

    private int chooseAColumn() {
		if (this.getPlayerFromId(this.numberOfPlayers-1).whatIs() == Entity.IA) {
			if (this.currentPlayerId == this.numberOfPlayers-1) {
				return (new Random()).nextInt(7);		
			}
		}
		return this.askForInput();
    }

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
					this.save.write(this);
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
			catch (InputMismatchException e) {
				System.out.println("Ce choix n'existe pas.");
			}
		}
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
				catch (IllegalArgumentException e) {
					System.out.println("Colonne invalide, un entier en 1 et " +
									   this.grid.getWidth());
				}
			}
		}
		return column;
    }

    private int valideColumn(int column) {return this.grid.valideColumn(column);}

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
		this.lastMove = column;
		return true;
    }

    // Fonction de test de fin de partie

    private boolean isTie() {return this.iteration == this.grid.getSize();}

    private boolean hasWin() {
		Cell lastCellPlayed = this.grid.getNextEmptyCellAt(this.lastMove);
		if (lastCellPlayed.getToken() != Token.emptyToken);
		else lastCellPlayed = lastCellPlayed.getNeighbor(Direction.DOWN);
		return lastCellPlayed.check();
    }

    private void isEnd() {if (this.isTie() || this.hasWin()) this.end = true;}

    // toString()
    
    @Override
    public String toString() {
		return this.grid.toString() + "&"
			+ this.getPlayerFromId(0).toString() + "&"
			+ this.getPlayerFromId(1).toString() + "&"
			+ String.valueOf(this.currentPlayerId);
    }
}
