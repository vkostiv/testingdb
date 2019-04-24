package kostiv.testingdb.services;

import java.util.List;

import kostiv.testingdb.data.AnswerCase;
import kostiv.testingdb.data.Question;

public interface QuestionService {

	List<Question> findQuestions(Long id);

	Question createQuestion(String title, String text, Long themeId,
			Integer type, Integer mark, String checkQuery, String schema);

	Question updateQuestion(Long id, String title, String text,
			Long themeId, Integer type, Integer mark, String checkQuery);

	boolean deleteQuestion(Long id);

	List<AnswerCase> findAnswerCases(Long id);

	AnswerCase createAnswerCase(Long questionId, String text, Integer correct);

	AnswerCase updateAnswerCase(Long id, Long questionId, String text,
			Integer correct);

	boolean deleteAnswerCase(Long id);

	List<Question> findByQuestionsTheme(Long id);

}
