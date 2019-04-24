package kostiv.testingdb.data.exchange;

import java.util.ArrayList;
import java.util.List;

import kostiv.testingdb.data.Group;
import kostiv.testingdb.data.User;

public class GroupDTO {
	public Long id;
	public String name;
	public List<UserDTO> users;
	
	public GroupDTO() {
		super();
	}
	
	public GroupDTO(Group group) {
		this();
		id = group.getId();
		name = group.getName();
		users = new ArrayList<>();
		for (User user : group.getUsers()) {
			users.add(new UserDTO(user));
		}
	}
	
	public Group toEntity() {
		Group result = new Group();
		result.setId(id);
		result.setName(name);
		result.setUsers(new ArrayList<User>());
		for (UserDTO user : users) {
			result.getUsers().add(user.toEntity());
		}
		return result;
	}
}
