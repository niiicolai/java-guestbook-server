package org.example.guestbook.server;


import java.net.Socket;
import java.util.ArrayList;

public class CommandHandler {

	private int clientNumber = 0;
	private Command nextCommand = Command.NONE; 
	
	private static String clientPrefix = "Client NO: ";
	
	// # Help
	private static String helpMessage = "Command list:\n GET => list all messages\n CREATE => create new message ";
	
	// # Unknown
	private static String unknownMessage = "Unknown command. Type 'help' to see a list of commands.";
	
	// # GET
	private static String noMessages = "The message list is empty. You can be the first to add one!";
	
	// # CREATE
	private static String createMessageStp1 = "Creating new message. Enter text:";
	private static String createMessageStp2 = "Message saved. Type 'get' to see it on the list.";
	private static String createMessagePrefix = "Message list: ";
	
	private static ArrayList<Message> runtimeMessages = 
			new ArrayList<Message>();
	
	private enum Command {
		GET,
		CREATE,
		HELP,
		NONE
	}
	
	public CommandHandler(int clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String Process(String stringCmd) {
		var command = (nextCommand.equals(Command.NONE) ?
						GetCommand(stringCmd) :
					   nextCommand);
		switch(command) {
			case GET:	 return Get();
			case HELP:	 return Help();
			case CREATE: return Create(stringCmd);
			default: 	 return Unknown();
		}
	}

	private String Get() {
		if (runtimeMessages.size() == 0) return noMessages;
		
		StringBuilder formattedMessages = 
				new StringBuilder();
		
		formattedMessages.append(createMessagePrefix);
		formattedMessages.append(System.lineSeparator());
		
		for (int i = 0; i < runtimeMessages.size(); i++) {
			var text = runtimeMessages.get(i).GetText();
			formattedMessages.append(text);
			formattedMessages.append(System.lineSeparator());			
		}
		
		// 'Get' doesn't expect any additional data
		nextCommand = Command.NONE;
		
		return formattedMessages.toString();
	}
	
	private String Create(String stringCmd) {
		var msg = "";
		
		if (nextCommand.equals(Command.NONE)) {
			// Wait for another create call.
			nextCommand = Command.CREATE;
			msg = createMessageStp1;
		} else {
			// Wait for a new command
			nextCommand = Command.NONE;
			var text = clientPrefix + clientNumber + " " + stringCmd;
			var msgObj = new Message(text);
			runtimeMessages.add(msgObj);
			msg = createMessageStp2;
		}

		return msg;
	}
	
	private static String Unknown() {
		return unknownMessage;
	}
	
	private static String Help() {
		return helpMessage;
	}

	private static Command GetCommand(String stringCmd) {
		Command command = Command.NONE;
		for (Command cmd : Command.values()) { 
            if (stringCmd.toUpperCase().equals(cmd.toString()))
            	command = cmd;
        }
		return command;
	}
}
