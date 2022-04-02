class Player {

    private static final int MAX_PLAYERS = 2;

    private final String pseudo;
    private final Token token;
    private final Entity gameplay;

    /* Constructeur */

    public Player(String pseudo, int indexPlayer, Entity og) {
	this.token = initToken(validePlayer(indexPlayer));
        this.pseudo = pseudo;
	this.gameplay = og;
    }

    /* Initialisation du jeton */

    private Token initToken(int id) {
	switch (id) {
	case 0: return new Token(Color.RED);
	case 1: return new Token(Color.YELLOW);
	}
	return Token.emptyToken;
    }

    /* Vérificateurs */

    private int validePlayer(int id) {
	if (id < 0 || id >= this.MAX_PLAYERS) {
	    throw new IllegalArgumentException("indexPlayer must equal 0 if first  player else 1.");
	}
	return id;
    }

    /* Accesseurs */

    public Entity whatIs() {
	return this.gameplay;
    }

    public Color getColor() {
	return this.token.getColor();
    }

    public Token getToken() {
	return this.token;
    }

    public String getUsername() {
	return this.pseudo;
    }

    /* toString */

    @Override
    public String toString(){
	return String.format("%s %s (%s)",
			     this.gameplay.toString(),
			     this.pseudo,
			     this.getColor());
    }
}
