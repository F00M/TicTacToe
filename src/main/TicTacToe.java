package tictactoe.src.main;

/**
 * A TicTacToe Object.
 * <p>
 * This object contains the functions of a tic tac toe game with the minimum number
 * of players being 3 and the max being 10. Users are able to identify the number
 * specific number of symbols in a row before the game chooses a winner
 * @author Nahtann
 * @version 1.1
 */
public class TicTacToe {
    /**
     * User has not entered enough symbols.
     */
    public final static int UNDERTOTAL = -1;

    /**
     * User has entered too many symbols.
     */
    public final static int OVERTOTAL = 1;

    /**
     * User has entered the correct amount of symbols.
     */
    public final static int CORRECT = 0;

    /**
     * User has entered non-letters.
     */
    public final static int ONLYLETTERS = -2;

    /**
     * Tic tac toe grid is filled.
     */
    public final static int FILLED = 2;
    

    private char[] symbols;                         // array of symbols
    private char[][] grid;                          // tic tac toe grid
    private int rows;
    private int cols;
    private int winCondition;                       // # of symbols in a row to win

    /**
     * Default constructor.
     */
    public TicTacToe() {
        rows = 4;
        cols = 4;
        winCondition = 3;
        symbols = new char[4];

        generateGrid();
    }

    /**
     * Constructs a TicTacToe object with rows, columns, and a win condition.
     * @param rows number of rows in grid
     * @param cols number of cols in grid
     * @param winCondition number of symbols in a row
     */
    public TicTacToe(int rows, int cols, int winCondition) {
        this.rows = rows;
        this.cols = cols;
        this.winCondition = winCondition;
        symbols = new char[rows-1];

        generateGrid();
    }

    /**
     * Generates a tic tac toe grid based on number of rows and columns.
     */
    public void generateGrid() {
        grid = new char[rows][cols];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    /**
     * Gets the symbol found at a particular row and column.
     * @param row grid row
     * @param col grid column
     * @return symbol if found, else '~'
     */
    public char getChar(int row, int col) {
        try {
            return grid[row][col];
        } catch (NullPointerException e) {
            return 126;
        }
    }

    /**
     * Checks if the symbol at a particular row and column satisfies the win condition
     * on all sides.
     * <p>
     * Checks the following directions:
     * <ul>
     *  <li>up
     *  <li>right
     *  <li>down
     *  <li>left
     *  <li>up-right
     *  <li>up-left
     *  <li>down-right
     *  <li>down-left
     * </ul>
     * @param row grid row
     * @param col grid column
     * @return winning characeter, FILLED if grid is filled, 0 if no conditions are met
     */
    public char checkStatus(int row, int col) {
        char[] horizontal = new char[winCondition + (winCondition-1)];
        char[] vertical = new char[winCondition + (winCondition-1)];
        char[] leftDiagonal = new char[winCondition + (winCondition-1)];
        char[] rightDiagonal = new char[winCondition + (winCondition-1)];

        for (int i = winCondition-1; i >= 0; i--) {
            //left horizontal
            if (col - i >= 0) {
                horizontal[winCondition - i - 1] = grid[row][col-i];
            }

            //upper vertical
            if (row - i >= 0) {
                vertical[winCondition - i - 1] = grid[row-i][col];
            }

            //upper left diagonal
            if ((col - i >= 0) && (row - i >= 0)) {
                leftDiagonal[winCondition - i - 1] = grid[row-i][col-i];
            }

            //lower right diagonal
            if ((col - i >= 0) && (row + i < rows)) {
                rightDiagonal[winCondition - i - 1] = grid[row+i][col-i];
            }
            

            //right horizontal
            if (col + i < cols) {
                horizontal[winCondition + i - 1] = grid[row][col+i];
            }
    
            //lower vertical
            if (row + i < rows) {
                vertical[winCondition + i - 1] = grid[row+i][col];
            }
    
            //lower left diagonal
            if ((row + i < rows) && (col + i < cols)) {
                leftDiagonal[winCondition + i - 1] = grid[row+i][col+i];
            }
    
            //upper right diagonal
            if ((row - i >= 0) && (col + i < cols)) {
                rightDiagonal[winCondition + i - 1] = grid[row-i][col+i];
            }
        }

        if (matchWinCondition(horizontal, grid[row][col])) {return grid[row][col];}
        else if (matchWinCondition(vertical, grid[row][col])) {return grid[row][col];}
        else if (matchWinCondition(leftDiagonal, grid[row][col])) {return grid[row][col];}
        else if (matchWinCondition(rightDiagonal, grid[row][col])) {return grid[row][col];}
        else if (checkFilled()) {return FILLED;}
        else {return 0;}
    }

    /**
     * Checks if the array has consecutive characters.
     * @param array character array
     * @param verify character to check
     * @return true: consecutive characters matches win condition | false: consecutive characters does not match win condition
     */
    private boolean matchWinCondition(char[] array, char verify) {
        int consecutive = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] == verify) {
                consecutive++;
            }
            else {
                consecutive = 0;
            }

            if (consecutive == winCondition) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check if the tic tac toe grid is filled with symbols.
     * @return true: filled | false: not filled
     */
    public boolean checkFilled() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == ' ') {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * Add symbol to tic tac toe grid.
     * @param symbol grid symbol
     * @param row grid row
     * @param col grid column
     * @return true: successful | false: unsuccessful
     */
    public boolean addSymbol(char symbol, int row, int col) {
        if ((0 <= row && row < rows) && (0 <= col && col < cols)) {
            if (grid[row][col] != ' ') {
                return false;
            }
            grid[row][col] = symbol;
            return true;
        }
        
        return false;
    }

    /**
     * Set the symbols for the tic tac toe game.
     * Ex. a,b,c is three symbols where symbol 1 is a, symbol 2 is b, etc...
     * @param symbolsString String of symbols seperated by commas
     * @return UNDERTOTAL: not enough symbols, OVERTOTAL: too much symbols, CORRECT: correct number of symbols 
     */
    public int setSymbols(String symbolsString) {
        String[] symbols = symbolsString.split(",");
        
        if (symbols.length < rows-1) {
            return UNDERTOTAL;
        }
        else if (symbols.length > rows-1) {
            return OVERTOTAL;
        }
        else {
            for (int i = 0; i < rows-1; i++) {
                if (90 < symbols[i].toUpperCase().trim().charAt(0) && symbols[i].toUpperCase().trim().charAt(0) < 65) {
                    return ONLYLETTERS;
                }
                this.symbols[i] = symbols[i].toUpperCase().trim().charAt(0);
            }
            return CORRECT;
        }
    }

    /**
     * Formats grid with columns and rows. 
     * Columns are represented by letters (starting from A) and rows
     * are represented by numbers (starting from 1).
     * @return formatted tic tac toe grid
     */
    public String printPlayerGrid() {
        String gridString = "";
        String rowLetters = "   ";
        String lineString = "";
        for (int i = 0; i < cols; i++) {
            rowLetters += String.format(" %3c", i + 65);
        }
        for (int i = 0; i < rowLetters.length() + 2; i++) {
            lineString += "-";
        }
        gridString += String.format("%s\n%s\n", rowLetters, lineString);
        
        for (int i = 0; i < rows; i++) {
            String colString = String.format("%2d | ", i + 1);
            for (int j = 0; j < getRows(); j++) {
                colString += String.format("[%c] ", getChar(i, j));
            }
            gridString += colString + "\n";
        }
        
        return gridString;
    }

    /**
     * Get the symbols associated with the tic tac toe.
     * @return character array
     */
    public char[] getSymbols() {
        return symbols;
    }

    /**
     * Get rows.
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Set the rows.
     * @param rows grid rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Get columns.
     * @return columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Set the columns.
     * @param cols grid columns
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Get win condition.
     * @return the win condition
     */
    public int getWinCondition() {
        return winCondition;
    }

    /**
     * Set the win condition.
     * @param winCondition number of symbols in a row
     */
    public void setWinCondition(int winCondition) {
        this.winCondition = winCondition;
    }

    public String toString() {
            String x = "";

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    x += String.format("[%c] ", grid[i][j]);
                }
                x += "\n";
            }

            return x;
    }   
}
