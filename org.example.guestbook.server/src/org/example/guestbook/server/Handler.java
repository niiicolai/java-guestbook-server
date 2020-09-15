package org.example.guestbook.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Handler implements Runnable {
	private final Socket socket;
	private String clientMsg, serverMsg;
	Handler(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			var inStream = new DataInputStream(socket.getInputStream());
			var outStream = new DataOutputStream(socket.getOutputStream());;
			var cmdHandler = new CommandHandler();
			
			while (!cmdHandler.isStopped()) {	
				clientMsg = inStream.readUTF();
				serverMsg = cmdHandler.Process(clientMsg);
				outStream.writeUTF(serverMsg);
				outStream.flush();
			}

			inStream.close();
			outStream.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
