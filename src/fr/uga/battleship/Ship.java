package fr.uga.battleship;

/**
 * Class used to represent a ship
 *
 * @author jerome.david@univ-grenoble-alpes.fr
 *
 */
public class Ship {

	private Coordinate begin;
	private Coordinate end;

	private Coordinate[] hits;
	private int hitsNumber;

	/**
	 * Initialize a ship
	 *
	 * @param begin
	 *            the coordinate of the top/left corner of the ship
	 * 
	 * @param length
	 *            the size of the ship (its number of cases)
	 * @param isVertical
	 *            : true id the ship positions vertically, false if it positions
	 *            horizontally
	 */
	public Ship(Coordinate begin, int length, boolean isVertical) {
		this.begin = begin;
		if (isVertical) {
			end = new Coordinate(begin.getRow() + length - 1, begin.getColumn());
		} else {
			end = new Coordinate(begin.getRow(), begin.getColumn() + length - 1);
		}
		hits = new Coordinate[length];
		hitsNumber = 0;
	}

	/**
	 * Give a string representation of a ship. For instance a ship whith
	 * begin=B1 and end=B4 will be presented as "(Ship, B1, 4, vertical)"
	 * 
	 * @return the string representation of the ship
	 */
	public String toString() {
		int diffR = end.getRow() - begin.getRow();
		int diffC = end.getColumn() - begin.getColumn();
		String align = "vertical";
		int length = diffC;
		if (diffR == 0) {
			align = "horizontal";
			length = diffC;
		}

		return "(Ship, " + length + "," + align + ")";
	}

	/**
	 * gives the top/left corner of the ship
	 *
	 * @return la coordonnée de begin du navire
	 */
	public Coordinate getBegin() {
		return begin;
	}

	/**
	 * give the bottom/right corner of the ship
	 *
	 * @return la coordonnée de end du navire
	 */
	public Coordinate getEnd() {
		return end;
	}

	/**
	 * Checks if the coordinate c is contained in the ship
	 *
	 * @param c
	 *            a not null reference to a coordinate
	 * @return true iff c is in the ship
	 */
	public boolean contains(Coordinate c) {
		return (c.getRow() >= begin.getRow() && c.getRow() <= end.getRow() && c.getColumn() >= begin.getColumn()
				&& c.getColumn() <= end.getColumn());
	}

	/**
	 * Checks if the ship is adjacent to the ship given as parameter
	 *
	 * @param n
	 *            a not null reference to a ship
	 * @return
	 */
	public boolean adjacentTo(Ship n) {
		return ((begin.getRow() == n.end.getRow() + 1 || end.getRow() == n.begin.getRow() - 1) && // et
																									// ils
																									// partagent
																									// au
																									// moins
																									// une
																									// colonne
																									// en
																									// commun
				(begin.getColumn() <= n.end.getColumn() && n.begin.getColumn() <= end.getColumn())) || // OU
				((begin.getColumn() == n.end.getColumn() + 1 || n.begin.getColumn() == end.getColumn() + 1) && // et
																												// ils
																												// partagent
																												// au
																												// moins
																												// une
																												// ligne
																												// en
																												// commun
						(begin.getRow() <= n.end.getRow() && n.begin.getRow() <= end.getRow()));
	}

	/**
	 * checks if the ship overlaps the ship given as parameter
	 *
	 * @param n
	 *            a not null reference to a ship
	 * @return
	 */
	public boolean overlaps(Ship n) {

		return // les navires doivent partager au moins une ligne
		(begin.getRow() <= n.end.getRow() && n.begin.getRow() <= end.getRow()) && // et
																					// au
																					// moins
																					// une
																					// colonne
				(begin.getColumn() <= n.end.getColumn() && n.begin.getColumn() <= end.getColumn());
	}

	/**
	 * Proceed to the reception of a potential hit on the given coordinate. If
	 * the coordinate is contained in the ship then the ship is hit, i.e., c is
	 * added to the array hits.
	 *
	 * @param c
	 *            a not null reference to the coordinate representing the hit
	 * @return true true if it hits the ship, false otherwise
	 */
	public boolean receiveHit(Coordinate c) {
		if (contains(c)) {
			if (isHit(c)) {
				return true;
			}
			hits[hitsNumber] = c;
			hitsNumber++;
			return true;
		}
		return false;
	}

	/**
	 * Checks if the ship has been hit on the given coordinate.
	 *
	 * @param c
	 *            a not null reference to the coordinate to test
	 * @return true if c is in the hits array
	 */
	public boolean isHit(Coordinate c) {
		for (int i = 0; i < hitsNumber; i++) {
			if (hits[i].equals(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the ship has been hit at least once.
	 *
	 * @return true if there is at leadt one element with hits array
	 */
	public boolean hasBeenHit() {
		return hitsNumber > 0;
	}

	/**
	 * Checks if the ship is sunk. A ship is sunk if all his square are hit
	 *
	 * @return true if the ship is sunk
	 */
	public boolean isSunk() {
		return hitsNumber == hits.length;
	}

}