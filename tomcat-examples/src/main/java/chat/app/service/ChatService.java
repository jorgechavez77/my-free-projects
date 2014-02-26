package chat.app.service;

import chat.app.domain.ChatIncident;
import chat.app.domain.Chatter;

public interface ChatService {

	Chatter findUser(String user);

	String getClientType(String id);

	void saveChatIncident(ChatIncident chatIncident);

	ChatIncident findChatIncidentByReporter(String reporter);
	
}
