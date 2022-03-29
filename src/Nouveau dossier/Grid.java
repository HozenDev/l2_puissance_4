class Grid {
    private static final int WEIGHT = 7;
    private static final int HEIGHT = 6; 
    private int [] arrayEmptyCell; // contient la référance vers la prochaine case vide de chaque colonne
    private Cell [][] globalCell; // map entiere

    public Grid grid(){
        this.arrayEmptyCell = new Cell[HEIGHT][WEIGHT];
        this.globalCell = new int[WEIGHT];
    }

    private initStartArrayemptyCell(){
        for(int i=0; i<WEIGHT; ++i) this.arrayEmptyCell[i] = 0;
    }

    
}