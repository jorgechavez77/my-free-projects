package chat.service;

import chat.domain.Chatter;

public class ChatServiceImpl implements ChatService {

	@Override
	public Chatter findUser(String user) {
		if (!(user.contains("client") || user.contains("helper"))) {
			return null;
		}

		Chatter chatter = new Chatter();
		chatter.setId(user);
		chatter.setType(getClientType(user));
		return chatter;
	}

	@Override
	public String getClientType(String id) {
		return id.contains("helper") ? Chatter.HELPER : Chatter.CLIENT;
	}

}
