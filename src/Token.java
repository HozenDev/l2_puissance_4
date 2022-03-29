class Token {
    private final Color colorToken;

    public Token(Color c) {
        this.colorToken = c;
    }
    public Color getColor(){
        return this.colorToken;
    }

    
    public String toString(){
        return this.colorToken.toString(); // delegation a la fonction toString de Color qui est par default son nom de var donc RED ou YELLOW
    }
}
