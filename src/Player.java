class Player{
    private final String pseudo;
    private final Token token;

    public Player(String pseudo, int indexPlayer) {// indexPlayer == 0 alors premier joueur donc color RED
        if      (1 == indexPlayer) this.token = new Token(Color.RED);
        else if (0 == indexPlayer) this.token = new Token(Color.YELLOW);
        else throw new IllegalArgumentException("indexPlayer must equal 0 if first  player else 1.");

        this.pseudo = pseudo;
    }
    @Override
    public String toString(){
        return String.format("%s, color : %s", this.pseudo, this.getColor());
    }

    public Color getColor() {
	return this.token.getColor();
    }

    public Token getToken() {
	return this.token;
    }
}
