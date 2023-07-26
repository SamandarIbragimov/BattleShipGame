/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.battleship;

/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */
public abstract class Player {

	public final static int HIT = 1;
	public final static int SUNK = 2;
	public final static int MISS = 3;
	public final static int GAMEOVER = 4;
	public final static int OTHER_CASE = 5;

	private String nickname;
	private int gridSize;

	private Player opponent;

	public Player(String nick, int gridSize) {
		this.nickname = nick;
		this.gridSize = gridSize;
	}

	public Player(int gridSize) {
		this.gridSize = gridSize;
	}

	public abstract Coordinate chooseAttack();

	public abstract void feedbackAttack(Coordinate c, int status);

	public abstract void feedbackDefensis(Coordinate c, int status);

	public abstract int defensis(Coordinate c);

	public abstract void close();

	/**
	 * @return the gridSize
	 */
	protected int getGridSize() {
		return gridSize;
	}

	public void playWith(Player p) {
		if (this.opponent != null) {
			throw new RuntimeException("You are already playing with " + opponent.getNickname());
		} else if (p.opponent != null) {
			throw new RuntimeException("You're opponent is already playing");
		} else if (p.getGridSize() != this.getGridSize()) {
			throw new RuntimeException("You do not have the same grid size...");
		}
		this.opponent = p;
		p.opponent = this;

		Player p1 = this;
		Player p2 = opponent;
		int res = 0;
		while (res != GAMEOVER) {
			Coordinate c = p1.chooseAttack();
			res = p2.defensis(c);
			p1.feedbackAttack(c, res);
			p2.feedbackDefensis(c, res);
			// switch the players
			Player x = p1;
			p1 = p2;
			p2 = x;
		}
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname
	 *            the nickname to set
	 */
	protected void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
