package kostiv.testingdb.services;

import java.util.List;

import kostiv.testingdb.data.User;

public interface UserService {

	User logonUser(String ticketId, String hostName, String userAgent);
	
	void logUserResults(String authToken);
	
	void checkAuthToken(String authToken);

	String logonUser(String username, String password, String hostName, String userAgent);

	List<User> getUsers(Long id);

	User update(User newUser);

	User create(String name, String password, String ticketId, Integer mark);

	boolean delete(Long id);
	
}
