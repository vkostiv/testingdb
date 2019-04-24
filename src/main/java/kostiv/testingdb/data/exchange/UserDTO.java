package kostiv.testingdb.data.exchange;

import java.util.Date;

import kostiv.testingdb.data.User;

public class UserDTO {
	public Long id;
	public String username;
	public String password;
	public Long lastLogin;
	public String ticketId;
	public String token;
	public Integer mark;
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(User user) {
		this();
		id = user.getId();
		username = user.getUsername();
		password = user.getPassword();
		lastLogin = user.getLastLogin() == null?null:user.getLastLogin().getTime();
		ticketId = user.getTicketId();
		token = user.getToken();
		mark = user.getMark();
	}
	
	public User toEntity() {
		User result = new User();
		result.setId(id);
		result.setUsername(username);
		result.setPassword(password);
		if (lastLogin != null) {
			result.setLastLogin(new Date(lastLogin));
		}
		result.setTicketId(ticketId);
		result.setToken(token);
		return result;
	}
}
