package kostiv.testingdb.rest;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kostiv.testingdb.data.AnswerResult;
import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.TestResult;
import kostiv.testingdb.data.User;
import kostiv.testingdb.services.TestingService;
import kostiv.testingdb.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class QuestionsController extends BasicController {

	@Autowired
	private UserService userService;

	@Autowired
	private TestingService testingService;

	@PostMapping(value = "/login")
	public ResponseEntity<User> performLogin(@RequestParam String ticketId, 
			HttpServletRequest request) {
		
		String hostName = extractHostname(request);
		String userAgent = extractUserAgent(request);
		User user = userService.logonUser(ticketId, hostName, userAgent);
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = "/start")
	public ResponseEntity<Long> startTest(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken, 
			HttpServletRequest request) {
		
		String hostName = extractHostname(request);
		Long questId = testingService.startTesting(authToken, hostName);
		return ResponseEntity.ok(questId);
	}

	@PostMapping(value = "/answer")
	public ResponseEntity<AnswerResult> setAnswer(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken,
			@RequestParam Long questionId,
			@RequestParam(required = false) Long answerId,
			@RequestParam(required = false) List<Long> answers,
			@RequestParam(required = false) String answerText,
			@RequestParam Long timeTaken, 
			HttpServletRequest request) {
		
		String hostName = extractHostname(request);
		String userAgent = extractUserAgent(request);
		AnswerResult result;
		if (answers != null) {
			result = testingService.setAnswer(authToken, questionId, answers,
					timeTaken, hostName, userAgent);
		} else if (answerText != null) {
			result = testingService.setAnswer(authToken, questionId,
					answerText, timeTaken, hostName, userAgent);
		} else {
			result = testingService.setAnswer(authToken, questionId, answerId,
					timeTaken, hostName, userAgent);
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping(value = "/next")
	public ResponseEntity<Question> getNextQuestion(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken, 
			HttpServletRequest request) {
		
		String hostName = extractHostname(request);
		Question question = testingService.getNextQuestion(authToken, hostName);
		if (question != null) {
			if (question.getAnswers() != null) {
				Collections.shuffle(question.getAnswers());
			}
			return new ResponseEntity<Question>(question, HttpStatus.OK);
		} else {
			return new ResponseEntity<Question>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/finish")
	public ResponseEntity<TestResult> finishTest(
			@RequestHeader(AUTH_TOKEN_HEADER_NAME) String authToken, 
			HttpServletRequest request) {
		
		String hostName = extractHostname(request);
		return ResponseEntity.ok(
				testingService.endTest(authToken, hostName));
	}
	
	@GetMapping("/time")
	public ResponseEntity<Long> getTime() {
		return ResponseEntity.ok(System.currentTimeMillis());
	}
}
