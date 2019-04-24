package kostiv.testingdb.dao;

import kostiv.testingdb.data.Group;

public interface GroupDao extends BaseDao<Group> {

	Group findByName(String name);
	
	Group findByCode(String code);

	Group findById(Long id);

}
