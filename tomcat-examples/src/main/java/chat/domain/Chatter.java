package chat.domain;

import websocket.chat.ChatWebSocket;
import chat.control.ChatRoom;

public class Chatter {

	public static final String CLIENT = "C";
	public static final String HELPER = "H";

	private String id;
	private ChatRoom chatRoom;
	private String type;

	private ChatWebSocket chatSocket;

	public ChatWebSocket getChatSocket() {
		return chatSocket;
	}

	public void setChatSocket(ChatWebSocket chatSocket) {
		this.chatSocket = chatSocket;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}

	public ChatRoom getChatRoom() {
		return this.chatRoom;
	}

	@Override
	public String toString() {
		return "chatter id: " + getId();
	}

}
