package kostiv.testingdb.dao;

import kostiv.testingdb.data.Exam;
import kostiv.testingdb.data.Group;

public interface ExamDao extends BaseDao<Exam> {

	Exam findById(Long id);
	
	Exam findByGroup(Group group);

	Exam findByCode(String code);
	
}
