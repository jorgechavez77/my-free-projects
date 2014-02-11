package chat.service;

import chat.domain.Chatter;

public class ChatServiceImpl implements ChatService {

	@Override
	public Chatter findUser(String user) {
		Chatter chatter = new Chatter();
		chatter.setId(user);
		chatter.setType(getClientType(user));
		return chatter;
	}

	@Override
	public String getClientType(String id) {
		return id.equalsIgnoreCase("helpDesk") ? Chatter.HELPER
				: Chatter.CLIENT;
	}

}
