package org.example.guestbook.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {

	private int clientNumber = 0;
	private CommandHandler cmdHandler = null;
	private Socket socket = null;	
	private String clientMsg = null;
	private String serverMsg = null;

	// The command the client should send before
	// the connection is closed.
	private static String exitCommand = "EXIT";
	
	public Connection(Socket socket, int clientNumber) {
		this.socket = socket;
		this.clientNumber = clientNumber;
		this.cmdHandler = new CommandHandler(clientNumber);
	}
	
	public void run() {
		try {
			var inputStream = new DataInputStream(socket.getInputStream());
			var outputStream = new DataOutputStream(socket.getOutputStream());
			var shouldRun = true;
			while (shouldRun)
			{
				// Get client message
				clientMsg = inputStream.readUTF();
				
				// Determines if the loop should
				// break in the next iteration
				shouldRun = !clientMsg.toUpperCase().equals(exitCommand);
				
				// Process client message
				serverMsg = cmdHandler.Process(clientMsg);
				
				Logger.LogClient(clientMsg, clientNumber);
				Logger.LogClient(serverMsg, clientNumber);
				
				// Return server message
				outputStream.writeUTF(serverMsg);
				outputStream.flush();
			}
			
			inputStream.close();
			outputStream.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
