/**
 * Represent colors globally
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public enum Color {

    RED, YELLOW, EMPTY;

    public static Color colorOf(String colorString) {
	/**
	 * Give the color for a specify String
	 *
	 * @param colorString the color String representation you need
	 * @return the Color of the colorString
	 */
	switch (colorString) {
	case "RED":
	    return Color.RED;
	case "YELLOW":
	    return Color.YELLOW;
	}
	return Color.EMPTY;
    }

    public static String ansiColorOf(Color c) {
	/**
	 * Give the ansi color corresponding to a Color given
	 *
	 * @param c Color source
	 * @return String ansi representation
	 */
	switch (c) {
	case RED :
	    return ansiColorOf("RED");
	case YELLOW :
	    return ansiColorOf("YELLOW");
	}
	return ansiColorOf("WHITE");
    }

    public static String ansiColorOf(String c) {
	/**
	 * Give the ansi color corresponding of a String given
	 *
	 * @param c String color source
	 * @return String ansi representation
	 */
	switch (c) {
	case "RED":
	    return "\u001B[31m"; // RED
	case "YELLOW":
	    return "\u001B[33m"; // YELLOW
	case "BLUE":
	    return "\u001B[36m"; // BLUE
	case "GREEN":
	    return "\u001B[32m"; // GREEN
	case "WHITE":
	    return "\u001B[37m"; // WHITE
	}
	System.out.println("There is no color corresponding to the argument: white is returning");
	return ansiColorOf("WHITE");
    }
}
