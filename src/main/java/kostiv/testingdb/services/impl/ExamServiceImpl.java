package kostiv.testingdb.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kostiv.testingdb.dao.ExamDao;
import kostiv.testingdb.dao.GroupDao;
import kostiv.testingdb.dao.UserDao;
import kostiv.testingdb.data.Exam;
import kostiv.testingdb.data.Group;
import kostiv.testingdb.data.User;
import kostiv.testingdb.data.exchange.ExamDTO;
import kostiv.testingdb.services.ExamService;
import kostiv.testingdb.services.ThemeService;

@Service("examService")
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamDao examDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private ThemeService themeService;
	
	@Override
	public List<ExamDTO> getExamsForExport() {
		List<ExamDTO> result = new ArrayList<ExamDTO>();
		List<Exam> exams = examDao.findAll();
		for (Exam exam : exams) {
			result.add(new ExamDTO(exam));
		}
		return result;
	}

	@Transactional
	@Override
	public String importExams(List<ExamDTO> exams) {
		String result = null;
		try {
			for (ExamDTO dto : exams) {
				Exam exam = dto.toEntity();
				if (StringUtils.isNotEmpty(exam.getCode())) {
					Exam existing = examDao.findByCode(exam.getCode());
					if (existing != null) {
						exam.setId(existing.getId());
					}
				} else {
					exam.setCode(exam.getStartMoment().toString());
				}
				// Groups processing
				if (!CollectionUtils.isEmpty(exam.getGroups())) {
					updateGroups(exam.getGroups());
				}
				// Themes processing
				if (!CollectionUtils.isEmpty(exam.getThemes())) {
					themeService.importEntities(exam.getThemes());
				}
				examDao.save(exam);
			}
		} catch (Exception e) {
			result = "FAIL: "+e.getMessage();
		}
		return result;
	}

	private void updateGroups(List<Group> groups) {
		for (Group group : groups) {
			if (StringUtils.isNotEmpty(group.getCode())) {
				Group existing = groupDao.findByCode(group.getCode());
				if (existing != null) {
					// merge changes
					group.setId(existing.getId());
					updateUsers(existing, group);
				}
			}
		}
	}

	private void updateUsers(Group existing, Group group) {
		Map<String, User> existingUsers = new HashMap<>();
		for (User u:existing.getUsers()) {
			existingUsers.put(u.getTicketId(), u);
		}
		for (User user : group.getUsers()) {
			User eu = existingUsers.get(user.getTicketId());
			if (eu != null) {
				user.setId(eu.getId());
				user.setToken(eu.getToken());
			}
		}
	}

}
