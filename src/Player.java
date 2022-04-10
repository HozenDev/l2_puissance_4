/**
 * Represent a Player of a Game using Token
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public class Player {

    private final String pseudo;
    private final Token token;
    private final Entity gameplay;

    public Player(String pseudo, int indexPlayer, Entity e) {
	/**
	 * Player constructor
	 * 
	 * @param pseudo username of the player
	 * @param indexPlayer id of the player, decide its Token Color
	 * @param e Entity of the Player (LOCAL or IA)
	 */
	this.token = initToken(validePlayer(indexPlayer));
        this.pseudo = pseudo;
	this.gameplay = e;
    }

    private Token initToken(int id) {
	/**
	 * Initialisation of the Player Token : give the Token in function of index
	 *
	 * @param id index of player
	 * @return the Token of the Player
	 */
	switch (id) {
	case 0: return new Token(Color.RED);
	case 1: return new Token(Color.YELLOW);
	}
	return Token.emptyToken;
    }

    private int validePlayer(int id) {
	/**
	 * Validate the index of a Player
	 *
	 * @param id index to validate
	 * @exception IllegalArgumentException
	 * @return the valide index
	 */
	if (id < 0 || id >= Game.numberOfPlayers) {
	    throw new IllegalArgumentException("indexPlayer must equal 0 if first  player else 1.");
	}
	return id;
    }

    public Entity whatIs() {
	/**
	 * Give the Entity of the Player
	 * 
	 * @return Entity
	 */
	return this.gameplay;
    }

    public Color getColor() {
	/**
	 * Give the Color of the Token of the Player (Delegation on Token getColor())
	 *
	 * @return Color
	 */
	return this.token.getColor();
    }

    public Token getToken() {
	/**
	 * Give the reference of the Token of the Player
	 *
	 * @return Player's Token
	 */
	return this.token;
    }

    public String getUsername() {
	/**
	 * Give the username of the Player
	 *
	 * @return String corresponding to the username
	 */
	return this.pseudo;
    }

    @Override
    public String toString(){
	/**
	 * Give the String representation of the Player
	 *
	 * @return String format (Entity, String username, (Token's Color))
	 */
	return String.format("%s %s (%s)",
			     this.gameplay.toString(),
			     this.pseudo,
			     this.getColor());
    }
}
