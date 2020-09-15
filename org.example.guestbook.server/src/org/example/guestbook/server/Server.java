package org.example.guestbook.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private static int poolSize = 3;
	private static int port = 6666;
	
	public static void main (String[] args) {
		
		NetworkService ns = null;
		
		try {
			ns = new NetworkService(port, poolSize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread(ns).start();
	}
}

