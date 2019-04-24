package kostiv.testingdb.dao;

import kostiv.testingdb.data.TestResult;

public interface TestResultDao extends BaseDao<TestResult> {

	public boolean getQueryResult(String query, String checkQuery);
	
}
