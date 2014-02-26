package chat.app.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
		return null;
	}

	@Override
	public ChatIncident findChatIncidentByReporter(String reporter) {
		String qlString = "SELECT c FROM ChatIncident c WHERE c.reportedBy = :reportedBy ORDER BY c.id DESC";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("reportedBy", reporter);
		return (ChatIncident) query.getResultList().get(0);
	}

}
