import static java.util.Objects.requireNonNull;
import java.util.EnumMap;

class Grid {
    public static final String ANSI_RED    = "\u001B[31m";     // RED
    public static final String ANSI_YELLOW = "\u001B[33m";  // YELLOW
    public static final String ANSI_WHITE  = "\u001B[37m";   // WHITE
    
    private static final int WIDTH = 7;
    private static final int HEIGHT = 6;
    private Cell[] arrayNextEmptyCell; // contient la référance vers la prochaine cellule vide de chaque colonne

    public Grid(){
        this.arrayNextEmptyCell = new Cell[WIDTH];
    }

    public void initGridEmpty(){
	Cell[][] tempGrid2D = new Cell[HEIGHT][WIDTH];
	
	for(int i = 0; i < HEIGHT; i++) {
	    for(int j = 0; j < WIDTH; ++j) {
		tempGrid2D[i][j] = new Cell(Token.emptyToken);
	    }
	}
	
	for(int i = 0; i < HEIGHT; i++) {
	    for(int j = 0; j < WIDTH; ++j) {
		if (i > 0) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i-1][j], Direction.UP);
		}
		if (i < HEIGHT-1) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i+1][j], Direction.DOWN);
		}
		if (j > 0) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i][j-1], Direction.LEFT);		    
		}
		if (j < WIDTH-1) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i][j+1], Direction.RIGHT);
		}
	    }
	}

	for(int j = 0; j<WIDTH ; ++j) {
	    this.arrayNextEmptyCell[j] = tempGrid2D[HEIGHT-1][j] ;
	}
    }

    private void printNextEmptyCells() {
	for(int j = 0; j<WIDTH ; ++j) {
	    System.out.print(this.arrayNextEmptyCell[j] + " | ");
	}
	System.out.println();

    }

    private int valideColumn(int column) {
	if (column < 0 || column >= WIDTH) {
	    throw new IllegalArgumentException("column outOfBound");
	}
	return column;
    }

    private Cell getNextEmptyCellAt(int column) {
	return this.arrayNextEmptyCell[valideColumn(column)];
    }

    private void UpToNextEmptyCellAt(int column) {
	this.arrayNextEmptyCell[valideColumn(column)] =
	    this.getNextEmptyCellAt(column).getNeighbor(Direction.UP);
    }

    private Cell getTopCell(int column) {
	// Parcours jusqu'à la dernière cellule haute de la colonne i
	Cell top = this.getNextEmptyCellAt(column);
	while (top.getNeighbor(Direction.UP) != Cell.outOfBoundCell){
	    top = top.getNeighbor(Direction.UP);
	}
	return top;
    }


    public void printGrid() {

	char[][] tempArray = new char[HEIGHT][WIDTH];

	for(int i=0; i < WIDTH; ++i) {
	    Cell cellTemp = this.arrayNextEmptyCell[i];

	    cellTemp = getTopCell(i);
	    
	    int j = 0;
	    // Parcours jusqu'à la dernière cellule basse de la colonne i
	    while (cellTemp != Cell.outOfBoundCell) {
		if (cellTemp.getToken().getColor() == Color.RED) {
		    tempArray[j][i] = 'r';
		}
		else if (cellTemp.getToken().getColor() == Color.YELLOW) {
		    tempArray[j][i] = 'y';
		}
		else if (cellTemp.getToken().getColor() == Color.EMPTY) {
		    tempArray[j][i] = ' ';
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
}
