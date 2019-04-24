package kostiv.testingdb.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kostiv.testingdb.dao.UserDao;
import kostiv.testingdb.data.User;
import kostiv.testingdb.exceptions.IncorrectTokenException;
import kostiv.testingdb.services.UserService;
import kostiv.testingdb.services.exceptions.UnknownUserException;
import kostiv.testingdb.util.TokenUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Transactional
	@Override
	public User logonUser(String ticketId, String hostName, String userAgent) {
		try {
			User user = userDao.findByTicket(ticketId);
			user.setHost(hostName);
			user.setUserAgent(userAgent);
			String token = TokenUtil.generateRandomToken();
			user.setToken(token);
			user.setLastLogin(new Date());
			userDao.update(user);
			return user;
		} catch (Exception e) {
			throw new UnknownUserException();
		}
	}

	@Transactional
	@Override
	public void logUserResults(String authToken) {
		User user = userDao.findByToken(authToken);
		if (user != null) {
			// TODO: Complete here
		} else {
			throw new IncorrectTokenException();
		}

	}

	@Transactional
	@Override
	public void checkAuthToken(String authToken) {
		try {
			User user = userDao.findByToken(authToken);
			if (user == null) {
				throw new IncorrectTokenException();
			}
		} catch (NoResultException e) {
			throw new IncorrectTokenException();
		}
	}

	@Transactional
	@Override
	public String logonUser(String username, String password, String hostName, String userAgent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public List<User> getUsers(Long id) {
		List<User> users = new ArrayList<User>();
		if (id == null) {
			users = userDao.findAll();
		} else {
			users.add(userDao.findById(id));
		}
		return users;
	}

	@Transactional
	@Override
	public User update(User newUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public User create(String name, String password, String ticketId, Integer mark) {
		User user = new User();
		user.setUsername(name);
		user.setPassword(password);
		user.setTicketId(ticketId);
		user.setMark(mark);
		userDao.save(user);
		return user;
	}

	@Transactional
	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
}
