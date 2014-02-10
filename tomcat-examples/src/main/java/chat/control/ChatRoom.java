package chat.control;

import java.util.ArrayList;
import java.util.List;

import chat.domain.Chatter;
import chat.log.MyLogger;

public class ChatRoom {

	private List<Chatter> chatters = new ArrayList<>();

	public boolean addChatter(Chatter chatter) {
		MyLogger.print("Adding chatter: " + chatter + " to room: " + toString());
		return this.chatters.add(chatter);
	}

	public boolean removeChatter(Chatter chatter) {
		return this.chatters.remove(chatter);
	}

	public List<Chatter> getChatters() {
		return this.chatters;
	}

}
