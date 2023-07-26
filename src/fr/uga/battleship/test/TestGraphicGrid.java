package fr.uga.battleship.test;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.uga.battleship.Coordinate;
import fr.uga.battleship.GraphicGrid;

public class TestGraphicGrid {

	public static void main(String[] args) {
		GraphicGrid g = new GraphicGrid(10);

		SwingUtilities.invokeLater(new Thread() {
			public void run() {
				JFrame f = new JFrame("Hello world!");
				f.setSize(300, 300);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);

				g.color(new Coordinate("A3"), Color.pink);

				g.color(new Coordinate("B4"), new Coordinate("B7"), Color.yellow);
				f.setContentPane(g);

			}
		});

		Coordinate c = g.getSelectedCoordinate();
		System.out.println(c);

	}

}
