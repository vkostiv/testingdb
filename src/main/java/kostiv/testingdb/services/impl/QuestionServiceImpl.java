package kostiv.testingdb.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kostiv.testingdb.dao.AnswerCaseDao;
import kostiv.testingdb.dao.QuestionDao;
import kostiv.testingdb.dao.ThemeDao;
import kostiv.testingdb.data.AnswerCase;
import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.Theme;
import kostiv.testingdb.services.QuestionService;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDao questionDao;
	
	@Autowired
	private AnswerCaseDao answerCaseDao;
	
	@Autowired
	private ThemeDao themeDao;

	@Transactional
	@Override
	public List<Question> findQuestions(Long id) {
		if (id == null) {
			return questionDao.findAll();
		} else {
			ArrayList<Question> result = new ArrayList<Question>();
			result.add(questionDao.findById(id));
			return result;
		}
	}
	
	@Transactional
	@Override
	public List<Question> findByQuestionsTheme(Long id) {
		ArrayList<Question> result = new ArrayList<Question>();
		result.addAll(questionDao.findByTheme(id));
		return result;
	}

	@Transactional
	@Override
	public Question createQuestion(String title, String text,
			Long themeId, Integer type, Integer mark, String checkQuery, String schema) {
		Theme theme = themeDao.findById(themeId);
		Question question = new Question();
		question.setTitle(title);
		question.setText(text);
		question.setType(type);
		question.setMark(mark);
		question.setTheme(theme);
		question.setCorrectResultQuery(checkQuery);
		question.setSchema(schema);
		questionDao.save(question);
		return question;
	}

	@Transactional
	@Override
	public Question updateQuestion(Long id, String title, String text,
			Long themeId, Integer type, Integer mark, String checkQuery) {
		
		Question question = questionDao.findById(id);
		if (question == null) {
			question = createQuestion(title, text, themeId, type, mark, checkQuery, null);
		} else {
			if (checkQuery != null) {
				question.setCorrectResultQuery(checkQuery);
			}
			if (mark != null) {
				question.setMark(mark);
			}
			if (type!= null) {
				question.setType(type);
			}
			if (themeId != null) {
				Theme theme = themeDao.findById(themeId);
				if (theme != null) {
					question.setTheme(theme);
				}
			}
			if (text != null) {
				question.setText(text);
			}
			if (title != null) {
				question.setTitle(title);
			}
			questionDao.update(question);
		}
		
		return question;
	}

	@Transactional
	@Override
	public boolean deleteQuestion(Long id) {
		Question question = questionDao.findById(id);
		if (question != null) {
			questionDao.delete(question);
			return true;
		}
		return false;
	}

	@Transactional
	@Override
	public List<AnswerCase> findAnswerCases(Long id) {
		if (id == null) {
			return answerCaseDao.findAll();
		} else {
			ArrayList<AnswerCase> result = new ArrayList<AnswerCase>();
			AnswerCase answer = answerCaseDao.findById(id);
			if (answer != null) {
				result.add(answer);
			}
			return result;
		}
	}

	@Transactional
	@Override
	public AnswerCase createAnswerCase(Long questionId, String text,
			Integer correct) {
		Question question = questionDao.findById(questionId);
		AnswerCase answer = new AnswerCase();
		answer.setText(text);
		answer.setCorrect(correct);
		answer.setQuestion(question);
		answer = answerCaseDao.save(answer);
		return answer;
	}

	@Transactional
	@Override
	public AnswerCase updateAnswerCase(Long id, Long questionId, String text,
			Integer correct) {
		AnswerCase answer = answerCaseDao.findById(id);
		if (text != null) {
			answer.setText(text);
		}
		if (correct != null) {
			answer.setCorrect(correct);
		}
		answerCaseDao.update(answer);
		if (questionId != null) {
			Question question = questionDao.findById(questionId);
			question.getAnswers().add(answer);
			questionDao.update(question);
		}
		return answer;
	}

	@Transactional
	@Override
	public boolean deleteAnswerCase(Long id) {
		AnswerCase answer = answerCaseDao.findById(id);
		answerCaseDao.delete(answer);
		return false;
	}
}
