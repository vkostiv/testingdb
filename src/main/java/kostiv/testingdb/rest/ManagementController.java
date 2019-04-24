package kostiv.testingdb.rest;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kostiv.testingdb.data.AnswerCase;
import kostiv.testingdb.data.Group;
import kostiv.testingdb.data.Progress;
import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.TestResult;
import kostiv.testingdb.data.Theme;
import kostiv.testingdb.data.User;
import kostiv.testingdb.data.exchange.ExamDTO;
import kostiv.testingdb.data.exchange.GroupDTO;
import kostiv.testingdb.data.exchange.ThemeDTO;
import kostiv.testingdb.services.ExamService;
import kostiv.testingdb.services.GroupService;
import kostiv.testingdb.services.QuestionService;
import kostiv.testingdb.services.ResultService;
import kostiv.testingdb.services.TestingService;
import kostiv.testingdb.services.ThemeService;
import kostiv.testingdb.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manage")
public class ManagementController extends BasicController {

	@Autowired
	private UserService userService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ResultService resultService;

	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private ExamService examService;
	
	@Autowired
	private GroupService groupService;

	@Autowired
	private TestingService testingService;

	@RequestMapping(value = "/login", method = POST)
	public ResponseEntity<String> logonUser(String username, String password, 
			HttpServletRequest request) {
		
		String hostName = extractHostname(request);
		String userAgent = extractUserAgent(request);
		String token = userService.logonUser(username, password, hostName, userAgent);
		return new ResponseEntity<String>(token, HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = GET)
	public ResponseEntity<List<User>> getUsers(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam(required = false) Long id) {

		userService.checkAuthToken(authToken);
		List<User> users = userService.getUsers(id);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = PUT)
	public ResponseEntity<User> updateUser(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestBody User newUser) {

		userService.checkAuthToken(authToken);
		User user = userService.update(newUser);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user", method = POST)
	public ResponseEntity<User> createUser(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam String name,
			@RequestParam(required = false) String password,
			@RequestParam String ticketId,
			@RequestParam Integer mark) {

		userService.checkAuthToken(authToken);
		User user = userService.create(name, password, ticketId, mark);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user", method = DELETE)
	public ResponseEntity<Boolean> deleteUser(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam Long id) {

		userService.checkAuthToken(authToken);
		boolean result = userService.delete(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.CREATED);
	}

	// ================= Questions ==========================
	@RequestMapping(value = "/quest", method = GET)
	public ResponseEntity<List<Question>> getQuestions(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam(required = false) Long id) {

		userService.checkAuthToken(authToken);
		List<Question> result = questionService.findQuestions(id);
		return new ResponseEntity<List<Question>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/theme/{themeId}/quest", method = GET)
	public ResponseEntity<List<Question>> getQuestionsByTheme(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@PathVariable Long themeId) {

		userService.checkAuthToken(authToken);
		List<Question> result = questionService.findByQuestionsTheme(themeId);
		return new ResponseEntity<List<Question>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/quest", method = POST)
	public ResponseEntity<Question> createQuestion(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam String title, @RequestParam String text,
			@RequestParam Long themeId, @RequestParam Integer type,
			@RequestParam Integer mark, @RequestParam(required=false) String checkQuery,
			@RequestParam String schema) {

		userService.checkAuthToken(authToken);
		Question result = questionService.createQuestion(title, text, themeId,
				type, mark, checkQuery, schema);
		return new ResponseEntity<Question>(result, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/quest", method = PUT)
	public ResponseEntity<Question> updateQuestion(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam Long id, @RequestParam String title,
			@RequestParam String text, @RequestParam Long themeId,
			@RequestParam Integer type, @RequestParam Integer mark,
			@RequestParam(required = false) String checkQuery) {

		userService.checkAuthToken(authToken);
		Question result = questionService.updateQuestion(id, title, text,
				themeId, type, mark, checkQuery);
		return new ResponseEntity<Question>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/quest", method = DELETE)
	public ResponseEntity<Boolean> deleteQuestion(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam Long id) {

		userService.checkAuthToken(authToken);
		boolean result = questionService.deleteQuestion(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	// ================= Answers ==========================
	@RequestMapping(value = "/answer", method = GET)
	public ResponseEntity<List<AnswerCase>> getAnswerCases(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam(required=false) Long id) {

		userService.checkAuthToken(authToken);
		List<AnswerCase> result = questionService.findAnswerCases(id);
		return new ResponseEntity<List<AnswerCase>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/answer", method = POST)
	public ResponseEntity<AnswerCase> createAnswerCase(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam Long questionId, @RequestParam String text,
			@RequestParam Integer correct) {

		userService.checkAuthToken(authToken);
		AnswerCase result = questionService.createAnswerCase(questionId, text,
				correct);
		return new ResponseEntity<AnswerCase>(result, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/answer/{id}", method = PUT)
	public ResponseEntity<AnswerCase> updateAnswerCase(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@PathVariable("id") Long id, @RequestParam Long questionId,
			@RequestParam String text, @RequestParam Integer correct) {

		userService.checkAuthToken(authToken);
		AnswerCase result = questionService.updateAnswerCase(id, questionId,
				text, correct);
		return new ResponseEntity<AnswerCase>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/answer/{id}", method = DELETE)
	public ResponseEntity<Boolean> deleteAnswerCase(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@PathVariable("id") Long id) {

		userService.checkAuthToken(authToken);
		boolean result = questionService.deleteAnswerCase(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}

	// ================= ControlPanel ============================
	public ResponseEntity<List<TestResult>> getResults(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken) {

		userService.checkAuthToken(authToken);
		List<TestResult> results = resultService.findAll();
		return new ResponseEntity<List<TestResult>>(results, HttpStatus.OK);
	}

	// ================= Theme =================================
	@RequestMapping(value = "/theme", method = GET)
	public ResponseEntity<List<Theme>> geteThemes(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam(required = false) Long id) {

		userService.checkAuthToken(authToken);
		List<Theme> result = themeService.getThemes(id);
		return new ResponseEntity<List<Theme>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/theme", method = POST)
	public ResponseEntity<Theme> createTheme(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam String name,
			@RequestParam(required = false) String description) {

		userService.checkAuthToken(authToken);
		Theme result = themeService.createTheme(name, description);
		return new ResponseEntity<Theme>(result, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/theme", method = PUT)
	public ResponseEntity<Theme> updateTheme(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam Long id, @RequestParam(required = false) String name,
			@RequestParam(required = false) String description) {

		userService.checkAuthToken(authToken);
		Theme result = themeService.updateTheme(id, name, description);
		return new ResponseEntity<Theme>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/theme", method = DELETE)
	public ResponseEntity<Boolean> deleteTheme(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam Long id) {

		userService.checkAuthToken(authToken);
		boolean result = themeService.deleteTheme(id);
		return new ResponseEntity<Boolean>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/progress", method = GET)
	public ResponseEntity<List<Progress>> getProgress() {
		List<Progress> result = resultService.getCurrentProgress();
		return new ResponseEntity<List<Progress>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/groups", method = GET)
	public ResponseEntity<List<Group>> getGroups() {
		List<Group> result = resultService.getAllGroups();
		return new ResponseEntity<List<Group>>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/export/exam", method = GET)
	public ResponseEntity<List<ExamDTO>> exportExams() {
		List<ExamDTO> exams = examService.getExamsForExport();
		return new ResponseEntity<List<ExamDTO>>(exams, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/export/theme", method = GET)
	public ResponseEntity<List<ThemeDTO>> exportThemes() {
		List<ThemeDTO> exams = themeService.getThemesForExport();
		return new ResponseEntity<List<ThemeDTO>>(exams, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/export/group", method = GET)
	public ResponseEntity<List<GroupDTO>> exportGroups() {
		List<GroupDTO> groups = groupService.getGroupsForExport();
		return new ResponseEntity<List<GroupDTO>>(groups, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/import/exam", method = POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> importExams(@RequestBody List<ExamDTO> exams) {
		String result = examService.importExams(exams);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/import/theme", method = POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> exportThemes(List<ThemeDTO> themes) {
		String result = themeService.importThemes(themes);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/import/group", method = POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> importGroups(List<GroupDTO> groups) {
		String result = groupService.importGroups(groups);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(value="/generate/{count}", method=GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List> generateOffline(@PathVariable("count") Integer count) {
		return new ResponseEntity<List>(testingService.generateOffline(count), HttpStatus.OK);
	}
}
