package chat.service;

import chat.domain.Chatter;

public interface ChatService {

	Chatter findUser(String user);

	String getClientType(String id);

}
