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

     @Override
    public int compareTo(Player other) {
        if (other.getClass().equals(this.getClass())) {
            return String.compare(this.pseudo, other.pseudo);
        }
        return -1 * other.compareTo(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (o == null) { return false; }
        if (!(o instanceof Player)) { return false; }
        return this.compareTo((Player) o) == 0;
    }

    @Override
    public int hashCode() {
        return String.hashCode(this.pseudo());
}