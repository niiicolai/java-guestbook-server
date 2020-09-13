package org.example.guestbook.server;

public class Message {
	private String text;
	
	public Message(String text) {
		this.text = text;
	}
	
	public String GetText() { return text; }
}

