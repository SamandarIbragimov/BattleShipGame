package fr.uga.battleship.test;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import fr.uga.battleship.net.BattleshipServer;

public class TestServer {
	public static void main(String[] args) throws IOException {

		BattleshipServer s = new BattleshipServer(8080);

		JFrame f = new JFrame("Battleship Server");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = f.getContentPane();
		c.setLayout(new FlowLayout());

		JButton bt = new JButton("Start");

		bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if ("Start".equals(bt.getText())) {
					new Thread() {
						public void run() {
							s.startServer();
							bt.setText("Stop");
						}
					}.start();
				} else {
					s.stopServer();
					bt.setText("Start");
				}
			}

		});

		c.add(bt);

		f.pack();
		f.setVisible(true);

	}
}
