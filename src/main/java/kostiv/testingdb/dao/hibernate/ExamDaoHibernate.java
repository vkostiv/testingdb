package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import kostiv.testingdb.dao.ExamDao;
import kostiv.testingdb.data.Exam;
import kostiv.testingdb.data.Group;

@Repository("examDao")
public class ExamDaoHibernate extends AbstractBaseDao<Exam> implements ExamDao {

	@Override
	public List<Exam> findAll() {
		TypedQuery<Exam> query = entityManager.createQuery(
				"select u from Exam u", Exam.class);
		return query.getResultList();
	}

	@Override
	public Exam findById(Long id) {
		return entityManager.find(Exam.class, id);
	}

	@Override
	public Exam findByGroup(Group group) {
		TypedQuery<Exam> query = entityManager.createQuery(
				"select e from Exam e where :group in (e.groups)", Exam.class);
		query.setParameter("group", group);
		return query.getSingleResult();
	}

	@Override
	public Exam findByCode(String code) {
		try {
			TypedQuery<Exam> query = entityManager.createQuery(
					"select e from Exam e where e.code = :code", Exam.class);
			query.setParameter("code", code);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
