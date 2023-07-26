package fr.uga.battleship;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GraphicNavalGrid extends NavalGrid {

	private GraphicGrid graphicGrid;

	public GraphicNavalGrid(int gridSize) {
		super(gridSize);
		graphicGrid = new GraphicGrid(gridSize);
	}

	@Override
	public boolean addShip(Ship n) {
		boolean b = super.addShip(n);
		if (b) {
			graphicGrid.color(n.getBegin(), n.getEnd(), Color.YELLOW);
		}
		return b;
	}

	@Override
	public boolean shootAt(Coordinate c) {
		boolean b = super.shootAt(c);
		if (b) {
			graphicGrid.color(c, Color.RED);
		} else {
			graphicGrid.color(c, Color.BLUE);
		}
		return b;
	}

	public GraphicGrid getGraphicGrid() {
		return graphicGrid;
	}

}
