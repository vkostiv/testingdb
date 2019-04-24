package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import kostiv.testingdb.dao.QuestionDao;
import kostiv.testingdb.data.Question;

import org.springframework.stereotype.Repository;

@Repository("questionDao")
public class QuestionDaoHibernate extends AbstractBaseDao<Question> implements QuestionDao {

	@Override
	public List<Question> findByTheme(Long themeId) {
		TypedQuery<Question> query = entityManager.createQuery("select q from Question q WHERE q.theme.id = :id", Question.class);
		query.setParameter("id", themeId);
		return query.getResultList();
	}

	@Override
	public List<Question> findAll() {
		TypedQuery<Question> query = entityManager.createQuery("select q from Question q", Question.class);
		return query.getResultList();
	}

	@Override
	public Question findById(Long id) {
		return entityManager.find(Question.class, id);
	}

	@Override
	public List<Question> getQuestions(Long id) {
		Query query = entityManager.createNativeQuery("select * from questions where theme_id = :theme_id", Question.class);
		query.setParameter("theme_id", id);
		@SuppressWarnings("unchecked")
		List<Question> result = query.getResultList();
		return result;
	}

}
