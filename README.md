# Sudoku

Program that creates a Sudoku puzzle and attempts to solve using a recursive backtracking algorithm.

Definition of a Puzzle
A Sudoku puzzle consists of 81 small squares arranged in 9 rows by 9 columns. The small squares are subdivided 
into 3-by-3 blocks (see Output Data for example). A puzzle starts with a number of the squares filled in with numbers 
from 1 through 9. The puzzle is solved by filling in the rest of the squares with numbers from 1 through 9 according to 
the following rules:

No row has the same number twice.
No column has the same number twice.
No 3-by-3 block in the puzzle has the same number twice.
 
The Puzzle class represents a Sudoku puzzle. It holds 81 square objects in a 2D array. The Puzzle’s set() method enforces 
the above 3 rules so that a square cannot be given an invalid value. set() also calls a method checkBlock() to check whether 
there is a duplicate value in a particular block.
 
Definition of a Square
A Square represents one of the 81 small squares located in a Puzzle. A Square’s data member consist of its numerical value 
and a boolean flag that is true if its value is fixed, where its value is an initial given value of 1-9, and false if its value 
is variable, where its original value is 0 and can be changed as a part of a solution.
 
Purpose
In this program, main() will create a Puzzle object and will read a Sudoku puzzle from cin. The latter is accomplished by 
overloading the operator>>() in the Puzzle class. Using the overloaded operator<<() in the Puzzle class, main() then displays 
the original puzzle. main() then tries to solve the puzzle by applying a backtracking algorithm. If the program can solve the 
puzzle, the puzzle is then displayed. Otherwise the program outputs a message indicating that the puzzle could not be solved. 

