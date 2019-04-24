package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import kostiv.testingdb.dao.UserDao;
import kostiv.testingdb.data.User;
import kostiv.testingdb.exceptions.IncorrectTokenException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoHibernate extends AbstractBaseDao<User> implements UserDao {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(UserDaoHibernate.class);

	@Override
	public User findByName(String name) {

		TypedQuery<User> query = entityManager.createQuery(
				"select u from User u where u.name = :username", User.class);
		query.setParameter("username", name);
		return query.getSingleResult();
	}

	@Override
	public User findByTicket(String ticket) {
		TypedQuery<User> query = entityManager.createQuery(
				"select u from User u where u.ticketId = :ticket", User.class);
		query.setParameter("ticket", ticket);

		return query.getSingleResult();
	}

	@Override
	public List<User> findAll() {
		TypedQuery<User> query = entityManager.createQuery(
				"select u from User u", User.class);
		return query.getResultList();
	}

	@Override
	public User findById(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User findByToken(String token) {
		TypedQuery<User> query = entityManager.createQuery(
				"select u from User u where u.token = :token", User.class);
		query.setParameter("token", token);
		try {
			User result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

}
