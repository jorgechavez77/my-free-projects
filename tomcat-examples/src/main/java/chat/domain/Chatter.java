package chat.domain;

import java.io.Serializable;

import websocket.chat.ChatAnnotation;
import chat.control.ChatRoom;

public class Chatter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CLIENT = "C";
	public static final String HELPER = "H";

	private String id;
	private ChatRoom chatRoom;
	private String type;

	private ChatAnnotation chatSocket;

	public ChatAnnotation getChatSocket() {
		return chatSocket;
	}

	public void setChatSocket(ChatAnnotation chatSocket) {
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
