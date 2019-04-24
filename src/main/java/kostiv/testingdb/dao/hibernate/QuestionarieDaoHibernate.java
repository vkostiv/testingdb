package kostiv.testingdb.dao.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import kostiv.testingdb.dao.QuestionarieDao;
import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.Questionarie;
import kostiv.testingdb.data.User;

@Repository("questionarieDao")
public class QuestionarieDaoHibernate extends AbstractBaseDao<Questionarie> implements QuestionarieDao{

	@Override
	public List<Questionarie> findAll() {
		TypedQuery<Questionarie> query = entityManager.createQuery("select q from Questionarie q", Questionarie.class);
		return query.getResultList();
	}

	@Override
	public Questionarie findById(Long id) {
		return entityManager.find(Questionarie.class, id);
	}

	@Override
	public Questionarie findByUser(User user) {
		TypedQuery<Questionarie> query = entityManager.createQuery("select q from Questionarie q WHERE q.answeredBy.id = :id", Questionarie.class);
		query.setParameter("id", user.getId());
		Questionarie result;
		try {
			result = query.getSingleResult();
		} catch(NonUniqueResultException e) {
			result = query.getResultList().get(0);
		} catch(NoResultException e) {
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Question> findAssignedQuestions(Long themeId) {
		Query query = entityManager.createNativeQuery("select q.* from questions q, questionary_question qq, questionaries r "
				+ "where q.theme_id = :themeid AND q.question_id = qq.question_id AND qq.questionary_id = r.id AND r.answered_at <= '01-01-1990'", Question.class);
		query.setParameter("themeid", themeId);
		try {
			return new HashSet<Question>(query.getResultList());
		} catch(EntityNotFoundException e) {
			return new HashSet<Question>();
		}
	}

	@Override
	public void assignQuestion(Long themeId, Long questionId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Questionarie> findAllActive() {
		TypedQuery<Questionarie> query = entityManager.createQuery("select q from Questionarie q where q.when = :startDate order by q.user.name", Questionarie.class);
		query.setParameter("startDate", new Date(0L));
		return query.getResultList();
	}
}
