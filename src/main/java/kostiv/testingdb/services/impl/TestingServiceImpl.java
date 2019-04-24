package kostiv.testingdb.services.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kostiv.testingdb.config.AppSpringConfig;
import kostiv.testingdb.dao.AnswerCaseDao;
import kostiv.testingdb.dao.AnswerResultDao;
import kostiv.testingdb.dao.QuestionDao;
import kostiv.testingdb.dao.QuestionarieDao;
import kostiv.testingdb.dao.ThemeDao;
import kostiv.testingdb.dao.UserDao;
import kostiv.testingdb.data.AnswerCase;
import kostiv.testingdb.data.AnswerResult;
import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.Questionarie;
import kostiv.testingdb.data.TestResult;
import kostiv.testingdb.data.Theme;
import kostiv.testingdb.data.User;
import kostiv.testingdb.exceptions.IncorrectTokenException;
import kostiv.testingdb.exceptions.NotFoundException;
import kostiv.testingdb.services.TestingService;
import kostiv.testingdb.services.exceptions.UserNotLoggedinException;
import kostiv.testingdb.util.TokenUtil;

@Service("testingService")
@Scope(value = "prototype")
public class TestingServiceImpl implements TestingService, ApplicationContextAware {

	private static Logger LOGGER = LoggerFactory.getLogger(TestingServiceImpl.class);

	private ApplicationContext context;

	@Autowired
	private ThemeDao themeDao;

	@Autowired
	private QuestionarieDao questionarieDao;

	@Autowired
	private AnswerCaseDao answerCaseDao;

	@Autowired
	private AnswerResultDao answerResultDao;

	@Autowired
	private QuestionDao questionDao;

	@Autowired
	private UserDao userDao;

	@Transactional
	@Override
	public List<Questionarie> generateOffline(Integer count) {
		List<Questionarie> result = new ArrayList<>();
		if (count != null) {
			for (int i = 0; i < count; i++) {
				Questionarie questionarie = new Questionarie();
				questionarie.setQuestions(new ArrayList<Question>());
				List<Theme> themes = themeDao.findAll();
				Set<Long> ids = new HashSet<Long>();
				for (Theme theme : themes) {
					// List<Question> questions = theme.getQuestions();
					List<Question> questions = questionDao.getQuestions(theme.getId());
					if (questions.size() == 0) {
						continue;
					}
					int idx = TokenUtil.randomIndex(questions.size());
					Question question = questions.get(idx);
					ids.add(question.getId());
					questionarie.getQuestions().add(question);
					// For themes 149, 143, 146 and 145 two questions per theme
					// are selected
					if (theme.getId() == 143 || theme.getId() == 145 || theme.getId() == 146 || theme.getId() == 149) {
						while (ids.contains(question.getId())) {
							idx = TokenUtil.randomIndex(questions.size());
							question = questions.get(idx);
						}
						ids.add(question.getId());
						questionarie.getQuestions().add(question);
					}
				}

				questionarie.setWhen(new Date());
				result.add(questionarie);

			}
		}
		return result;
	}

	@Transactional
	@Override
	public synchronized Long startTesting(String authToken, String hostName) {
		// Generate questionarie
		User user = userDao.findByToken(authToken);
		if (user == null) {
			throw new UserNotLoggedinException();
		}
		Questionarie questionarie = questionarieDao.findByUser(user);
		if (questionarie == null) {
			questionarie = new Questionarie();
			questionarie.setQuestions(new ArrayList<Question>());
			List<Theme> themes = themeDao.findByOrder();
			Set<Long> ids = new HashSet<Long>();
			for (Theme theme : themes) {
				// List<Question> questions = theme.getQuestions();
				List<Question> questions = questionDao.getQuestions(theme.getId());
				if (questions.size() == 0) {
					continue;
				}
				int idx = TokenUtil.randomIndex(questions.size());
				Question question = questions.get(idx);
				ids.add(question.getId());
				questionarie.getQuestions().add(question);
				// For themes 149, 143, 146 and 145 two questions per theme are
				// selected
				if (theme.getId() == 143 || theme.getId() == 145 || theme.getId() == 146 || theme.getId() == 149) {
					while (ids.contains(question.getId())) {
						idx = TokenUtil.randomIndex(questions.size());
						question = questions.get(idx);
					}
					ids.add(question.getId());
					questionarie.getQuestions().add(question);
				}
				// questionarieDao.assignQuestion(theme.getId(),
				// question.getId());

			}
			questionarie.setAnsweredBy(user);
			questionarie.setWhen(new Date(0L));
			questionarieDao.save(questionarie);
		}
		return questionarie.getId();
	}

	@Transactional
	@Override
	public Question getNextQuestion(String authToken, String hostName) {
		User user = userDao.findByToken(authToken);
		if (user != null) {
			Questionarie questionarie = questionarieDao.findByUser(user);
			if (questionarie != null) {
				int current = questionarie.getCurrentQuestion();
				List<Question> questions = questionarie.getQuestions();
				Collections.sort(questions, new Comparator<Question>() {

					@Override
					public int compare(Question o1, Question o2) {
						long res = (o1.getTheme().getId() - o2.getTheme().getId());
						if (res == 0) {
							res = o1.getId() - o2.getId();
						}
						return (int) res;
					}
				});
				Question q = questions.size() > current ? questions.get(current) : null;
				current++;
				questionarie.setCurrentQuestion(current);
				questionarieDao.update(questionarie);
				return q;
			} else {
				throw new NotFoundException();
			}
		} else {
			throw new IncorrectTokenException();
		}
	}

	@Transactional
	@Override
	public AnswerResult setAnswer(String authToken, Long questionId, Long answerCaseId, long timeTaken, String hostName, String userAgent) {
		User user = userDao.findByToken(authToken);
		if (user != null) {
			AnswerResult result = new AnswerResult();
			Question question = questionDao.findById(questionId);
			Questionarie questionarie = questionarieDao.findByUser(user);
			if (answerCaseId != -1) {
				AnswerCase answer = answerCaseDao.findById(answerCaseId);
				result.setMarkObtained(answer.getCorrect());
			} else {
				result.setMarkObtained(0);
			}
			result.setQuestion(question);
			result.setTimeTaken(timeTaken);
			result.setQuestionarie(questionarie);
			result.setHost(hostName);
			result.setUserAgent(userAgent);
			result.setTime(new Date());
			answerResultDao.save(result);
			return result;
		} else {
			throw new IncorrectTokenException();
		}
	}

	@Transactional
	@Override
	public AnswerResult setAnswer(String authToken, Long questionId, List<Long> answerCaseIds, long timeTaken, String hostName, String userAgent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public AnswerResult setAnswer(String authToken, Long questionId, String answer, long timeTaken, String hostName, String userAgent) {

		User user = userDao.findByToken(authToken);
		if (user != null) {

			AnswerResult result = new AnswerResult();
			Question question = questionDao.findById(questionId);
			Questionarie questionarie = questionarieDao.findByUser(user);
			String correctQuery = question.getCorrectResultQuery();
			result.setMarkObtained(checkQuery(answer, correctQuery, question.getMark(), question.getType(),
					question.getSchema(), user));

			LOGGER.info(user.getUsername() + ", QUERY: " + answer + " :END QUERY. On questionID: " + questionId);

			result.setQuestion(question);
			result.setTimeTaken(timeTaken);
			result.setQuestionarie(questionarie);
			result.setQuery(answer);
			result.setHost(hostName);
			result.setUserAgent(userAgent);
			result.setTime(new Date());
			answerResultDao.save(result);
			return result;
		} else {
			throw new IncorrectTokenException();
		}
	}

	private Integer checkQuery(String answer, String correctQuery, Integer mark, Integer type, String schema,
			User user) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		Environment env = context.getBean(Environment.class);
		dataSource.setDriverClassName(env.getProperty(AppSpringConfig.DB_DRIVER_CLASSNAME, "org.postgresql.Driver"));
		String url = env.getProperty("database." + schema);
		dataSource.setUrl(url);
		dataSource.setUsername(env.getProperty(AppSpringConfig.DB_USERNAME));
		dataSource.setPassword(env.getProperty(AppSpringConfig.DB_PASSWORD));
		if (answer.toUpperCase().indexOf("CREATE ") > 0 || answer.toUpperCase().indexOf("DROP ") > 0
				|| answer.toUpperCase().indexOf("ALTER ") > 0) {
			return -5;
		} else {
			Connection cnn = null;
			Statement stmt = null;
			ResultSet rs = null;

			Statement checkStmt = null;
			ResultSet checkRs = null;
			try {
				cnn = dataSource.getConnection();
				cnn.setAutoCommit(false);
				stmt = cnn.createStatement();
				if (answer.indexOf(";") > 0) {
					answer = answer.substring(0, answer.length() - 1);
				}
				// checkCnn = dataSource.getConnection();
				checkStmt = cnn.createStatement();
				if (type == Question.CODE_ANSWER) {
					rs = stmt.executeQuery(answer);
					if (correctQuery.indexOf("%s") >= 0) {
						correctQuery = String.format(correctQuery, answer);
					}
					checkRs = checkStmt.executeQuery(correctQuery);
					if (checkRs.next()) {
						if (checkRs.getBoolean(1)) {
							return 5;
						} else {
							return 2;
						}
					} else {
						return 0;
					}
				} else {
					stmt.executeUpdate(answer);
					checkRs = checkStmt.executeQuery(correctQuery);
					if (checkRs.next()) {
						if (checkRs.getBoolean(1)) {
							return 5;
						} else {
							return 2;
						}
					} else {
						return 0;
					}
				}

			} catch (SQLException e) {
				LOGGER.error("QUERY DID NOT RUN! User: " + user.getTicketId() + ": " + user.getUsername());
				LOGGER.error("ERROR ON QUERY: " + answer, e);
				return 0;
			} finally {
				if (checkRs != null) {
					try {
						checkRs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (checkStmt != null) {
					try {
						checkStmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (cnn != null) {
					try {
						cnn.rollback();
						cnn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Transactional
	@Override
	public AnswerResult setTestingQuery(String authToken, Long questionId, String query, long timeTaken, String hostName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public TestResult endTest(String authToken, String hostName) {
		User user = userDao.findByToken(authToken);
		if (user != null) {
			TestResult result = new TestResult();
			Questionarie questionarie = questionarieDao.findByUser(user);
			int questionarieMark = 0;
			for (Question question : questionarie.getQuestions()) {
				questionarieMark += question.getMark();
			}
			List<AnswerResult> answers = questionarie.getAnswers();
			int totalMark = 0;
			double avgMark = 0.0;
			double avgTaskTimeDbl = 0.0;
			ArrayList<Integer> marks = new ArrayList<Integer>();
			boolean passed = false;
			long timeUsed = 0;
			for (AnswerResult answer : answers) {
				totalMark += answer.getMarkObtained();
				marks.add(answer.getMarkObtained());

				avgTaskTimeDbl += answer.getTimeTaken();
				timeUsed += answer.getTimeTaken();

			}

			avgMark = totalMark / answers.size();
			avgTaskTimeDbl = avgTaskTimeDbl / answers.size();
			long avgTaskTime = (long) Math.ceil(avgTaskTimeDbl);

			double percent = totalMark / questionarieMark;
			if (percent < 0.51) {
				// test was not passed
			} else if (percent < 0.71) {
				// D, E marks
				passed = true;
			} else if (percent < 0.9) {
				// B, C marks
				passed = true;
			} else {
				// A mark
				passed = true;
			}

			result.setAverageMark(avgMark);
			result.setAverageTimePerTask(avgTaskTime);
			result.setDateCompleted(System.currentTimeMillis());
			result.setQuestionarie(questionarie);
			result.setPassed(passed);
			result.setTimeUsed(timeUsed);
			result.setUser(user);
			result.setTotalMark(totalMark + user.getMark());
			return result;
		} else {
			throw new IncorrectTokenException();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;
	}

}
