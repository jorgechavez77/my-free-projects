package chat.control;

import chat.domain.Chatter;

public class ChatRoom {

	public ChatRoom(Chatter client, Chatter helper) {
		this.client = client;
		this.helper = helper;
		this.client.setChatRoom(this);
		this.helper.setChatRoom(this);
	}

	private Chatter client;
	private Chatter helper;

	public Chatter getClient() {
		return client;
	}

	public void setClient(Chatter client) {
		this.client = client;
	}

	public Chatter getHelper() {
		return helper;
	}

	public void setHelper(Chatter helper) {
		this.helper = helper;
	}

}
