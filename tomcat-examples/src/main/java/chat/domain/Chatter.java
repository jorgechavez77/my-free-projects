package chat.domain;

import java.io.Serializable;

import javax.servlet.http.HttpServletResponse;

import chat.control.ChatRoom;

public class Chatter implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CUSTOMER = "C";
	public static final String HELP_DESK = "H";

	private String id;
	private HttpServletResponse response;
	private ChatRoom chatRoom;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
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
