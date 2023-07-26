package fr.uga.battleship.net;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;

import fr.uga.battleship.Player;

/* SERVER PROTOCOL
 * 
 * 
 * c1 first client, c2 second one


 */

public class BattleshipServer {

	private ServerSocket server;
	private Socket socketWaiting;
	private String nameWaiting;

	private boolean start;
	private boolean running;

	public BattleshipServer(int portNumber) throws IOException {
		server = new ServerSocket(portNumber);
		socketWaiting = null;
		nameWaiting = null;
		start = true;
	}

	public void stopServer() {
		start = false;
	}

	public void startServer() {
		if (running)
			return;
		start = true;
		running = true;
		/*
		 * WHILE TRUE read name
		 * 
		 * if (client c1 waiting) THEN startGame
		 * 
		 * ELSE { make the client waiting for connection }
		 * 
		 * END WHILE
		 */
		while (start) {
			try {
				Socket s = server.accept();
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String name = dis.readUTF();

				if (socketWaiting != null) {
					Socket c1 = socketWaiting;
					String name1 = nameWaiting;
					socketWaiting = null;
					nameWaiting = null;
					new Thread() {
						public void run() {
							try {
								startGame(c1, name1, s, name);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}.start();

				} else {
					socketWaiting = s;
					nameWaiting = name;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		running = false;
	}

	private void startGame(Socket c1, String name1, Socket c2, String name2) throws IOException {
		DataInputStream dis1 = new DataInputStream(c1.getInputStream());
		DataInputStream dis2 = new DataInputStream(c2.getInputStream());

		DataOutputStream dos1 = new DataOutputStream(c1.getOutputStream());
		DataOutputStream dos2 = new DataOutputStream(c2.getOutputStream());

		/*
		 * send true to c1 send false to c2
		 */
		dos1.writeBoolean(true);
		dos2.writeBoolean(false);

		int status = Player.OTHER_CASE;
		while (status != Player.GAMEOVER) {
			// receive and send coordinate
			int row = dis1.readInt();
			int col = dis1.readInt();
			dos2.writeInt(row);
			dos2.writeInt(col);

			// receive and send status
			status = dis2.readInt();
			dos1.writeInt(status);

			DataInputStream disTmp = dis1;
			DataOutputStream dosTmp = dos1;
			dis1 = dis2;
			dos1 = dos2;
			dis2 = disTmp;
			dos2 = dosTmp;
		}
		/*
		 * dis1.close(); dis2.close(); dos1.close(); dos2.close();
		 */
		c1.close();
		c2.close();

		/*
		 * 
		 * 
		 * DO read a coordinate coord from c1 write coord to c2
		 * 
		 * read feedback-status from c2 write feedback-status to c1
		 * 
		 * switch c1 and c2
		 * 
		 * WHILE (feedback-status /= gameover)
		 * 
		 */
	}

}
