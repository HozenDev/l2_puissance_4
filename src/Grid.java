import static java.util.Objects.requireNonNull;
import java.util.EnumMap;

class Grid {
    private static final String ANSI_RED    = "\u001B[31m";     // RED
    private static final String ANSI_YELLOW = "\u001B[33m";  // YELLOW
    private static final String ANSI_WHITE  = "\u001B[37m";   // WHITE
    private static final String ANSI_BLUE   = "\u001B[36m";
    private static final String ANSI_GREEN  = "\u001B[32m";
    
    private static final int WIDTH = 7;
    private static final int HEIGHT = 6;
    private Cell[] arrayNextEmptyCell; // contient la référance vers la prochaine cellule vide de chaque colonne

    public Grid(){
        this.arrayNextEmptyCell = new Cell[WIDTH];
	this.initGrid();
    }

    public int getWidth() {
	return this.WIDTH;
    }

    public int getHeight() {
	return this.HEIGHT;
    }

    public int getSize() {
	return this.getWidth()*this.getHeight();
    }

    public void initGrid(){
	
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

    public int valideColumn(int column) {
	if (column < 0 || column >= WIDTH) {
	    throw new IllegalArgumentException("column outOfBound");
	}
	return column;
    }

    public Cell getNextEmptyCellAt(int column) {
	return this.arrayNextEmptyCell[valideColumn(column)];
    }

    public void UpToNextEmptyCellAt(int column) {
	this.arrayNextEmptyCell[valideColumn(column)] =
	    this.getNextEmptyCellAt(column).getNeighbor(Direction.UP);
    }

    public Cell getTopCellAt(int column) {
	// Parcours jusqu'à la dernière cellule haute de la colonne i
	Cell top = this.getNextEmptyCellAt(column);
	while (top.getNeighbor(Direction.UP) != Cell.outOfBoundCell){
	    top = top.getNeighbor(Direction.UP);
	}
	return top;
    }

    private void printGrid() {

	char[][] tempArray = new char[HEIGHT][WIDTH];

	for(int i=0; i < WIDTH; ++i) {
	    Cell cellTemp = this.arrayNextEmptyCell[i];

	    cellTemp = this.getTopCellAt(i);
	    
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

	System.out.print(ANSI_BLUE);
	
	for(int i=0; i<HEIGHT; ++i) {
	    for(int j=0; j<WIDTH; ++j) {
		System.out.print("+---");
	    }
	    System.out.println("+");
	    System.out.print("|");
	    for(int j=0; j<WIDTH; ++j) {
		if(tempArray[i][j] == 'r') {
		    System.out.print(ANSI_RED + " O " + ANSI_BLUE);
		}
		else if (tempArray[i][j] == 'y') {
		    System.out.print(ANSI_YELLOW + " O " + ANSI_BLUE);
		}
		else {
		    System.out.print("   ");
		}
		System.out.print("|");
	    }
	    System.out.println();	    
	}
	for(int j=0; j<WIDTH; ++j) {
	    System.out.print("+---");
	}
	System.out.println("+" + ANSI_WHITE);
    }

    private void printAvailableColumn() {

	System.out.print(ANSI_BLUE);

	for(int j=0; j<WIDTH; ++j) {
	    System.out.print("+-^-");
	}
	System.out.println("+");
	
	for(int j=0; j<WIDTH; ++j) {
	    if (this.getNextEmptyCellAt(j).getColor() != Color.EMPTY) {
		System.out.print("| X ");
	    }
	    else {
		System.out.print("| " + ANSI_GREEN + (j+1) + ANSI_BLUE + " ");		
	    }
	}
	System.out.println("|");

	for(int j=0; j<WIDTH; ++j) {
	    System.out.print("+---");
	}
	System.out.println("+" + ANSI_WHITE);
    }

    public void print() {

	System.out.println();	
	this.printGrid();
	System.out.println();	
	this.printAvailableColumn();
	System.out.println();	
     
    }

    @Override
    public String toString() {

	StringBuilder s = new StringBuilder();

	Cell c;
	
	for (int i=0; i<WIDTH; i++) {
	    c = this.getTopCellAt(i);

	    for(int j=0; j<HEIGHT; j++) {
		
		s.append(c.toString()+";");
		c = c.getNeighbor(Direction.DOWN);		
	    }
	}

	return s.toString();
    }

    public void loadGrid(String schema, Token[] tokenOfPlayers) {

	this.initGrid();

	Cell current;

	String[] cells = schema.split(";");

	for (int i=0; i<WIDTH; i++) {
	    current = this.getTopCellAt(i);

	    for (int j=0; j<HEIGHT; j++) {
		for (int k=0; k<Color.values().length; k++) {
		    if (tokenOfPlayers[k].getColor() == Color.colorOf(cells[j+i*HEIGHT]))
		    {
			current.setToken(tokenOfPlayers[k]);
			if (tokenOfPlayers[k] != Token.emptyToken) this.UpToNextEmptyCellAt(i);
			break;
		    }
		}
		current = current.getNeighbor(Direction.DOWN);
	    }
	}
    }
}
