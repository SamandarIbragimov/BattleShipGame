package fr.uga.battleship.test;

import java.io.*;
import java.net.*;

public class TestSocketServer {

	public static void main(String[] args) throws IOException {

		// creates a server socket that will listen on the port 8080
		ServerSocket server = new ServerSocket(8080);

		System.out.println("waiting for connection");

		while (true) {
			// ask the server socket to wait for a connection (from a client)
			Socket s = server.accept();
			// now the connection is established and a socket s as been created
			// for dealing with the connection
			System.out.println("connected");

			InputStream is = s.getInputStream();
			DataInputStream dis = new DataInputStream(is);

			String sd = dis.readUTF();
			System.out.println(sd);

			// Code for reading text data line by line
			/*
			 * BufferedReader r = new BufferedReader(new InputStreamReader(is));
			 * 
			 * String line=null; while ((line=r.readLine())!=null) {
			 * System.out.println(line); }
			 */

			// we close the connection
			s.close();
		}
		// we close the server
		// server.close();

	}

}
