package kostiv.testingdb.dao.hibernate;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import kostiv.testingdb.dao.TestResultDao;
import kostiv.testingdb.data.TestResult;

@Repository("testResultDao")
public class TestResultDaoHibernate extends AbstractBaseDao<TestResult> implements TestResultDao {

	@Override
	public List<TestResult> findAll() {
		TypedQuery<TestResult> query = entityManager.createQuery("select r from TestResult", TestResult.class);
		return query.getResultList();
	}

	@Override
	public TestResult findById(Long id) {
		return entityManager.find(TestResult.class, id);
	}

	@Override
	public boolean getQueryResult(String query, String checkQuery) {
		boolean result = true;
		Query original = entityManager.createNativeQuery(query);
		Query check = entityManager.createNativeQuery(checkQuery);
		List originalResult = original.getResultList();
		List checkResult = check.getResultList();
		result = result && (originalResult.size() == checkResult.size());
		
		return result;
	}
	
	
}
