package fr.uga.battleship;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GraphicGrid extends JPanel implements ActionListener {

	private static class JButtonCoordinate extends JButton {

		final Coordinate coordinate;

		JButtonCoordinate(Coordinate c) {
			super();
			this.coordinate = c;
		}
	}

	private JButton[][] bts;

	private Coordinate selectedCoordinate;

	public GraphicGrid(int size) {
		selectedCoordinate = null;
		this.setLayout(new GridLayout(size + 1, size + 1));
		bts = new JButton[size][size];

		this.add(new JLabel(""));
		for (int i = 0; i < size; i++) {
			JLabel lbl = new JLabel("" + (i + 1));
			lbl.setHorizontalAlignment(JLabel.CENTER);
			this.add(lbl);
		}
		for (int i = 0; i < size; i++) {
			JLabel lbl = new JLabel("" + (char) ('A' + i));
			lbl.setHorizontalAlignment(JLabel.CENTER);
			this.add(lbl);
			for (int j = 0; j < size; j++) {
				JButton bt = new JButtonCoordinate(new Coordinate(i, j));

				bt.addActionListener(this);
				bts[i][j] = bt;
				this.add(bt);
			}
		}
		// When we create the grid, the buttons have to be desactivated.
		setClicActive(false);
	}

	public void color(Coordinate c, Color color) {
		JButton bt = bts[c.getRow()][c.getColumn()];// ???
		bt.setBackground(color);
	}

	public void color(Coordinate begin, Coordinate end, Color color) {
		for (int i = begin.getRow(); i <= end.getRow(); i++) {
			for (int j = begin.getColumn(); j <= end.getColumn(); j++) {
				bts[i][j].setBackground(color);
			}
		}
	}

	public void setClicActive(boolean b) {
		for (JButton[] row : bts) {
			for (JButton bt : row) {
				bt.setEnabled(b);
			}
		}
	}

	public synchronized Coordinate getSelectedCoordinate() {
		setClicActive(true);
		// wait the user to click on some square
		// It makes the current Thread waiting to be notified by another thread
		try {
			this.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setClicActive(false);
		return selectedCoordinate;
	}

	@Override
	public synchronized void actionPerformed(ActionEvent evt) {
		JButtonCoordinate bt = (JButtonCoordinate) evt.getSource();

		// 1- search in the bts array which button has been clicked (==bt)
		selectedCoordinate = bt.coordinate;
		// It notifies the thread waiting for user to select a coordinate.
		this.notify();

	}

}
