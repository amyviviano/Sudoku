package Sudoku;
//Amy Viviano
//Puzzle.java
//2018-11-09
//
//Definition of a Puzzle:
//A Sudoku puzzle consists of 81 small squares arranged in 9 rows by 9 columns.
//The small squares are subdivided into 3-by-3 blocks. A puzzle starts with a number of
//the squares filled in with numbers from 1 through 9. The puzzle is solved by filling
//in the rest of the squares with numbers from 1 through 9 according to the following rules:
//1. No row has the same number twice.
//2. No column has the same number twice.
//3. No 3-by-3 block in the puzzle has the same number twice.
//The Puzzle class represents a Sudoku puzzle. It holds 81 square objects in a 2D array.
//The Puzzle’s set() method enforces the above 3 rules so that a square cannot be given an
//invalid value. set() also calls a method checkBlock() to check whether there is a duplicate
//value in a particular block.
//
// A Puzzle has a private class Square
//

public class Puzzle {
	
	//Definition of a Square:
	//A Square represents one of the 81 small squares located in a Puzzle. A Square’s
	//data member consist of its numerical value and a boolean flag that is true if its
	//value is fixed, where its value is an initial given value of 1-9, and false if its
	//value is variable, where its original value is 0 and can be changed as a part of a solution.
	private static class Square {

		// Data members for a Square object
		// Integer value representing the value of a Square
		private int value;
		// Flag to indicate if the value is fixed or variable
		private boolean fixed;
		
		// Default constructor
		// Sets value to '-1', sets fixed flag to false
		Square() {
		   value = -1;
		   fixed = false;
		}

		// Accsessor method that returns value of the square as an integer
		// No parameters. Doesn't change Square's members. Returns value.
		// Returns -1 if value isn't set.
		int getValue() {
		   return value;
		}
		
		// Method that can set the Square's bool flag to fixed when Puzzle's
		// initial values are given.
		// Pre: A Square's initial fixed value is set to false
		// Post: a Square's  fixed value is changed from false to true.
		void setToFixed() {
		   fixed = true;
		}
		
		// Accessor method that returns the Square's bool flag 
		boolean fixedFlag() {
			   return fixed;
			}
		
		// Mutator method that sets a Square's value. Only Puzzle has friend access to this method.
		// Parameter is an int value. Parameter constraints are enforced by Puzzle::set() method.
		void setValue(int newValue) {
		   value = newValue;
		}
	}

	// Data members for a Puzzle object
	public static final int ROWS = 9;
	public static final int COLUMNS = 9;
	// 2D array to store a Sudoku board
	private Square[][] board;
	
	// Constructor
	public Puzzle(String s) {
		board = new Square[ROWS][COLUMNS];
		int index = 0;
		int data = s.charAt(index) - '0';
		// Populate board with Square objects and set Squares values
		for (int i = 0; i < ROWS; i++)
		{
			for (int j = 0; j < COLUMNS; j++) {
				board[i][j] = new Square();
				board[i][j].setValue(data);
				// Set Square flag to fixed if value is initialized 1 - 9
				if (data > 0) {
					board[i][j].setToFixed();
				}
				index++;
				if (index < s.length()) {
					data = s.charAt(index) - '0';
				}
			}
		}
	}
	
	// Method takes two integer arguments ((x,y) location, zero-based)
	// Pre: Puzzle board is filled with Squares
	// Post: Returns a const reference to the square at given location.
	// Throws exception if parameters represent location outside of board
	private Square get(int row, int column) {
	   return board[row][column];
	}
	
	// Method sets the value of the Square at the given location
	// Calls Square's setValue() on a specific Square to set its value
	// Calls duplicateBlock() and Square's getValue()
	// Checks row, column, and block for duplicate values before allowing Square's value to be set
	// Parameters: (row, column, value)
	// Pre: A Puzzle object has been created and set() is called on a particular Square
	// Post: Sets Square's value if legal.
	// Returns true is Square's value is set; otherwise returns false
	private boolean set(int row, int column, int value) {
	   // Check to see if Square's value is fixed
	   // Return false if value is fixed
	   if (get(row, column).fixedFlag())
	   {
	      return false;
	   }
	   // Check for row, column, and block duplicates
	   if (value > 0)
	   {
	      // Loops through relevant row searching for duplicate.
	      // Return false if duplicate is found
	      for (int i = 0; i < COLUMNS - 1; i++)
	      {
	         if (board[row][i].getValue() == value)
	         {
	            return false;
	         }
	      }
	      // Loops through relevant column searching for duplicate.
	      // Return false if duplicate is found
	      for (int i = 0; i < ROWS - 1; i++)
	      {
	         if (board[i][column].getValue() == value)
	         {
	            return false;
	         }
	      }
	      // Loops through relevant block searching for duplicate.
	      // Return false if duplicate is found
	      if (blockDuplicate(row, column, value))
	      {
	         return false;
	      }
	   }
	   // Set the Squares value after having passed all tests
	   // Set Square values for initial variable values of 0
	   board[row][column].setValue(value);
	   return true;
	}
	
	// Method checks to see whether the block corresponding to a given square contains a given value
	// Parameters: (Row, column, value)
	// Pre: blcokDuplicate is called with a given Square's location and potential new value that
	// needs to be checked for validation
	// Post: Returns true if block contains value, false if it does not.
	private boolean blockDuplicate(int row, int column, int value) 
	{
	   // blockRow and blockColumn indicate the location in the board to begin searching the relevant
	   // 3*3 block in the Puzzle board for a duplicate
	   int blockRow = row / 3 * 3;
	   int blockColumn = column / 3 * 3;
	   // Nested loop will check the block for a duplicate value
	   // Outer loop will always loop through each of the 3 rows of the block
	   for (int i = blockRow; i < blockRow + 3; i++)
	   {
	      // Inner loop will go thorugh each column of the block, and check each value for
	      // a duplicate value
	      for (int j = blockColumn; j < blockColumn + 3; j++)
	      {
	         if (board[i][j].getValue() == value)
	         {
	            // Return true if duplicate value is found
	            return true;
	         }
	      }
	   }
	   // Return false if no duplicate is found
	   return false;
	}
	
	// Method to solve the puzzle by applying a backtracking algorithm.
	// Paramters: (int row, int column)
	// Pre: Client program calls solve() on a Puzzle
	// Post: Returns true if Puzzle can be solved; false if it can't
	// Calls Puzzle::solve(), Puzzle::set()
	public boolean solve(int row, int column)
	{
	   // If column is past last column, move to new row and set column to 0
	   if (column > COLUMNS-1)
	   {
	      row++;
	      column = 0;
	   }
	   // If row is past the last row, print the solved puzzle
	   if (row > ROWS-1)
	   {
		   return true;
	   }
	   // Move to next square, loop executes while current Square already has a value
	   while (board[row][column].getValue() != 0)
	   {
	      column++;
	      // If column is past the end, move to new row and set column to 0
	      if (column > COLUMNS-1)
	      {
	         row++;
	         column = 0;
	      }
	   }
	   // If row is past the last row, print the solved puzzle
	   if (row > ROWS-1)
	   {
	      return true;
	   }
	   // Try to solve the Square with values 1-9.
	   for (int i = 1; i <= 9; i++)
	   {
	      // Call solve on next square once a valid value can be set to current Square
	     if (set(row, column, i))
		 {
		 // Return true if the next Square can be solved
	    	 if (solve(row, column+1))
		     {
		        return true;
		     }
		     // Set current Square to 0 (erase value) if the next Square could not be solved
		     set(row, column, 0);
		  }
	   }
	   // Return false if no valid values exist for a Square
	   return false;
	}
	
	// Method toString() outputs a puzzle as 11 lines of text: 9 lines representing each row
	// of the Puzzle and 2 divider lines.
	// Pre: A Puzzle has been created.
	// Post: A readable Puzzle is output to the console.
	public String toString() {
		String puzzle = "";
	   // Nested loop prints board's 2D array of Squares with their values
	   // Loop executes while there are rows left to print
	   int vertDividerCount = 0;
	   int lastBlockRowCount = 0;
	   for (int j = 0; j < ROWS; j++)
	   {
	      int horDividerCount = 0;
	      // Loop executes while there are more columns to print
	      for (int k = 0; k < COLUMNS; k++)
	      {
	         puzzle += board[j][k].getValue() + " ";
	         puzzle += board[j][k + 1].getValue() + " ";
	         puzzle += board[j][k + 2].getValue() + " ";
	         k += 2;
	         // Prints vertical block divider lines
	         if (horDividerCount < 2)
	         {
	        	 puzzle += "| ";
	         } else {
	        	 puzzle += "\n";
	         }
	         horDividerCount++;
	      }
	      vertDividerCount++;
	      // Prints horizontal block diver lines
	      if (vertDividerCount > 2 && lastBlockRowCount < 2)
	      {
	    	 puzzle += "------+-------+------\n";
	         vertDividerCount = 0;
	         lastBlockRowCount++;
	      }
	   }
	   return puzzle;
	}
}
