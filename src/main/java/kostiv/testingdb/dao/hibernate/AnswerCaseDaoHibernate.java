package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import kostiv.testingdb.dao.AnswerCaseDao;
import kostiv.testingdb.data.AnswerCase;

@Repository("answerCaseDao")
public class AnswerCaseDaoHibernate extends AbstractBaseDao<AnswerCase> implements AnswerCaseDao {

	@Override
	public List<AnswerCase> findAll() {
		TypedQuery<AnswerCase> query = entityManager.createQuery("select c from AnswerCase c", AnswerCase.class);
		return query.getResultList();
	}

	@Override
	public AnswerCase findById(Long id) {
		return entityManager.find(AnswerCase.class, id);
	}

}
