package fr.uga.battleship.test;

import fr.uga.battleship.NavalGrid;
import fr.uga.battleship.Player;
import fr.uga.battleship.TextPlayer;

public class TestTextPlayer {
	private static TextPlayer createTextPlayer(String name) {
		NavalGrid g1 = new NavalGrid(10);
		g1.automaticPosition(new int[] { 2, 3, 3, 4, 5 });
		return new TextPlayer(name, g1);
	}

	public static void main(String[] args) {
		Player p1 = createTextPlayer("John");
		Player p2 = createTextPlayer("Sarra");
		p1.playWith(p2);
	}
}
