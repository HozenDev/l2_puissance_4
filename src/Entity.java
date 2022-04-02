public enum Entity {

    LOCAL, IA;

    public static Entity of(String og) {
	Entity option = LOCAL;
	switch (og) {
	case "LOCAL":
	    option = LOCAL;
	    break;
	case "IA":
	    option = IA;
	    break;
	}
	return option;
    }
}
