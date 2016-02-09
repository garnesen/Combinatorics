import java.util.ArrayList;
import java.util.List;

/**
 * The SquareMatrix class.
 * 
 * A SquareMatrix is represented by a 2D array where the length is equal to the width. This class
 * presents methods to perform matrix multiplication and exponentiation.
 * @author Gunnar Arnesen
 *
 */
public class SquareMatrix {

	private int size;
	private int[][] matrix;

	/**
	 * Creates a new SquareMatrix object.
	 * @param matrix - The 2D array of values
	 */
	public SquareMatrix(int[][] matrix) {
		if (matrix.length != matrix[0].length) {
			throw new IllegalArgumentException("Matrix needs to be square.");
		}
		this.size = matrix.length;
		this.matrix = matrix;
	}

	/**
	 * Creates a new SquareMatrix object with a specified size. All entries are 0.
	 * @param size - The size of the matrix
	 */
	public SquareMatrix(int size) {
		this.size = size;
		this.matrix = new int[size][size];
	}

	/**
	 * Gets the value at an entry in the matrix.
	 * @param row
	 * @param col
	 * @return the value
	 */
	public int get(int row, int col) {
		return matrix[row][col];
	}

	/**
	 * Sets a value at the specified entry.
	 * @param row
	 * @param col
	 * @param val
	 */
	public void set(int row, int col, int val) {
		matrix[row][col] = val;
	}

	/**
	 * Gets the size of the matrix.
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Calculates the matrix to a given power.
	 * @param power - The exponent
	 * @return the calculates matrix
	 */
	public SquareMatrix power(int power) {
		SquareMatrix mult = this.clone();
		for (int i = 0; i < power; i++) {
			mult = multiply(mult);
		}
		return mult;
	}

	/**
	 * Multiply this matrix by another given matrix. Both must have the same size!
	 * @param right - The right side of the matrix operation 
	 * @return the calculated matrix or null if the calculation is not possible
	 */
	public SquareMatrix multiply(SquareMatrix right) {
		// Check that these matrices have the same size.
		if (right.getSize() != this.getSize())
			return null;

		// Loop through the values to compute matrix multiplication.
		SquareMatrix result = new SquareMatrix(this.getSize());
		for (int rightCol = 0; rightCol < right.getSize(); rightCol++) {
			for (int leftRow = 0; leftRow < this.getSize(); leftRow++) {
				int total = 0;
				for (int i = 0; i < this.getSize(); i ++) {
					total += this.get(leftRow, i) * right.get(i, rightCol);
				}
				result.set(leftRow, rightCol, total);
			}
		}
		return result;
	}
	
	/**
	 * Get the diagonal of the matrix as List
	 * @return the list containing the values on the diagonal
	 */
	public List<Integer> getDiagonal() {
		List<Integer> diagonal = new ArrayList<Integer>(size);
		for (int index = 0; index < size; index++) {
			diagonal.add(matrix[index][index]);
		}
		return diagonal;
	}
	
	@Override
	public SquareMatrix clone() {
		int[][] copy = new int[size][size];
		for (int row = 0; row < size; row++ ) {
			for (int col = 0; col < size; col++) {
				copy[row][col] = matrix[row][col];
			}
		}
		return new SquareMatrix(copy);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				sb.append(matrix[row][col] + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
