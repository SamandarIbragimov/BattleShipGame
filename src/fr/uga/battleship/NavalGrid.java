package fr.uga.battleship;

import java.util.Arrays;

/**
 * Class that represents a square grid that contains an arrangement of ships.
 * Ships owned by this grid are recorded and shots are recorded
 *
 * @author jerome.david@univ-grenoble-alpes.fr
 */
public class NavalGrid {

	/**
	 * array that stores the ships that are contains in the grid. this arrays
	 * acts as a buffer, i.e., its capacity is higher that the ships effectively
	 * contained in the grid. The number of ships contained in this array is
	 * given by the attribute shipsNumber below.
	 */
	private Ship[] ships;
	/**
	 * number of ship effectively contained in the grid
	 */
	private int shipsNumber;

	/**
	 * size of the grid
	 */
	private int gridSize;

	/**
	 * array that stores the shots revceived on that grid. this arrays acts as a
	 * buffer, i.e., its capacity is higher that the shots effectively received
	 * on the grid. The number of shots contained in this array is given by the
	 * attribute shotsNumber below.
	 */
	private Coordinate[] shots;
	/**
	 * number of shots effectively received on this grid
	 */
	private int shotsNumber;

	/**
	 * Initialize a grid gien its size. The initial capacity of ship array is 10
	 * and the capacity of shots is the number of squares in the grid
	 *
	 * @param gridSize
	 */
	public NavalGrid(int gridSize) {
		if (gridSize < 1) {
			throw new RuntimeException("Grid has to be larger than 0");
		}
		ships = new Ship[10];
		shipsNumber = 0;
		shots = new Coordinate[gridSize * gridSize];
		shotsNumber = 0;
		this.gridSize = gridSize;
	}

	/**
	 * String representation of the grid. It should lokks like: 1 2 3 4 5 6 7 8
	 * 9 10 A . . . . . . . . . . B . . . # # # . . . . C . . . . . . # X # . D
	 * . . O . . . . . . E . . . . . # . . . . F . . . O . # . . . . G . # # . .
	 * # . . . . H . . . . . # . . . . I . . . . . . . O . . J . . . . . . . . .
	 * .
	 * 
	 * '.' is for a free square. '#" for a square contained in a ship, 'O' for a
	 * hit free square, 'X' for a hit ship square
	 */
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append(' ');
		for (int i = 0; i < gridSize; i++) {
			b.append(' ');
			b.append(i + 1);

		}
		b.append('\n');
		for (int i = 0; i < gridSize; i++) {
			b.append((char) ('A' + i));

			for (int j = 0; j < gridSize; j++) {
				b.append(' ');
				Coordinate c = new Coordinate(i, j);
				if (this.isHit(c)) {
					b.append('X');
				} else if (this.isMiss(c)) {
					b.append('O');
				} else if (this.isShip(c)) {
					b.append('#');
				} else {
					b.append('.');
				}

			}
			b.append('\n');
		}
		return b.toString();
	}

	private boolean isShip(Coordinate c) {
		for (int i = 0; i < shipsNumber; i++) {
			if (ships[i].contains(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add a ship in the grid. A ship is added in the grid only if: it fits in
	 * the grid, it does not overlaps and is nt adajent to another ship already
	 * contained in this grid
	 *
	 * @param n
	 *            the ship to be added
	 * @return true only if the ship has been added.
	 */
	public boolean addShip(Ship n) {
		// TO BE CHANGED

		if (!(isInTheGrid(n.getBegin()) && isInTheGrid(n.getEnd()))) {
			return false;
		}
		for (int i = 0; i < shipsNumber; i++) {
			if (ships[i].overlaps(n) || ships[i].adjacentTo(n)) {
				return false;
			}
		}

		if (this.shipsNumber == ships.length) {
			ships = Arrays.copyOf(ships, ships.length + 10);
			/*
			 * Ship[] newShipArray = new Ship[ships.length+10]; for (int i=0 ;
			 * i< ships.length ; i++) { newShipArray[i]=ships[i]; }
			 * ships=newShipArray;
			 */
		}
		ships[shipsNumber] = n;
		shipsNumber += 1;
		return true;
	}

	/**
	 * Automatically position and add shipSizes.length ship in the grid. The
	 * sizes of the ships is given by the shipSizes array. The positionning of
	 * ships is randomly generated
	 *
	 * @param shipSizes
	 *            array that gives the sizes of the ship to be added
	 */
	public void automaticPosition(int[] shipSizes) {
		for (int length : shipSizes) {
			// optional
			if (length > gridSize) {
				throw new RuntimeException("Grid too small for a ship of length " + length);
			}

			Ship s = null;
			do {
				int r = (int) (Math.random() * gridSize);
				int c = (int) (Math.random() * gridSize);
				Coordinate b = new Coordinate(r, c);
				boolean vertical = Math.random() < 0.5;

				s = new Ship(b, length, vertical);
			} while (!addShip(s));
		}
	}

	/**
	 * Check if the given coordinate is in the grid
	 *
	 * @param c
	 *            not null reference to the coordinate to check
	 * @return true if the coordinate is in the grid
	 */
	private boolean isInTheGrid(Coordinate c) {
		return c.getRow() < gridSize && c.getColumn() < gridSize;
	}

	/**
	 * Check if the coordinate has already been shot, i.e. it is contained in
	 * the shots array
	 *
	 * @param c
	 *            the coordinate to check
	 * @return true if the coordinate has been already shot
	 */
	private boolean hasBeenShot(Coordinate c) {
		if (!isInTheGrid(c)) {
			return false;
		}
		for (int i = 0; i < shotsNumber; i++) {
			if (shots[i].equals(c)) {

				return true;
			}
		}
		return false;
	}

	// ==
	// o.equals(...)

	/**
	 * Mark this coordinate as shot (if not already in shots). The shot is also
	 * propagate to all the ships contained in the grid.
	 *
	 * @param c
	 *            a not null reference to a coordinate to shoot at
	 * @return true if the shot hits a ship
	 */
	public boolean shootAt(Coordinate c) {
		// TO BE CHANGED
		// verify that c is in the grid
		// verify that c in not already in shots
		if (c == null) {
			return false;
		}
		if (!isInTheGrid(c) || hasBeenShot(c)) {
			return false;
		}

		// add c to shots
		shots[shotsNumber] = c;
		shotsNumber += 1;

		// for each ship, send the shot c to it (ships have the method
		// receiveHit)
		for (int i = 0; i < shipsNumber; i++) {
			if (ships[i].receiveHit(c)) {

				return true;
			}
		}

		// return true id at least ship has returned true with receiveHit

		return false;
	}

	/**
	 * Check if one of the ships has been hit at this coordinate
	 *
	 * @param c
	 *            a not null reference to a coordinate
	 * @return true if there is one ship hit at this coordinate
	 */
	public boolean isHit(Coordinate c) {
		if (hasBeenShot(c)) {
			for (int i = 0; i < shipsNumber; i++) {
				if (ships[i].isHit(c)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if the coordinate is the shot array but missed all the ships
	 *
	 * @param c
	 *            a not null reference to a coordinate
	 * @return true if c is in the grid, in shots but missed all the ships
	 */
	public boolean isMiss(Coordinate c) {
		if (hasBeenShot(c)) {
			for (int i = 0; i < shipsNumber; i++) {
				if (ships[i].isHit(c)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Check if there is a sunk ship at this coordinate
	 *
	 * @param c
	 *            a not null reference to a coordinate
	 * @return true if there is a ship at this coordinate and if it is sunk
	 */
	public boolean isSunk(Coordinate c) {
		if (hasBeenShot(c)) {
			for (int i = 0; i < shipsNumber; i++) {
				if (ships[i].isHit(c) && ships[i].isSunk()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if all the ships on this grid are sunk
	 *
	 * @return true if all the ships on this grid are sunk
	 */
	public boolean gameover() {
		for (int i = 0; i < shipsNumber; i++) {
			if (!ships[i].isSunk()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Give the size of the grid
	 *
	 * @return the size of the grid
	 */
	public int getGridSize() {
		return gridSize;
	}

}
