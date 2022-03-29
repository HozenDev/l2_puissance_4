class Player{
    private final String pseudo;
    private final Color colorPlayer;

    public Player(String pseudo, int indexPlayer) {                 // indexPlayer == 0 alors premier joueur donc color RED
        if      (1 == indexPlayer) this.colorPlayer = Color.RED;
        else if (0 == indexPlayer) this.colorPlayer = Color.YELLOW;
        else throw new IllegalArgumentException("indexPlayer must equal 0 if first  player else 1.");

        this.pseudo = pseudo;
    }
    @Override
    public String toString(){
        return String.format("%s, color : %s", this.pseudo, this.colorPlayer);
    }

    public static void main(String [] args){
        Player thibault = new Player("Thibault" , 1);
        System.out.println(thibault);
    }
}