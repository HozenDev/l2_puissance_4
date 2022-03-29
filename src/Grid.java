class Grid {
    public static final String ANSI_RED = "\u001B[31m";     // RED
    public static final String ANSI_YELLOW = "\u001B[33m";  // YELLOW
    public static final String ANSI_WHITE = "\u001B[37m";   // WHITE
    
    private static final int WIDTH = 7;
    private static final int HEIGHT = 6;
    private Cell [] arrayNextEmptyCell; // contient la référance vers la prochaine cellule vide de chaque colonne


    public Grid(){
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
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i+1][j], Direction.UP);
		}
		if(i != 0) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i-1][j], Direction.DOWN);
		}
		if(j != 0) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i][j-1], Direction.LEFT);
		}
		if(j != WIDTH-1) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i][j+1], Direction.RIGHT);
		}
	    }
	}

	for(int j = 0; j<WIDTH ; ++j) {
	    this.arrayNextEmptyCell[j] = tempGrid2D[0][j] ;
	}
    }


    public void printGrid(){

	System.out.println("****************************");

	char[][] tempArray = new char[HEIGHT][WIDTH];

	for(int i=0; i<WIDTH; ++i) {
	    Cell cellTemp = this.arrayNextEmptyCell[i];
		
	    while (cellTemp.getNeighbor(Direction.UP) != Cell.outOfBoundCell){ // on se trouve sur la dernière cellule de la grille, la plus basse
		cellTemp = cellTemp.getNeighbor(Direction.UP);
		//System.out.println(cellTemp + " " + cellTemp.getNeighbor(Direction.UP));
	    }

	    int j =0; //////////
	    while(cellTemp.getNeighbor(Direction.DOWN) != Cell.outOfBoundCell){ // on remplie le tableau jusqu'à avoir plus de jetons de couleur
	    	if(cellTemp.getToken() == null){
		    tempArray[j][i] = ' ';
	    	}
	    	else if(Color.RED == cellTemp.getToken().getColor()){
	    	    tempArray[j][i] = 'r';
	    	}
	    	else {
	    	    tempArray[j][i] = 'y';
	    	}
	    	++j;
	    	cellTemp = cellTemp.getNeighbor(Direction.DOWN);
	    }
	}


	for(int i=0; i<HEIGHT; ++i) {
	    System.out.print("|");
	    for(int j=0; j<WIDTH; ++j) {
		if(tempArray[i][j] == 'r') {
		    System.out.print(ANSI_RED + "O" + ANSI_WHITE);
		}
		else if (tempArray[i][j] == 'y') {
		    System.out.print(ANSI_YELLOW + "O" + ANSI_WHITE);
		}
		else {
		    System.out.print(" ");
		}
		System.out.print("|");
	    }
	    System.out.println();
	}
    }


    public boolean play(Token token, int column) {
	if(token == null) {
	    throw new IllegalArgumentException("Token is null");
	}
	this.arrayNextEmptyCell[column].setToken(token);
        this.arrayNextEmptyCell[column] = this.arrayNextEmptyCell[column].getNeighbor(Direction.UP);
	return true;
    }
}
