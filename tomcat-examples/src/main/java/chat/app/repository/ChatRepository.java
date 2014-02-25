package chat.app.repository;

import java.io.Serializable;

import chat.app.domain.BaseEntity;
import chat.app.domain.ChatIncident;
import chat.app.domain.User;

public interface ChatRepository {

	void save(BaseEntity entity);

	ChatIncident findChatIncidentById(Serializable id);

	User findUser(String screenName);

}
