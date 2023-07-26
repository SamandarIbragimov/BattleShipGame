package fr.uga.battleship;

import java.util.Arrays;

/**
 * The Basic AUtomatic player consists in choosing randomly a coordinate that
 * has not been already shoot.
 * 
 * @author jerome.david@univ-grenoble-alpes.fr
 *
 */
public class AutomaticPlayer extends LocalPlayer {

	private Coordinate[] previous;
	private int previousNb;
	// the coordinate of the first hit on the current ship. null if no ship
	// detected
	private Coordinate origin;

	// the direction that we actually follow
	// use the NORTH, EAST, SOUTH, WEST constants from Coordinate class
	private int direction;

	private Coordinate nextShot;
	private int nbHits;

	public AutomaticPlayer(String nick, NavalGrid primaryGrid) {
		super(nick, primaryGrid);
		previous = new Coordinate[10];
		previousNb = 0;
		origin = null;
		direction = Coordinate.NORTH;
		nextShot = newRandom();
		nbHits = 0;

	}

	private boolean isInPrevious(Coordinate c) {
		for (int i = 0; i < previousNb; i++) {
			if (previous[i].equals(c)) {
				return true;
			}
		}
		return false;
	}

	private void addToPrevious(Coordinate c) {
		if (c == null)
			return;

		// check if there are still space in previous array
		// otherwise, create a bigger one
		int whatEverYouWant = 10;
		if (previous.length == previousNb) {
			previous = Arrays.copyOf(previous, previous.length + whatEverYouWant);
		}

		previous[previousNb] = c;
		previousNb += 1;
	}

	/**
	 * return a random coordinate that is not in previous array
	 * 
	 * @return
	 */
	private Coordinate newRandom() {
		int gz = getPrimaryGrid().getGridSize();
		Coordinate c = new Coordinate((int) (Math.random() * gz), (int) (Math.random() * gz));
		if (isInPrevious(c)) {
			return newRandom();
		}
		return c;

	}

	/**
	 * Generate the coordinate that is neighboor of c given the direction. If
	 * there is no neighbooring coordinate (i.e. we go out of the grid, then
	 * return null)
	 * 
	 * @param c
	 * @return
	 */
	private Coordinate nextFollowingDirection(Coordinate c) {
		try {
			switch (direction) {
			case Coordinate.NORTH:
				return new Coordinate(c.getRow() - 1, c.getColumn());
			case Coordinate.EAST:
				// we go out of the grid, in that case, return null;
				if (c.getColumn() + 1 == this.getPrimaryGrid().getGridSize()) {
					return null;
				}
				return new Coordinate(c.getRow(), c.getColumn() + 1);
			case Coordinate.SOUTH:
				if (c.getRow() + 1 == this.getPrimaryGrid().getGridSize()) {
					return null;
				}
				return new Coordinate(c.getRow() + 1, c.getColumn());
			case Coordinate.WEST:
				return new Coordinate(c.getRow(), c.getColumn() - 1);
			}
		} catch (RuntimeException e) {
			// if we have reach the limit of the grid.
		}
		return null;
	}

	/**
	 * turn on in the clockwise the direction
	 */
	private void turn() {
		direction = (direction + 1) % 4;
	}

	/**
	 * Change to the reverse direction
	 */
	private void reverse() {
		direction = (direction + 2) % 4;
	}

	@Override
	public Coordinate chooseAttack() {
		addToPrevious(nextShot);
		return nextShot;
	}

	@Override
	public void feedbackAttack(Coordinate c, int status) {
		// TODO Auto-generated method stub

		if (status == HIT || status == SUNK) {
			nbHits += 1;
		}
		// miss and no ship detected (i.e. origin == null)
		// nextShot = choose a random coordinate
		if (status == MISS && origin == null) {
			nextShot = newRandom();
		}
		// status hit and ship not detected (i.e. origin ==null)
		// origin=c;
		// nextShot = origin +1 in following direction

		else if (status == HIT && origin == null) {
			origin = c;
			nextShot = nextFollowingDirection(c);
			;
			while (nextShot == null || isInPrevious(nextShot)) {
				turn();
				nextShot = nextFollowingDirection(c);
			}
		}
		// status hit and ship detected (i.e. origin !=null)
		// it means that we know the direction :
		// we continue this direction (from c), if we can, (i.e. the next is
		// still in the grid)
		// else we go in the opposite direction (from origin)
		else if (status == HIT && origin != null) {
			nextShot = nextFollowingDirection(c);
			if (nextShot == null || isInPrevious(nextShot)) {
				reverse();
				nextShot = nextFollowingDirection(origin);
			}
		}
		// status miss, ship detected and we do not know the direction
		// (nbHits==1) :
		// turn around

		else if (status == MISS && nbHits == 1) {
			nextShot = null;
			while (nextShot == null || isInPrevious(nextShot)) {
				turn();
				nextShot = nextFollowingDirection(origin);
			}
		}
		// status miss, ship detected (i.e. origin !=null) and we know the
		// direction (nbHits>1) :
		// It means that we have to go in the opposite direction (from origin)

		else if (status == MISS && nbHits > 1) {
			reverse();
			nextShot = nextFollowingDirection(origin);
		}
		// status : sunk
		// we assign null to origin, NORTH to direction, nbHits=0
		// we choose a random coordinate
		else if (status == SUNK) {
			// we have here to blacklist all the neighboring coordinate to the
			// shiop that we sunk

			// get following coordinate
			Coordinate ex = nextFollowingDirection(c);
			this.addToPrevious(ex);
			System.out.println("Excluded: " + ex);
			reverse();
			Coordinate current = c;
			for (int i = 0; i < nbHits; i++) {
				// System.out.println(current);
				if (direction == Coordinate.NORTH || direction == Coordinate.SOUTH) {
					// vertical
					Coordinate c1 = new Coordinate(current.getRow(), current.getColumn() + 1);
					if (current.getColumn() > 0) {
						Coordinate c2 = new Coordinate(current.getRow(), current.getColumn() - 1);
						System.out.println("Excluded: " + c2);
						addToPrevious(c2);
					}
					addToPrevious(c1);
					System.out.println("Excluded: " + c1);

				} else {
					// horizontal
					Coordinate c1 = new Coordinate(current.getRow() + 1, current.getColumn());
					if (current.getRow() > 0) {
						Coordinate c2 = new Coordinate(current.getRow() - 1, current.getColumn());
						addToPrevious(c2);
						System.out.println("Excluded: " + c2);
					}

					addToPrevious(c1);
					System.out.println("Excluded: " + c1);

				}
				current = nextFollowingDirection(current);

			}
			addToPrevious(current);
			System.out.println("Excluded: " + current);

			origin = null;
			direction = Coordinate.NORTH;
			nbHits = 0;
			nextShot = newRandom();
		} else if (status == GAMEOVER) {

		} else {
			throw new RuntimeException("Case not considered, there is a pb, debug your code ;-)");
		}

	}

	@Override
	public void feedbackDefensis(Coordinate c, int status) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// NOTHING TO DO HERE
	}

}
