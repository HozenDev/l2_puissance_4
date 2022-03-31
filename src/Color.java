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

}
