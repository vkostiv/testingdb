package kostiv.testingdb.dao;

import java.util.List;
import java.util.Set;

import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.Questionarie;
import kostiv.testingdb.data.User;

public interface QuestionarieDao extends BaseDao<Questionarie> {

	public Questionarie findByUser(User user);

	public Set<Question> findAssignedQuestions(Long id);

	public void assignQuestion(Long id, Long id2);

	public List<Questionarie> findAllActive();

}
