package chat.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import chat.app.domain.ChatIncident;
import chat.app.domain.ChatIncidentDetail;
import chat.app.domain.Chatter;
import chat.app.domain.User;
import chat.app.repository.ChatRepository;

@Service("chatService")
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatRepository chatRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Chatter findUser(String screenName) {
		User user = chatRepository.findUser(screenName);

		if (user == null) {
			return null;
		}

		String roleName = user.getRoles().get(0).getName();

		Chatter chatter = new Chatter();
		chatter.setId(user.getScreenName());
		chatter.setType(getClientType(roleName));
		return chatter;
	}

	@Override
	public String getClientType(String id) {
		return id.contains("SAT") ? Chatter.HELPER : Chatter.CLIENT;
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

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ChatIncident chatIncident) {
		chatRepository.update(chatIncident);
	}

}
