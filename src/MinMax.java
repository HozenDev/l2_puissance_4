import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * MinMax algorithm for Puissance 4
 * @author Durel Enzo
 * @author Villepreux Thibault
 * @version 1.0
 */
public class MinMax {
	public static int INF = 100;
	
	/**
	 * Return a list of integers corresponding to the playable columns
	 * 
	 * @param array game grid
	 * @return a list of integers
	 */
	public static ArrayList<Integer> getValidLocations(char [][] array) {
		ArrayList<Integer> validateColumn = new ArrayList<Integer>() ;

		for(Integer column = 0 ; column < array[0].length; ++column) {
			if(array[0][column] == '0') {// test aussi avec long -1 ?!
				validateColumn.add(column);	
			}
		}
		return validateColumn;
	}

	/**
	 * Checks if there is a person of the color given in parameter who won
	 * 
	 * @param array game grid
	 * @param color player color
	 * @return a boolean indicating whether the game is won or not
	 */
	public static boolean caseWin(char [][] array, char color) {
		for(int i = 0 ; i < array.length; ++i) {
			for(int j = 0 ; j < array[0].length; ++j) {
			    int countR = 1;
				int countL = 1;
				int countU = 1;
				int countD = 1;
				int countUR = 1;
				int countUL = 1;
				int countDR = 1;
				int countDL = 1;
				if(array[i][j] == color) {
					for(int k = 1 ; k < 4 ; ++k) {
						// droite
						if(j+k < array[0].length) {
							if (array[i][j+k] == color) {
								++ countR;
							}
						}
						// gauche
						if(j-k >= 0) {
							if (array[i][j-k] == color) {
								++ countL;
							}
						}
						// bas
						if(i+k <  array.length) {
							if (array[i+k][j] == color) {
								++ countD;
							}
						}
						// haut
						if(i-k >=0) {
							if (array[i-k][j] == color) {
								++ countU;
							}
						}
						// droite && bas
						if(j+k < array[0].length && i+k < array.length) {
							if (array[i+k][j+k] == color) {
								++ countDR;
							}
						}
						// gauche && haut
						if(j-k >= 0 && i-k >=0) {
							if (array[i-k][j-k] == color) {
								++ countUL;
							}
						}
						// gauche && bas
						if(i+k < array.length && j-k >= 0) {
							if (array[i+k][j-k] == color) {
								++ countDL;
							}
						}
						// droite && haut
						if(j+k < array[0].length && i-k >=0 ) {
							if (array[i-k][j+k] == color) {
								++ countUR;
							}
						}
					}
					if(countR >= 4 || countL >= 4 || countU >= 4 || countD >= 4 || countUR >= 4 || countUL >= 4 || countDR >= 4 || countDL >= 4){
						return true;
					}
				}
			}
		}
		return false;
    }

	/**
	 * Copy grid to another variable, function with no side effects
	 * 
	 * @param array game grid
	 * @return a new grid
	 */
	public static char[][] copieArray(char[][] array) {
		char[][] arrayTmp = new char[array.length][array[0].length] ;
		for(int i = 0 ; i < array.length; ++i) {
			for(int j = 0 ; j < array[0].length; ++j){
		    	arrayTmp[i][j] = array[i][j];
			}
		}
		return arrayTmp;
	}

	/**
	 * Retrieves the next empty box from the column passed as a parameter
	 * 
	 * @param array game grid
	 * @param column column number
	 * @return a integer corresponding to the line 
	 */
	public static int getUpCase(char [][] array, int column){
		for(int line = 0 ; line < array.length ; ++ line) {
			if(array[line][column] != '0') {
				return line-1;
			}
		}
		return array.length-1;
	}

	/**
	 * Retrieves the last non-empty cell of the column passed as a parameter
	 * 
	 * @param array game grid
	 * @param column column number
	 * @return a integer corresponding to the line 
	 */
	public static int getCase(char [][] array, int column){
		for(int line = 0 ; line < array.length ; ++ line) {
			if(array[line][column] != '0') {
				return line;
			}
		}
		return array.length-1;
	}

	/**
	 * Play a pawn in the grid with no side effects
	 * 
	 * @param array game grid
	 * @param column column number
	 * @param color player color
	 * @return the new grid
	 */
	public static char[][] playTokenArray(char [][] grid, int column, char color) {
		if (getUpCase(grid, column) > 0 ) {
			grid[getUpCase(grid, column)][column] = color;
		}
		return grid;
	}

	/**
	 * Allows you to know which column to play
	 * 
	 * @param grid game grid
	 * @param detph depth of the possibility tree
	 * @param color player color
	 * @return the best column for playing
	 */
    public static int bestColumn(char[][] grid, char color, int depth) {
        List<Integer> listeOfColumn = getValidLocations(grid);
        
        List<Integer> choix = new ArrayList<Integer>();
        int max = -INF;
        int calcul;
        int finalChoix;

        
        for (Integer col : listeOfColumn) {
            calcul = minimax(depth, grid, true, col);
            if (max < calcul) {
                max = calcul;
                choix.clear();
                choix.add(col);
            }
            if (max == calcul) choix.add(col);
        }
        if (choix.size() > 1 && choix.contains(3)) return 3;
        else {
            Random r = new Random();
            finalChoix = choix.get(r.nextInt(choix.size()));
        }
        return finalChoix;
    }

    /**
	 * Allows to calculate the tree by evaluating the last nodes and we make them go up
	 * 
	 * @param grid game grid
	 * @param detph depth of the possibility tree
	 * @param ia boolean that identifies the player
	 * @param column to play the last move
	 * @return the value of node
	 */
    public static int minimax(int depth, char [][] grid, boolean ia, int column) {
        if (depth == -1) return 0;

        char[][] g = copieArray(grid);
        
        if (ia) playTokenArray(g, column, 'y');
        else    playTokenArray(g, column, 'r');

        if (caseWin(g, 'y'))      return INF;
        else if (caseWin(g, 'r')) return -INF;
        else {
            List<Integer> listeOfColumn = getValidLocations(g);
            List<Integer> results = new ArrayList<Integer>();
            int max = -INF; int min = INF;
            
            for(Integer col : listeOfColumn)
                results.add(minimax(depth-1, g, !ia, col));

            for(int value : results) {
                if(ia && min > value) min = value;
                else if(max < value)  max = value;
            }
            return (ia) ? min : max ;
        }
    }
}
