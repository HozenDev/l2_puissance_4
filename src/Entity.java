/**
 * Logical representation of a Player (IA, LOCAL(human))
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public enum Entity {

    LOCAL, IA;

    public static Entity of(String e) {
	/**
	 * Give the Entity corresponding to the given String
	 *
	 * @param e name of the entity
	 * @return the Entity of the string
	 */
	Entity option = LOCAL;
	switch (e) {
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
