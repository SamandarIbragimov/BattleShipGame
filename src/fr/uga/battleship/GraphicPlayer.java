package fr.uga.battleship;

import java.awt.*;

import javax.swing.*;

public class GraphicPlayer extends LocalPlayer {

	private JFrame f;
	private GraphicGrid trackingGrid;

	public GraphicPlayer(String nick, GraphicNavalGrid primaryGrid) {
		super(nick, primaryGrid);
		this.trackingGrid = new GraphicGrid(primaryGrid.getGridSize());
		initGUI();
	}

	// GUI : Graphical User Interface
	/**
	 * Method for initialization of the user interface
	 */
	private void initGUI() {
		SwingUtilities.invokeLater(new Thread() {
			public void run() {
				f = new JFrame("Player : " + GraphicPlayer.this.getNickname());
				f.setSize(600, 300);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setVisible(true);

				trackingGrid.setBorder(BorderFactory.createTitledBorder("Tracking Grid"));

				GraphicGrid primaryGraphicGrid = ((GraphicNavalGrid) getPrimaryGrid()).getGraphicGrid();

				primaryGraphicGrid.setBorder(BorderFactory.createTitledBorder("Primary Grid"));

				JPanel p = new JPanel();
				p.setLayout(new GridLayout(1, 2));
				p.add(trackingGrid);
				p.add(primaryGraphicGrid);

				f.setContentPane(p);

				// f.setContentPane(trackingGrid);
			}
		});
	}

	@Override
	public Coordinate chooseAttack() {
		return trackingGrid.getSelectedCoordinate();
	}

	@Override
	public void feedbackAttack(Coordinate c, int status) {
		if (status == HIT || status == SUNK) {
			trackingGrid.color(c, Color.orange);
			if (status == SUNK) {
				// JOptionPane.showMessageDialog(trackingGrid, "Yeah! You sunk a
				// ship!");
			} else {
				JOptionPane.showMessageDialog(trackingGrid, "Yeah! You hit a ship!");
			}
		} else if (status == MISS) {
			trackingGrid.color(c, Color.blue);
			// JOptionPane.showMessageDialog(trackingGrid, "You miss it!");
		}
		if (status == GAMEOVER) {
			JOptionPane.showMessageDialog(trackingGrid, "You Win !!!");
		}
	}

	@Override
	public void feedbackDefensis(Coordinate c, int status) {
		if (status == HIT || status == SUNK) {
			if (status == SUNK) {
				JOptionPane.showMessageDialog(trackingGrid, "Oops! One of your ships is sunk!");
			} else {
				// JOptionPane.showMessageDialog(trackingGrid, "Oops! One of
				// your ships is hit!");
			}
		} else if (status == MISS) {
			// JOptionPane.showMessageDialog(trackingGrid, "Yeak, your opponent
			// miss it");
		}
		if (status == GAMEOVER) {
			JOptionPane.showMessageDialog(trackingGrid, "You Lost !!!");
		}

	}

	@Override
	public void close() {
		if (f != null) {
			// f.setVisible(false);
			f.dispose();
		}

	}

}
