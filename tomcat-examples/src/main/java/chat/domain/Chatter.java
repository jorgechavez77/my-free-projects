package chat.domain;

import javax.servlet.http.HttpServletResponse;

import chat.control.ChatRoom;

public abstract class Chatter {

	public static final String CUSTOMER = "C";
	public static final String HELP_DESK = "H";

	private String id;
	private HttpServletResponse response;
	private ChatRoom chatRoom;

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

	abstract String getChatterType();

	@Override
	public String toString() {
		return "chatter id: " + getId();
	}

}
