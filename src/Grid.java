class Grid {
    private static final int WEIGHT = 7;
    private static final int HEIGHT = 6; 
    private Cell [] arrayEmptyCell; // contient la référance vers la prochaine case vide de chaque colonne
    private Cell [][] globalCell; // map entiere

    public Grid(){
        this.arrayEmptyCell = new Cell[HEIGHT];
        this.globalCell = new Cell[WEIGHT][HEIGHT];
    }
}
