package org.example.guestbook.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	
	public static void main (String[] args) {
		Server.Start();
	}
	
	private static String startServerMessage = "Starting server...";
	private static String newConnectionMessage = " - Established connection...";
	
	private static int port = 6666;
	private static void Start() {
		
		try {
			Logger.Log(startServerMessage);
			
			var serverSocket = new ServerSocket(port);
			var clientNumber = 0;

			while (true) {
				clientNumber++;
				var socket = serverSocket.accept();
				
				Logger.LogClient(newConnectionMessage, clientNumber);
				var connection = new Connection(socket, clientNumber);
				connection.start();
			}
			
			// Unreachable code
			//serverSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

