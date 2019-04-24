package kostiv.testingdb.dao;

import kostiv.testingdb.data.User;

public interface UserDao extends BaseDao<User> {

	public User findByName(String name);

	public User findByTicket(String ticket);
	
	public User findByToken(String token);
	
}
