import static java.util.Objects.requireNonNull;
import java.util.EnumMap;

/**
 * Logical representation of a board of the Game of Puissance 4
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public class Grid {

    private static final int WIDTH = 7;
    private static final int HEIGHT = 6;

    private Cell[] arrayNextEmptyCell; /* contient la référance vers la prochaine cellule
					  vide de chaque colonne */
    /* Constructeurs */

    public Grid() {
	/**
	 * Grid constructor
	 */
        this.arrayNextEmptyCell = new Cell[this.getWidth()];
	this.initGrid();
    }

    public int getWidth() {return this.WIDTH;}

    public int getHeight() {return this.HEIGHT;}

    public int getSize() {return this.getWidth()*this.getHeight();}

    public void initGrid() {
	/**
	 * Initialisation of Cell of the Grid with their 4 neighbor
	 */
	Cell[][] tempGrid2D = new Cell[this.getHeight()][this.getWidth()];
	for(int i = 0; i < this.getHeight(); i++) {
	    for(int j = 0; j < this.getWidth(); ++j) {
		tempGrid2D[i][j] = new Cell(Token.emptyToken);
	    }
	}
	for(int i = 0; i < this.getHeight(); i++) {
	    for(int j = 0; j < this.getWidth(); ++j) {
		if (i > 0) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i-1][j], Direction.UP);
		}
		if (i < this.getHeight()-1) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i+1][j], Direction.DOWN);
		}
		if (j > 0) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i][j-1], Direction.LEFT);
		}
		if (j < this.getWidth()-1) {
		    tempGrid2D[i][j].setNeighbor(tempGrid2D[i][j+1], Direction.RIGHT);
		}
	    }
	}
	for(int j = 0; j<this.getWidth() ; ++j) {
	    this.arrayNextEmptyCell[j] = tempGrid2D[this.getHeight()-1][j] ;
	}
    }

    public int valideColumn(int column) {
	/**
	 * Verify the validity of a column (range of width)
	 *
	 * @param column the column to verify
	 * @exception IllegalArgumentException
	 * @return the valide column
	 */
	if (column < 0 || column >= this.getWidth()) {
	    throw new IllegalArgumentException("column outOfBound");
	}
	return column;
    }

    public Cell getNextEmptyCellAt(int column) {
	/**
	 * Return the next empty cell in the specify column, if the column is full, return
	 * the top Cell of the column in the grid
	 * 
	 * @param column the column of the Grid where is the Cell
	 * @return the Cell corresponding to the given column
	 */
	return this.arrayNextEmptyCell[valideColumn(column)];
    }

    public void UpToNextEmptyCellAt(int column) {
	/**
	 * Update the next EMPTY Cell in the column
	 *
	 * @param column the column of the Grid where is the Cell
	 */
	if (this.getNextEmptyCellAt(valideColumn(column)).getNeighbor(Direction.UP)
	    != Cell.outOfBoundCell) {
	    this.arrayNextEmptyCell[column] =
		this.getNextEmptyCellAt(column).getNeighbor(Direction.UP);	    
	}
    }

    public Cell getTopCellAt(int column) {
	/**
	 * Return the top Cell in the specify column
	 *
	 * @param column the column to get the top Cell
	 * @result the top Cell of the column
	 */
	// Parcours jusqu'à la dernière cellule haute de la colonne i
	Cell top = this.getNextEmptyCellAt(column);
	while (top.getNeighbor(Direction.UP) != Cell.outOfBoundCell){
	    top = top.getNeighbor(Direction.UP);
	}
	return top;
    }

    private void printGrid() {
	/**
	 * Pretty print of the grid with ansi color corresponding to the tokens in it
	 */
	char[][] tempArray = new char[this.getHeight()][this.getWidth()];
	for(int i=0; i < this.getWidth(); ++i) {
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

	System.out.print(Color.ansiColorOf("BLUE"));

	for(int i=0; i<this.getHeight(); ++i) {
	    for(int j=0; j<this.getWidth(); ++j) {
		System.out.print("+---");
	    }
	    System.out.println("+");
	    System.out.print("|");
	    for(int j=0; j<this.getWidth(); ++j) {
		if(tempArray[i][j] == 'r') {
		    System.out.print(Color.ansiColorOf("RED") +
				     " O " +
				     Color.ansiColorOf("BLUE"));
		}
		else if (tempArray[i][j] == 'y') {
		    System.out.print(Color.ansiColorOf("YELLOW") +
				     " O " +
				     Color.ansiColorOf("BLUE"));
		}
		else {
		    System.out.print("   ");
		}
		System.out.print("|");
	    }
	    System.out.println();
	}
	for(int j=0; j<this.getWidth(); ++j) {
	    System.out.print("+---");
	}
	System.out.println("+" + Color.ansiColorOf("WHITE"));
    }

    private void printAvailableColumn() {
	/**
	 * Pretty print of the column available to put a token in, print a 'X' when the 
	 * column is full of Token
	 */
	System.out.print(Color.ansiColorOf("BLUE"));
	for(int j=0; j<this.getWidth(); ++j) {
	    System.out.print("+-^-");
	}
	System.out.println("+");
	for(int j=0; j<this.getWidth(); ++j) {
	    if (this.getNextEmptyCellAt(j).getColor() != Color.EMPTY) {
		System.out.print("| X ");
	    }
	    else {
		System.out.print("| "
				 + Color.ansiColorOf("GREEN")
				 + (j+1) + Color.ansiColorOf("BLUE")
				 + " ");
	    }
	}
	System.out.println("|");
	for(int j=0; j<this.getWidth(); ++j) {
	    System.out.print("+---");
	}
	System.out.println("+" + Color.ansiColorOf("WHITE"));
    }

    public void print() {
	/**
	 * Print the pretty print of the grid and column available
	 */
	System.out.println();
	this.printGrid();
	System.out.println();
	this.printAvailableColumn();
	System.out.println();

    }

    @Override
    public String toString() {
	/**
	 * Return the String representation of the Grid : the color of each Token of each 
	 * Cell 
	 *
	 * @return the String of Color separated by ';'
	 */
	StringBuilder s = new StringBuilder();
	Cell c;
	for (int i=0; i<this.getWidth(); i++) {
	    c = this.getTopCellAt(i);
	    for(int j=0; j<this.getHeight(); j++) {
		s.append(c.toString()+";");
		c = c.getNeighbor(Direction.DOWN);
	    }
	}
	return s.toString();
    }

    public void loadGrid(String schema, Token[] tokenOfPlayers) {
	/**
	 * Load a Grid from a save (based on its own toString() method)
	 *
	 * @param schema the grid representation
	 * @param tokenOfPlayers a tab of Player Token
	 */
	Cell current;
	String[] cells = schema.split(";");
	
	for (int i=0; i<this.getWidth(); i++) {
	    current = this.getTopCellAt(i);
	    for (int j=0; j<this.getHeight(); j++) {
		for (int k=0; k<Game.numberOfPlayers; k++) {
		    if (tokenOfPlayers[k].getColor() ==
			Color.colorOf(cells[j+i*this.getHeight()]))
		    {
			if (tokenOfPlayers[k].getColor() == Color.EMPTY) {
			    throw new IllegalArgumentException("A player token can't be empty");
			}
			current.setToken(tokenOfPlayers[k]);
			this.UpToNextEmptyCellAt(i);
		    }
		}
		current = current.getNeighbor(Direction.DOWN);
	    }
	}
    }
}
