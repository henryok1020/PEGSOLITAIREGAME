package peg;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PegSolitaireGame 
{	
	/**
	 * This method is responsible for everything from displaying the opening 
	 * welcome message to printing out the final thank you.  It will clearly be
	 * helpful to call several of the following methods from here, and from the
	 * methods called from here.  See the Sample Runs below for a more complete
	 * idea of everything this method is responsible for.
	 * 
	 * @param args - any command line arguments may be ignored by this method.
	 */
	
	public static void main(String[] args){	

		Scanner scanf = new Scanner(System.in);
		
		System.out.println("WELCOME TO cs300 PEG SOLITAIRE!");
		System.out.println("===============================");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("Board Style Menu");
		System.out.println("1) Cross");
		System.out.println("2) Circle");
		System.out.println("3) Triangle");
		System.out.println("4) Simple T");
		System.out.print("Choose a board style: ");
		
		String prompt = "Please enter your choice as an integer between 1 and 4: ";
		int min = 1;
		int max = 4;
		
		int boardType = readValidInt(scanf, prompt, min, max);
		char board[][] = createBoard(boardType);
		System.out.println(" ");
		
		while(true) {
			displayBoard(board);
			System.out.println(" ");
			
			int rowMove;
			int colMove;
			int direction;
			
			while(true) {
				int[] legalMove = readValidMove(scanf, board);
				
				rowMove = legalMove[0];
				colMove = legalMove[1];
				direction = legalMove[2];
				
				boolean result = isValidMove(board, rowMove, colMove, direction);
				
				String move = null;
				
				if(direction == 1) {
					move = "UP";
				} else if(direction == 2) {
					move = "DOWN";
				} else if(direction == 3) {
					move = "LEFT";
				} else if(direction == 4) {
					move = "RIGHT";
				}
				
				if(result) {
					break;
				} else {
					System.out.println("Moving a peg from row " + rowMove + " and column " + colMove + " " + move + " is not currently a legal move.");
				}
			}
			
			board = performMove(board, rowMove, colMove, direction);
			
			int countPeg = 0;
			countPeg = countPegsRemaining(board);
			
			int countMove = 0;
			countMove = countMovesAvailable(board);
			
			if(countPeg > 1 && countMove == 0) {
				System.out.println("It looks like there are no more legal moves.  Please try again.");
				break; // break while
			} else if(countPeg == 1 && countMove == 0 ){
				System.out.println("Congrats, you won!");
				break; // break while
			}
		}
		
		System.out.println("");
		System.out.println("==========================================");
		System.out.println("THANK YOU FOR PLAYING CS300 PEG SOLITAIRE!");	
	
		
		
	}
		
	/**
	 * This method is used to read in all inputs from the user.  After printing
	 * the specified prompt, it will check whether the user’s input is in fact
	 * an integer within the specified range.  If the user’s input does not 
	 * represent an integer or does not fall within the required range, print
	 * an error message asking for a value within that range before giving the
	 * user another chance to enter valid input.  The user should be given as
	 * many chances as they need to enter a valid integer within the specified
	 * range.  See the Sample Runs to see how these error messages should be 
	 * phrased, and to see how the prompts are repeated when multiple invalid 
	 * inputs are entered by the user.
	 * 
	 * @param in - user input from standard in is ready through this.
	 * @param prompt - message describing what the user is expected to enter.
	 * @param min - the smallest valid integer that the user may enter.
	 * @param max - the largest valid integer that the user may enter.
	 * @return - the valid integer between min and max entered by the user.
	 */
	public static int readValidInt(Scanner in, String prompt, int min, int max)
	{		
		int boardType = 0;
		
		while(true) {
			try {
				boardType = in.nextInt();
				
				if(max < boardType || boardType < min) {
					System.out.println(prompt);
				} else {
					break;
				}
			} catch(InputMismatchException e) {
				System.out.println(prompt);
				in.nextLine();
			}
		}
			
		return boardType;
		
	}

	/**
	 * This method creates, initializes, and then returns a rectangular two 
	 * dimensional array of characters according to the specified boardType.  
	 * Initial configurations for each of the possible board types are depicted
	 * below.  Note that pegs are displayed as @s, empty holes are displayed as
	 * -s, and extra blank positions that are neither pegs nor holes within 
	 * each rectangular array are displayed as #s.
	 * 
	 * @param boardType - 1-4 indicating one of the following initial patterns:
	 *   1) Cross
	 *     ###@@@###
	 *     ###@@@###
	 *     @@@@@@@@@
	 *     @@@@-@@@@
	 *     @@@@@@@@@
	 *     ###@@@###
	 *     ###@@@###
	 *     
	 *   2) Circle
	 *     #-@@-#
	 *     -@@@@-
	 *     @@@@@@
	 *     @@@@@@
	 *     -@@@@-
	 *     #-@@-#
	 *     
	 *   3) Triangle
	 *     ###-@-###
	 *     ##-@@@-##
	 *     #-@@-@@-#
	 *     -@@@@@@@-
	 *     
	 *   4) Simple T
	 *     -----
	 *     -@@@-
	 *     --@--
	 *     --@--
	 *     -----
	 *     
	 * @return - the fully initialized two dimensional array.
	 */
	public static char[][] createBoard(int boardType) {	
		
		char board[][] = null;
		
		// boardType 1 = Cross
		// boardType 2 = Circle
		// boardType 3 = Triangle
		// boardType 4 = Simple T
		
		if(boardType == 1) {
			board = new char[7][9];
			for(int i = 0; i < 7;i++){
				for(int j = 0; j < 9;j++){
					board[i][j] = '@';
				}
				board[0][4] = '@';
			
				board[0][0] = '#';
				board[0][1] = '#';
				board[0][2] = '#';
				board[0][6] = '#';
				board[0][7] = '#';
				board[0][8] = '#';
				
				board[1][0] = '#';
				board[1][1] = '#';
				board[1][2] = '#';
				board[1][6] = '#';
				board[1][7] = '#';
				board[1][8] = '#';
				
				board[5][0] = '#';
				board[5][1] = '#';
				board[5][2] = '#';
				board[5][6] = '#';
				board[5][7] = '#';
				board[5][8] = '#';
				
				board[6][0] = '#';
				board[6][1] = '#';
				board[6][2] = '#';
				board[6][6] = '#';
				board[6][7] = '#';
				board[6][8] = '#';
			}
		} else if(boardType == 2){
			board = new char[6][6];
			for(int i = 0; i < 6;i++){
				for(int j = 0; j < 6;j++){
					board[i][j] = '@';
				}
				board[0][0] = '#';
				board[0][5] = '#';
				board[5][0] = '#';
				board[5][5] = '#';
				
				board[0][1] = '-';
				board[0][4] = '-';
				board[5][1] = '-';
				board[5][4] = '-';
				
				
				board[1][0] = '-';
				board[1][5] = '-';
				board[4][0] = '-';
				board[4][5] = '-';	
			}	
		} else if(boardType == 3) {
			board = new char[4][9];
			for(int i = 0; i < 4;i++){
				for(int j = 0; j < 9;j++){
					board[i][j] = '@';
				}
				board[0][0] = '#';
				board[0][1] = '#';
				board[0][2] = '#';
				board[0][6] = '#';
				board[0][7] = '#';
				board[0][8] = '#';
				board[0][3] = '-';
				board[0][5] = '-';
				
				board[1][0] = '#';
				board[1][1] = '#';
				board[1][7] = '#';
				board[1][8] = '#';
				board[1][2] = '-';
				board[1][6] = '-';
				
				board[2][0] = '#';
				board[2][8] = '#';
				board[2][1] = '-';
				board[2][4] = '-';
				board[2][7] = '-';
				
				board[3][0] = '-';
				board[3][8] = '-';		
			}
		} else if(boardType == 4){
			board = new char[5][5];
			for(int i = 0; i < 5;i++){
				for(int j = 0; j < 5;j++){
					board[i][j] = '@';
				}
				board[0][0] = '-';
				board[0][1] = '-';
				board[0][2] = '-';
				board[0][3] = '-';
				board[0][4] = '-';
				
				board[1][0] = '-';
				board[1][4] = '-';
				
				board[2][0] = '-';
				board[2][1] = '-';
				board[2][3] = '-';
				board[2][4] = '-';
				
				board[3][0] = '-';
				board[3][1] = '-';
				board[3][3] = '-';
				board[3][4] = '-';
				
				board[4][0] = '-';
				board[4][1] = '-';
				board[4][2] = '-';
				board[4][3] = '-';
				board[4][4] = '-';		
			}
		} else {
			System.out.println("Choose a board style again.");
		}
		return board;
	}

	/**
	 * This method prints out the contents of the specified board using @s to 
	 * represent pegs, -s to represent empty hole, and #s to represent empty 
	 * positions that are neither pegs nor holes.  In addition to this, the 
	 * columns and rows of this board should be labelled with numbers starting 
	 * at one and increasing from left to right (for column labels) and from 
	 * top to bottom (for row labels).  See the Sample Runs for examples of how
	 * these labels appear next to boards with different dimensions.
	 * 
	 * @param board - the current state of the board being drawn.
	 */
	public static void displayBoard(char[][] board)
	{
		int row = board.length;
		int col = board[0].length;
		
		System.out.print("  ");
		for(int i = 1; i <= col; i++) {
			System.out.print(i);
		}
		System.out.println();
		
		for(int i = 0; i < row; i++) {
			System.out.print((i+1) + " ");
			
			for(int j = 0; j < col; j++) {
				System.out.print(board[i][j]);
			}
			
			System.out.println();
		}
	}
	
	/**
	 * This method is used to read in and validate each part of a user’s move 
	 * choice: the row and column that they wish to move a peg from, and the 
	 * direction that they would like to move/jump that peg in.  When the 
	 * player’s row, column, and direction selection does not represent a valid
	 * move, your program should report that their choice does not constitute a
	 * legal move before giving them another chance to enter a different move.  
	 * They should be given as many chances as necessary to enter a legal move.
	 * The array of three integers that this method returns will contain: the 
	 * user’s choice of column as the first integer, their choice of row as the
	 * second integer, and their choice of direction as the third.
	 * 
	 * @param in - user input from standard in is ready through this.
	 * @param board - the state of the board that moves must be legal on.
	 * @return - the user's choice of column, row, and direction representing
	 *   a valid move and store in that order with an array.
	 */
	public static int[] readValidMove(Scanner in, char[][] board){

		int row = board.length;
		int col = board[0].length;
		
		int rowMove = 0;
		int colMove = 0;
		int direction = 0;
		
		String rowPrompt = ("Please enter your choice as an integer between 1 and " + row + ": ");
		String colPrompt = ("Please enter your choice as an integer between 1 and " + col + ": ");
		String dirPrompt = ("Please enter your choice as an integer between 1 and 4: ");
		
		// row
		while(true) {
			try {
				if (rowMove == 0) {	
					System.out.print("Choose the ROW of a peg you'd like to move: ");
					rowMove = in.nextInt();
				}
				if(rowMove > row || row <= 0) {
					while(true) {
						System.out.print(rowPrompt);
						rowMove = in.nextInt();
						if(0 < rowMove && rowMove <= row) {
							break;
						}
					}
				}
			} catch(InputMismatchException e) {
				rowMove = -1;	
				in.nextLine();	
			}
	
			if(0 < rowMove && rowMove <= row) {
				break;
			}
		}
		
		// col	
		while(true) {
			try{
				if (colMove == 0) {
					System.out.print("Choose the COLUMN of a peg you'd like to move: ");
					colMove = in.nextInt();
				}
			
				if(colMove > col || colMove <= 0) {
					while(true) {
						System.out.print(colPrompt);
						colMove = in.nextInt();
						if(0 < colMove && colMove <= col) {
							break;
						}
					}
				}
			} catch(InputMismatchException e) { 
			    colMove = -1;	
				in.nextLine();
			}
			
			if(0 < colMove && colMove <= col) {
				break;
			}
		}
		
		// direction
		while(true) {
			try {
				if(direction == 0) {
					System.out.print("Choose a DIRECTION to move that peg 1) UP, 2) DOWN, 3) LEFT, or 4) RIGHT: ");
					direction = in.nextInt();
				}
				
				if(4 < direction || direction < 1) {
					while(true) {
						System.out.print(dirPrompt);
						direction = in.nextInt();
						if(0 < direction && direction < 5) {
							break;
						}
					}
				}
			} catch(InputMismatchException e) {
				direction = -1;
				in.nextLine();
			}
			if(0 < direction && direction < 5) {
				break;
			}
		}
		int[] legalMove = {rowMove, colMove, direction};
		
		return legalMove;
	}
		
	
	/**
	 * This method checks whether a specific move (column + row + direction) is
	 * valid within a specific board configuration.  In order for a move to be
	 * a valid: 1)there must be a peg at position row, column within the board,
	 * 2)there must be another peg neighboring that first one in the specified
	 * direction, and 3)there must be an empty hole on the other side of that
	 * neighboring peg (further in the specified direction).  This method
	 * only returns true when all three of these conditions are met.  If any of
	 * the three positions being checked happen to fall beyond the bounds of 
	 * your board array, then this method should return false.  Note that the 
	 * row and column parameters here begin with one, and may need to be 
	 * adjusted if your programming language utilizes arrays with zero-based 
	 * indexing.
	 * 
	 * @param board - the state of the board that moves must be legal on.
	 * @param row - the vertical position of the peg proposed to be moved.
	 * @param column - the horizontal position of the peg proposed to be moved.
	 * @param direction - the direction proposed to move/jump that peg in.
	 * @return - true when the proposed move is legal, otherwise false.
	 */
	public static boolean isValidMove(char[][] board, int row, int column, int direction){
		int maxRow = board.length;
		int maxCol = board[0].length;
		
		int destRow = row - 1;
		int destCol = column - 1;
		int passRow = row - 1;
		int passColumn = column - 1;
		
		if(direction == 1) {
			destRow = destRow - 2;
			passRow = passRow - 1;
		} else if(direction == 2) {
			destRow = destRow + 2;
			passRow = passRow + 1;
		} else if(direction == 3) {
			destCol = destCol - 2;
			passColumn = passColumn - 1;
		} else if(direction == 4) {
			destCol = destCol + 2;
			passColumn = passColumn + 1;
		} 
		
		if(destRow < 0 || destRow >= maxRow) {
			destRow = row - 1;
		}
		if(destCol < 0 || destCol >= maxCol) {
			destCol = column - 1;
		}
		if(passRow < 0 || passRow >= maxRow) {
			passRow = row - 1;
		}
		if(passColumn < 0 || passColumn >= maxCol) {
			passColumn = column - 1;
		}
		
		if(board[passRow][passColumn] == '@' && board[destRow][destCol] == '-') {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * The parameters of this method are the same as those of the isValidMove()
	 * method.  However this method changes the board state according to this
	 * move parameter (column + row + direction), instead of validating whether
	 * the move is valid.  If the move specification that is passed into this
	 * method does not represent a legal move, then do not modify the board.
	 * 
	 * @param board - the state of the board will be changed by this move.
	 * @param row - the vertical position that a peg will be moved from.
	 * @param column - the horizontal position that a peg will be moved from.
	 * @param direction - the direction of the neighbor to jump this peg over.
	 * @return - the updated board state after the specified move is taken.
	 */
	public static char[][] performMove(char[][] board, int row, int column, int direction){
		row--;
		column--;
		
		if(direction == 1) {
			board[row - 2][column] = '@';
			board[row - 1][column] = '-';
			board[row][column] = '-';
			
		} else if(direction == 2) {
			board[row + 2][column] = '@';
			board[row + 1][column] = '-';
			board[row][column] = '-';
			
		} else if(direction == 3) {
			board[row][column - 2] = '@';
			board[row][column - 1] = '-';
			board[row][column] = '-';
			
		} else if(direction == 4) {
			board[row][column + 2] = '@';
			board[row][column + 1] = '-';
			board[row][column] = '-';
		}
		return board;
	}
	
	/**
	 * This method counts up the number of pegs left within a particular board 
	 * configuration, and returns that number.
	 * 
	 * @param board - the board that pegs are counted from.
	 * @return - the number of pegs found in that board.
	 */
	public static int countPegsRemaining(char[][] board){
		
		int countPeg = 0;
		for(int i = 0;i < board.length;i++) {
			for(int j = 0;j < board[0].length;j++) {
				if (board[i][j] == '@') {
					countPeg++;
				}
			}
	    }
		return countPeg;
	}
	
	/**
	 * This method counts up the number of legal moves that are available to be
	 * performed in a given board configuration.
	 * 
	 * HINT: Would it be possible to call the isValidMove() method for every
	 * direction and from every position within your board?  Counting up the
	 * number of these calls that return true should yield the total number of
	 * moves available within a specific board.
	 * 
	 * @param board - the board that possible moves are counted from.
	 * @return - the number of legal moves found in that board.
	 */
	public static int countMovesAvailable(char[][] board){
		int countMove = 0;
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] == '@') {
					int row = i + 1;
					int column = j + 1;
					
					if(isValidMove(board, row, column, 1)) {
						countMove++;
					} else if(isValidMove(board, row, column, 2)) {
						countMove++;
					} else if(isValidMove(board, row, column, 3)) {
						countMove++;
					} else if(isValidMove(board, row, column, 4)) {
						countMove++;
					}
				}
			}
		}
		return countMove;
	}
}



