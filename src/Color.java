public enum Color {

    RED, YELLOW, EMPTY;

    public static Color colorOf(String colorString) {
	switch (colorString) {
	case "RED":
	    return Color.RED;
	case "YELLOW":
	    return Color.YELLOW;
	}
	return Color.EMPTY;
    }

    public static String ansiColorOf(Color c) {
	switch (c) {
	case RED :
	    return ansiColorOf("RED");
	case YELLOW :
	    return ansiColorOf("YELLOW");
	}
	return ansiColorOf("WHITE");
    }

    public static String ansiColorOf(String c) {
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
