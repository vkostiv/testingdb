package kostiv.testingdb.services;

import java.util.List;

import kostiv.testingdb.data.exchange.GroupDTO;

public interface GroupService {

	List<GroupDTO> getGroupsForExport();

	String importGroups(List<GroupDTO> groups);

}
