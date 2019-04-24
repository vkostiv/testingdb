package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import kostiv.testingdb.dao.GroupDao;
import kostiv.testingdb.data.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("groupDao")
public class GroupDaoHibernate extends AbstractBaseDao<Group> implements GroupDao {

		public static final Logger LOGGER = LoggerFactory
				.getLogger(GroupDaoHibernate.class);

		@Override
		public Group findByName(String name) {

			TypedQuery<Group> query = entityManager.createQuery(
					"select u from Group u where u.name = :username", Group.class);
			query.setParameter("username", name);
			return query.getSingleResult();
		}

		@Override
		public List<Group> findAll() {
			TypedQuery<Group> query = entityManager.createQuery(
					"select u from Group u", Group.class);
			return query.getResultList();
		}

		@Override
		public Group findById(Long id) {
			return entityManager.find(Group.class, id);
		}

		@Override
		public Group findByCode(String code) {
			TypedQuery<Group> query = entityManager.createQuery(
					"select u from Group u where u.code = :code", Group.class);
			query.setParameter("code", code);
			try {
				return query.getSingleResult();
			} catch (Exception nre) {
				return null;
			} 
		}
}
