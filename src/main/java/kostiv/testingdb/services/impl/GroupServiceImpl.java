package kostiv.testingdb.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kostiv.testingdb.dao.GroupDao;
import kostiv.testingdb.data.Group;
import kostiv.testingdb.data.exchange.GroupDTO;
import kostiv.testingdb.services.GroupService;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;
	
	@Override
	public List<GroupDTO> getGroupsForExport() {
		List<GroupDTO> result = new ArrayList<>();
		List<Group> groups = groupDao.findAll();
		for (Group group : groups) {
			result.add(new GroupDTO(group));
		}
		return result;
	}

	@Override
	public String importGroups(List<GroupDTO> groups) {
		// TODO Auto-generated method stub
		return null;
	}

}
