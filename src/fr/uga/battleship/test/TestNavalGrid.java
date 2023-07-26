package fr.uga.battleship.test;

import fr.uga.battleship.Coordinate;
import fr.uga.battleship.NavalGrid;
import fr.uga.battleship.Ship;

public class TestNavalGrid {
	public static void main(String[] args) {

		NavalGrid g = new NavalGrid(10);

		Ship s = new Ship(new Coordinate("D3"), 3, false);
		g.addShip(s);

		s = new Ship(new Coordinate("B8"), 2, true);
		boolean res = g.addShip(s);
		System.out.println(res);

		res = g.shootAt(new Coordinate("C3"));
		// System.out.println(g.(new Coordinate("C3")));
		res = g.shootAt(new Coordinate("D5"));
		res = g.shootAt(new Coordinate("D4"));
		res = g.shootAt(new Coordinate("D3"));

		res = g.shootAt(new Coordinate("B8"));
		res = g.shootAt(new Coordinate("C8"));

		System.out.println(g);

		System.out.println(g.isSunk(new Coordinate("D3")));
		System.out.println(g.gameover());

		NavalGrid g2 = new NavalGrid(10);
		g2.automaticPosition(new int[] { 2, 2, 3, 4, 5 });
		System.out.println(g2);

	}
}
