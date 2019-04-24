package kostiv.testingdb.services;

import java.util.List;

import kostiv.testingdb.data.Group;
import kostiv.testingdb.data.Progress;
import kostiv.testingdb.data.TestResult;

public interface ResultService {

	List<TestResult> findAll();

	List<Progress> getCurrentProgress();

	List<Group> getAllGroups();

}
