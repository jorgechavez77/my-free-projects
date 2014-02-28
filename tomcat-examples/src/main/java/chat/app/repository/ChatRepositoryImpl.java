package chat.app.repository;

import java.io.Serializable;
import java.util.List;

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
	public void update(BaseEntity entity) {
		String updateQuery = "UPDATE ChatIncident set assignedTo = :assignedTo WHERE id = :id";
		Query query = entityManager.createQuery(updateQuery);
		query.setParameter("assignedTo",
				((ChatIncident) entity).getAssignedTo());
		query.setParameter("id", ((ChatIncident) entity).getId());
		query.executeUpdate();
	}

	@Override
	public ChatIncident findChatIncidentById(Serializable id) {
		return entityManager.find(ChatIncident.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUser(String screenName) {
		String qlString = "SELECT u FROM User u WHERE u.screenName = :screenName";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("screenName", screenName);
		List<User> result = query.getResultList();

		User user = null;
		if (result != null && !result.isEmpty()) {
			user = (User) result.get(0);
		}
		return user;
	}

	@Override
	public ChatIncident findChatIncidentByReporter(String reporter) {
		String qlString = "SELECT c FROM ChatIncident c WHERE c.reportedBy = :reportedBy ORDER BY c.id DESC";
		Query query = entityManager.createQuery(qlString);
		query.setParameter("reportedBy", reporter);
		return (ChatIncident) query.getResultList().get(0);
	}

	@Override
	public void updateAll(BaseEntity entity) {
		entityManager.merge(entity);
	}

}
