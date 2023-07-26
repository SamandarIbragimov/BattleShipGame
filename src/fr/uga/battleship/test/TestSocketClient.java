package fr.uga.battleship.test;

import java.io.*;
import java.net.*;

import fr.uga.battleship.Coordinate;

public class TestSocketClient {

	public static void main(String[] args) throws UnknownHostException, IOException {

		Socket s = new Socket("localhost", 8080);

		OutputStream os = s.getOutputStream();

		DataOutputStream dos = new DataOutputStream(os);
		// dos.writeUTF("hello");

		Coordinate c = new Coordinate("A5");

		dos.writeInt(c.getRow());
		dos.writeInt(c.getColumn());

		s.close();

	}

}
