package kostiv.testingdb.dao;

import java.util.List;

import kostiv.testingdb.data.Question;

public interface QuestionDao extends BaseDao<Question> {

	public List<Question> findByTheme(Long themeId);

	public List<Question> getQuestions(Long id);
}
