package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import kostiv.testingdb.dao.AnswerResultDao;
import kostiv.testingdb.data.AnswerResult;

@Repository("answerResultDao")
public class AnswerResultDaoHibernate extends AbstractBaseDao<AnswerResult> implements AnswerResultDao {

	@Override
	public List<AnswerResult> findAll() {
		TypedQuery<AnswerResult> query = entityManager.createQuery("select r from AnswerResult r", AnswerResult.class);
		return query.getResultList();
	}

	@Override
	public AnswerResult findById(Long id) {
		return entityManager.find(AnswerResult.class, id);
	}

}
