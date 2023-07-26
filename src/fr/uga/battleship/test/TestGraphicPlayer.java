package fr.uga.battleship.test;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import fr.uga.battleship.AutomaticPlayer;
import fr.uga.battleship.GraphicNavalGrid;
import fr.uga.battleship.GraphicPlayer;

public class TestGraphicPlayer {

	public static GraphicNavalGrid createGrid() {
		GraphicNavalGrid g1 = new GraphicNavalGrid(10);
		g1.automaticPosition(new int[] { 2, 3, 3, 4, 5 });
		return g1;
	}

	public static void startGame(String nickName) {
		GraphicNavalGrid ng1 = createGrid();
		GraphicPlayer p1 = new GraphicPlayer(nickName, ng1);
		GraphicNavalGrid ng2 = createGrid();
		AutomaticPlayer p2 = new AutomaticPlayer("Robot", ng2);
		p1.playWith(p2);
		p1.close();
		p2.close();
	}

	public static void main(String[] args) {

		JFrame f = new JFrame("BattleShip");
		Container c = f.getContentPane();
		c.setLayout(new FlowLayout());

		c.add(new JLabel("Your Nickname: "));
		JTextField tf = new JTextField(10);
		c.add(tf);
		JButton bt = new JButton("Play!");

		bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.setVisible(false);
				new Thread() {
					public void run() {
						String nickName = tf.getText();// "John"; //
						startGame(nickName);
						while (true) {
							int res = JOptionPane.showConfirmDialog(null, "Do you want to start a new game?",
									"New game?", JOptionPane.YES_NO_OPTION);
							if (res == JOptionPane.YES_OPTION) {
								startGame(nickName);
							} else {
								System.exit(0);
							}
						}
					}
				}.start();

			}
		});

		c.add(bt);
		f.pack();
		f.setVisible(true);

	}

}
