package org.example.guestbook.server;

public class Logger {
	
	public static void Log(String str) {
		System.out.println(str);
	}

	public static void LogClient(String str, int clientNumber) {
		String txt = "Client NO: " + clientNumber + " - " + str;
		Log(txt);
	}
}
