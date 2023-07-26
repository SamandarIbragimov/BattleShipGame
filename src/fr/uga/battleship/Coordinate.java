package fr.uga.battleship;

/**
 * Class representing a coordinate for the battleship
 *
 * @author jerome.david@univ-grenoble-alpes.Fr
 *
 */
public class Coordinate implements Comparable<Coordinate> {

	/**
	 * Constant for NORTH direction
	 */
	public static final int NORTH = 0;
	/**
	 * Constant for EAST direction
	 */
	public static final int EAST = 1;
	/**
	 * Constant for SOUTH direction
	 */
	public static final int SOUTH = 2;
	/**
	 * Constant for WEST direction
	 */
	public static final int WEST = 3;

	private int row;
	private int column;

	/**
	 * Initialize a coordinate from its textual notation in the battleship
	 * system. examples: A1, B3, etc.
	 *
	 * @param coordinate
	 *            represents the textual notation of the coordinate
	 */
	public Coordinate(String coordinate) {
		char c = coordinate.charAt(0);
		if (c < 'A' || c > 'Z') {
			throw new RuntimeException(coordinate + " have to start with a letter between A and Z");
		}
		row = c - 'A';

		column = Integer.parseInt(coordinate.substring(1)) - 1;
		if (column < 0) {
			throw new RuntimeException(coordinate + " have to finish with a POSITIVE integer");
		}
	}

	/**
	 * Initialize a coordinate given a row and column indexes
	 *
	 * @param row
	 *            (starts from 0)
	 * @param column
	 *            (start from 0)
	 */
	public Coordinate(int row, int column) {
		if (row < 0) {
			throw new RuntimeException("The row has to be positive");
		}
		if (column < 0) {
			throw new RuntimeException("The column has to be positive");
		}
		this.row = row;
		this.column = column;
	}

	/**
	 * Gives the row
	 *
	 * @return the row index starting from 0
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gives the column
	 *
	 * @return the column index starting from 0
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * String representation of the coordinate in the battleship system
	 */
	public String toString() {
		return ((char) ('A' + row)) + "" + (column + 1);
	}

	/**
	 * Test if two coordinate are equals, i.e.
	 * <code>getRow()==((Coordinate)o).getRow() && getColumn()==((Coordinate)o).getColumn()</code>
	 */
	public boolean equals(Object o) {
		if (o instanceof Coordinate) {
			Coordinate c = (Coordinate) o;
			return this.row == c.row && this.column == c.column;
		}
		return false;
	}

	/**
	 * Test if a cordinate is in the neigborhood
	 *
	 * @return <code>true</code> if c is a neighbor of this
	 */
	public boolean neighbor(Coordinate c) {
		return (Math.abs(column - c.column) + Math.abs(row - c.row) == 1);
	}

	/**
	 * Compare two coordinates. A coordinate is smaller than another one if it
	 * is before the the other one following the reader stream (from top to
	 * botton, left to right)
	 *
	 * @param c
	 *            The coordinate to compare with
	 * @return 0 if they are equal, negtive integer if this is smaller than c
	 *         and a positive integer if this is greater than c
	 */
	public int compareTo(Coordinate c) {
		int diffRow = this.row - c.row;
		if (diffRow != 0) {
			return diffRow;
		}
		return this.column - c.column;
	}

}
