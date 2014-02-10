package chat.control;

public class MessageObject {

	private String id;
	private String message;

	public MessageObject(String user, String message) {
		this.id = user;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
