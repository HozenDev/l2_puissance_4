class Token {

    public static final Token emptyToken = new Token(Color.EMPTY);
    
    private final Color colorToken;
    
    public Token(Color c) {
	this.colorToken = c;
    }

    public Color getColor(){
        return this.colorToken;
    }
    
    public String toString(){
        return this.colorToken.toString();
    }
}
