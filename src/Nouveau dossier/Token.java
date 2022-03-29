class Token {
    private final Color colorToken;

    private Token(Color c) {
        this.colorToken = c;
    }
    private Color getColor(){
        return this.colorToken;
    }
    
    public String toString(){
        return this.colorToken.toString(); // delegation a la fonction toString de Color qui est par default son nom de var donc RED ou YELLOW
    }
}