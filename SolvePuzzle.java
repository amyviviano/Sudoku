package Sudoku;

//For Scanner
import java.util.*; 

public class SolvePuzzle {

	public static void main(String[] args) throws Exception {
		// Get input from console
		Scanner console = new Scanner(System.in);
		try {
			System.out.println("Enter 81 digits: 0 - 9: ");
			String initialPuzzle = console.nextLine();
			// Throw Exception if input is invalid
			for (int i = 0; i < 81; i++) {
				if (!Character.isDigit(initialPuzzle.charAt(i))) {
					throw new Exception("Invalid input");
				}
			}
			// Instantiate new Puzzle object
			Puzzle p = new Puzzle(initialPuzzle);
			// Print original Puzzle
			System.out.println(p);
			// Attempt to solve Puzzle
			if (p.solve(0, 0)) {
				System.out.println(p);
			} else {
				System.out.println("Puzzle could not be solved");
			}
		} finally {
			console.close();
		}
	}
}
