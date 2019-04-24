package kostiv.testingdb.services;

import java.util.List;

import kostiv.testingdb.data.AnswerResult;
import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.Questionarie;
import kostiv.testingdb.data.TestResult;

public interface TestingService {

	public Long startTesting(String authToken, String hostName);
	
	public Question getNextQuestion(String authToken, String hostName);
	
	public AnswerResult setAnswer (String authToken, Long questionId, Long answerCaseId, long timeTaken, String hostName, String userAgent);
	
	public AnswerResult setAnswer (String authToken, Long questionId, List<Long> answerCaseIds, long timeTaken, String hostName, String userAgent);
	
	public AnswerResult setAnswer (String authToken, Long questionId, String answer, long timeTaken, String hostName, String userAgent);
	
	public AnswerResult setTestingQuery (String authToken, Long questionId, String query, long timeTaken, String hostName);

	public TestResult endTest(String authToken, String hostName);

	public List<Questionarie> generateOffline(Integer count);
	
}
