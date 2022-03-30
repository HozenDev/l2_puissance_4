import java.util.Scanner;
import java.util.InputMismatchException;


class Game {
	private OptionGame optionGame;
	private Player[] arrayPlayer;

	public Game() {
		welcomeMessage();
		setOptionGame();
		this.arrayPlayer = new Player[2];

		initPlayer(0) ;  // premier joueur toujours humain

		if(this.optionGame == OptionGame.LOCAL) {
			initPlayer(1);
		}
		else {
			this.arrayPlayer[1] = new Player("Ordinateur", 1);
		}

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
		
    public  static void main(String[] args) {
    	Game game = new Game();
    	System.out.println(game);
    }
}
