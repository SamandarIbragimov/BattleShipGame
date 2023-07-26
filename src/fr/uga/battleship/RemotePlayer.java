package fr.uga.battleship;

import java.io.*;
import java.net.*;

public class RemotePlayer extends Player {

	private Socket s;
	private DataInputStream dis;
	private DataOutputStream dos;

	public RemotePlayer(LocalPlayer opponent, InetAddress ip, int port) throws IOException {
		super(opponent.getGridSize());
		s = new Socket(ip, port);

		dis = new DataInputStream(s.getInputStream());
		dos = new DataOutputStream(s.getOutputStream());

		dos.writeUTF(opponent.getNickname());

		boolean start = dis.readBoolean();
		if (start) {
			opponent.playWith(this);
		} else {
			this.playWith(opponent);
		}

	}

	@Override
	public Coordinate chooseAttack() {
		// read coordinate from server
		try {
			int row = dis.readInt();
			int col = dis.readInt();
			Coordinate c = new Coordinate(row, col);
			return c;
		} catch (IOException e) {
			throw new RuntimeException("Error when reading attack from server", e);
		}
	}

	@Override
	public void feedbackAttack(Coordinate c, int status) {
		// send status to the server
		try {
			dos.writeInt(status);
		} catch (IOException e) {
			throw new RuntimeException("Error when sending feedback attack to the server", e);
		}

	}

	@Override
	public void feedbackDefensis(Coordinate c, int status) {
		// NOTHING TO DO

	}

	@Override
	public int defensis(Coordinate c) {
		// send c to server
		// read status from server and return it
		try {
			dos.writeInt(c.getRow());
			dos.writeInt(c.getColumn());
			int status = dis.readInt();
			return status;
		} catch (IOException e) {
			throw new RuntimeException("Error when sending feedback attack to the server", e);
		}
	}

	@Override
	public void close() {
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws UnknownHostException, IOException {

		GraphicNavalGrid g1 = new GraphicNavalGrid(10);
		g1.automaticPosition(new int[] { 2, 3, 3, 4, 5 });
		GraphicPlayer p1 = new GraphicPlayer("Jack", g1);

		Player p2 = new RemotePlayer(p1, InetAddress.getByName("localhost"), 8080);

		p1.close();
		p2.close();

	}

}
