package chat.app.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import chat.app.domain.BaseEntity;
import chat.app.domain.ChatIncident;
import chat.app.domain.User;

@Repository("chatRepository")
public class ChatRepositoryImpl implements ChatRepository {

	@PersistenceContext(unitName = "simple-jpa")
	private EntityManager entityManager;

	@Override
	public void save(BaseEntity entity) {
		entityManager.persist(entity);
	}

	@Override
	public ChatIncident findChatIncidentById(Serializable id) {
		return entityManager.find(ChatIncident.class, id);
	}

	@Override
	public User findUser(String screenName) {
		// TODO Auto-generated method stub
		return null;
	}

}
