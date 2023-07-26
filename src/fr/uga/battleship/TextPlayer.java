package fr.uga.battleship;

import java.util.Scanner;

public class TextPlayer extends LocalPlayer {

	private Scanner sc;

	public TextPlayer(String nick, NavalGrid primaryGrid) {
		super(nick, primaryGrid);
		sc = new Scanner(System.in);
	}

	@Override
	public Coordinate chooseAttack() {
		System.out.println("Hello " + getNickname());
		Coordinate c = null;
		do {
			try {
				System.out.println("Choose a coordinate to attack: ");
				c = new Coordinate(sc.nextLine());
			} catch (RuntimeException e) {
				System.out.println("Error: " + e.getMessage());
			}
		} while (c == null);
		return c;

	}

	@Override
	public void feedbackAttack(Coordinate c, int status) {
		if (status == Player.HIT) {
			System.out.println("Congrats " + this.getNickname() + ", you hit a ship on " + c);
		} else if (status == Player.MISS) {
			System.out.println("Sorry " + this.getNickname() + ", you're shot fall into water on " + c);
		} else if (status == Player.SUNK) {
			System.out.println("Congrats " + this.getNickname() + ", you sunk a ship on " + c);
		} else if (status == Player.GAMEOVER) {
			System.out.println("Congrats " + this.getNickname() + ", you WIN !!!");
		}
	}

	@Override
	public void feedbackDefensis(Coordinate c, int status) {
		if (status == Player.HIT) {
			System.out.println("Sorry " + this.getNickname() + ", you're ship has been hit on " + c);
		} else if (status == Player.MISS) {
			System.out.println("Yeah " + this.getNickname() + ", you're opponent shot falled into water on " + c);
		} else if (status == Player.SUNK) {
			System.out.println("Sorry " + this.getNickname() + ", you're ship has been sunk on " + c);
		} else if (status == Player.GAMEOVER) {
			System.out.println("Sorry " + this.getNickname() + ", you LOST !!!");
		}
		System.out.println("You're grid :");
		System.out.println(this.getPrimaryGrid());
	}

	@Override
	public void close() {
		// NOTHING TO DO

	}

}
