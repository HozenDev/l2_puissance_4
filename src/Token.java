/**
 * Represent a Token of Puissance 4
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public class Token {

    public static final Token emptyToken = new Token(Color.EMPTY);
    
    private final Color colorToken;
    
    public Token(Color c) {
	/**
	 * Token constructor
	 *
	 * @param c Color of the Token
	 */
	this.colorToken = c;
    }

    public Color getColor(){
	/**
	 * Give the Color of the Token
	 *
	 * @return Color
	 */
        return this.colorToken;
    }

    @Override
    public String toString(){
	/**
	 * Give the String representation of a Token
	 *
	 * @return String representing the Color of the Token
	 */
        return this.colorToken.toString();
    }
}
