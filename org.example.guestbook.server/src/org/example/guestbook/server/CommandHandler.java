package org.example.guestbook.server;

import java.util.ArrayList;

public class CommandHandler {

	private boolean stop = false;
	private Command nextCommand = Command.NONE; 
	
	private static String helpMessage = Messages.getString("CommandHandler.0"); //$NON-NLS-1$
	private static String unknownMessage = Messages.getString("CommandHandler.1"); //$NON-NLS-1$
	private static String noMessages = Messages.getString("CommandHandler.2"); //$NON-NLS-1$
	private static String createMessageStp1 = Messages.getString("CommandHandler.3"); //$NON-NLS-1$
	private static String createMessageStp2 = Messages.getString("CommandHandler.4"); //$NON-NLS-1$
	private static String createMessagePrefix = Messages.getString("CommandHandler.5"); //$NON-NLS-1$
	private static String exitMessage = Messages.getString("CommandHandler.6"); //$NON-NLS-1$
	
	private static ArrayList<Message> runtimeMessages = 
			new ArrayList<Message>();
	
	private enum Command {
		EXIT,
		GET,
		CREATE,
		HELP,
		NONE
	}
	
	public CommandHandler() {
		// TODO Auto-generated constructor stub
	}

	public String Process(String stringCmd) {
		var command = (nextCommand.equals(Command.NONE) ?
						GetCommand(stringCmd) :
					   nextCommand);
		switch(command) {
			case GET:	 return Get();
			case HELP:	 return Help();
			case CREATE: return Create(stringCmd);
			case EXIT:   return Exit();
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
		var msg = ""; //$NON-NLS-1$
		
		if (nextCommand.equals(Command.NONE)) {
			// Wait for another create call.
			nextCommand = Command.CREATE;
			msg = createMessageStp1;
		} else {
			// Wait for a new command
			nextCommand = Command.NONE;
			var msgObj = new Message(stringCmd);
			runtimeMessages.add(msgObj);
			msg = createMessageStp2;
		}

		return msg;
	}
	
	private String Exit() {
		stop = true;
		
		return exitMessage;
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

	public boolean isStopped() {
		return stop;
	}
}
