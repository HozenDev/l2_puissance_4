class Grid {
    public static final String ANSI_RED = "\u001B[31m";     // RED
    public static final String ANSI_YELLOW = "\u001B[33m";  // YELLOW
    public static final String ANSI_WHITE = "\u001B[37m";   // WHITE
    
    private static final int WIDTH = 7;
    private static final int HEIGHT = 6;
    private Cell [] arrayNextEmptyCell; // contient la référance vers la prochaine cellule vide de chaque colonne

    public Grid grid(){
        this.arrayNextEmptyCell = new Cell[WIDTH];
    }


    public void initGridEmpty(){
	Cell[][] tempGrid2D = new Cell[HEIGHT][WIDTH];

	for(int i = 0; i < HEIGHT; i++) {
	    for(int j = 0; j < WIDTH; ++j) {
		tempGrid2D[i][j] = new Cell();
	    }
	}
	
	for(int i = 0; i < HEIGHT; i++) {
	    for(int j = 0; j < WIDTH; ++j) {
		if(i != HEIGHT-1) {
		    tempGrid2D[i][j].setNeighbor(UP,tempGrid2D[i+1][j]);
		}
		if(i != 0) {
		    tempGrid2D[i][j].setNeighbor(DOWN,tempGrid2D[i-1][j]);
		}
		if(j != 0) {
		    tempGrid2D[i][j].setNeighbor(LEFT,tempGrid2D[i][j-1]);
		}
		if(j != WIDTH-1) {
		    tempGrid2D[i][j].setNeighbor(RIGHT,tempGrid2D[i][j+1]);
		}
	    }
	}

	for(int i = 0; i<WIDTH ; ++i) {
	    this.arrayNextEmptyCell[i] = tempGrid2D[0][1] ;
	}
    }


    public void printGrid(){
	Color[][] tempArray = new String[HEIGHT][WIDTH];
	for(int i=0; i<WIDTH; ++i) {
	    Cell cellTemp = this.arrayNextEmptyCell[i];
	    while(cellTemp.getNeighbor(DOWN) != null){ // on se trouve sur la dernière cellule de la grille, la plus basse
		cellTemp = cellTemp.getNeighbor(DOWN);
	    }
	    int j = 0;
	    while(cellTemp.getNeighbor(UP).isEmpty()){ // on remplie le tableau jusqu'à avoir plus de jetons de couleur
		if(Color.RED == cellTemp.getToken()){
		    tempArray[j][i] = ANSI_RED;
		}
		else {
		    tempArray[j][i] = ANSI_YELLOW;
		}
		++j;
		cellTemp = cellTemp.getNeighbor(UP);
	    }
	    for( ; j < HEIGHT ; ++j) {
		tempArray[j][i] = ANSI_WHITE;
	    }
	}

	for(int i=0; i<HEIGHT; ++i) {
	    System.out.print("|");
	    for(int j=0; j<WIDTH; ++j) {
		if(tempArray[i][j] == ANSI_RED) {
		    System.out.print(ANSI_RED + "O" + ANSI_WHITE);
		}
		else if (tempArray[i][j] == ANSI_YELLOW) {
		    System.out.print(ANSI_YELLOW + "O" + ANSI_WHITE);
		}
		else {
		     System.out.print(" ");
		}
	    }
	    System.out.println();
	}
    }
        
}
