package kostiv.testingdb.rest;

import kostiv.testingdb.exceptions.IncorrectTokenException;
import kostiv.testingdb.exceptions.NotAcceptableException;
import kostiv.testingdb.exceptions.NotFoundException;
import kostiv.testingdb.exceptions.NotLoggedinUserException;
import kostiv.testingdb.exceptions.QuestionExpiredException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BasicController {

	protected static final String AUTH_TOKEN_HEADER_NAME = "X-Testing-Web-Token";

	public BasicController() {
		super();
	}

	@ExceptionHandler(IncorrectTokenException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Wrong authentication token")
	public void handleIncorrectToken() {
		
	}

	@ExceptionHandler(NotLoggedinUserException.class)
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Wrong ticketId")
	public void handleNotLoggedin() {
		
	}

	@ExceptionHandler(QuestionExpiredException.class)
	@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT, reason = "Time is out")
	public void handleResetRequestExpired() {
		
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Unknown entity")
	public void handleNotFound() {
		
	}

	@ExceptionHandler(NotAcceptableException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "User is locked")
	public void handleNotAcceptable() {
		
	}

	protected String extractHostname(HttpServletRequest request) {
		String result = "UNKNOWN";
		if (request != null) {
			if (request.getRemoteHost() != null) {
				result = request.getRemoteHost();
			} else if (request.getRemoteAddr() != null) {
				result = request.getRemoteAddr();
			}
		}
		return result;
	}
	
	protected String extractUserAgent(HttpServletRequest request) {
		String result = "";
		if (request != null && request.getHeader("User-Agent") != null) {
				result = request.getHeader("User-Agent");
		}
		return result;
	}
}