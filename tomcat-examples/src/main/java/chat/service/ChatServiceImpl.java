package chat.service;

public class ChatServiceImpl implements ChatService {

	public boolean isHelpDesk(String id) {
		return id.equalsIgnoreCase("helpDesk");
	}

}
