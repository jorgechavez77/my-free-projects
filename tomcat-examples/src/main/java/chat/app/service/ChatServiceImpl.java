package chat.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import chat.app.domain.ChatIncident;
import chat.app.domain.ChatIncidentDetail;
import chat.app.domain.Chatter;
import chat.app.repository.ChatRepository;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

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

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveChatIncident(ChatIncident chatIncident) {
		chatRepository.save(chatIncident);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ChatIncident findChatIncidentByReporter(String reporter) {
		return chatRepository.findChatIncidentByReporter(reporter);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveChatIncidentDetail(ChatIncidentDetail chatIncidentDetail) {
		this.chatRepository.save(chatIncidentDetail);
	}

}
