/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.battleship;

import javax.swing.JOptionPane;

/**
 *
 * @author Jerome David <jerome.david@univ-grenoble-alpes.fr>
 */
public abstract class LocalPlayer extends Player {

	private NavalGrid primaryGrid;

	public LocalPlayer(String nick, NavalGrid primaryGrid) {
		super(nick, primaryGrid.getGridSize());
		this.primaryGrid = primaryGrid;
	}

	public int defensis(Coordinate c) {
		if (this.primaryGrid.shootAt(c)) {
			if (this.primaryGrid.gameover()) {
				return GAMEOVER;
			} else {
				if (this.primaryGrid.isSunk(c)) {
					return SUNK;
				}
				return HIT;
			}
		} else {
			if (this.primaryGrid.isMiss(c)) {
				return MISS;
			} else {
				return OTHER_CASE;
			}
		}
	}

	public NavalGrid getPrimaryGrid() {
		return primaryGrid;
	}

}
