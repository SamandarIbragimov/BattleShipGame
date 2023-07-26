package fr.uga.battleship.test;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.uga.battleship.GraphicNavalGrid;

public class TestGraphicNavalGrid {

	public static void main(String[] args) {
		GraphicNavalGrid g = new GraphicNavalGrid(10);
		g.automaticPosition(new int[] { 5, 5, 3, 1 });
		SwingUtilities.invokeLater(new Thread() {
			public void run() {
				JFrame f = new JFrame("Hello world!");
				f.setSize(300, 300);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);
				f.setContentPane(g.getGraphicGrid());

			}
		});
	}
}
