package kostiv.testingdb.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kostiv.testingdb.dao.GroupDao;
import kostiv.testingdb.dao.QuestionarieDao;
import kostiv.testingdb.dao.TestResultDao;
import kostiv.testingdb.data.AnswerResult;
import kostiv.testingdb.data.Group;
import kostiv.testingdb.data.Progress;
import kostiv.testingdb.data.Questionarie;
import kostiv.testingdb.data.TestResult;
import kostiv.testingdb.data.WrongAnswer;
import kostiv.testingdb.services.ResultService;

@Service("resultService")
public class ResultServiceImpl implements ResultService {

	@Autowired
	private TestResultDao resultDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Autowired
	private QuestionarieDao questionarieDao;
	
	/**
	 * Testing result showing
	 */
	@Transactional
	@Override
	public List<TestResult> findAll() {
		return resultDao.findAll();
	}

	/**
	 * Operational progress showing
	 */
	@Override
	@Transactional
	public List<Progress> getCurrentProgress() {
		List<Progress> result = new ArrayList<Progress>();
		List<Questionarie> current = questionarieDao.findAll();
		for (Questionarie q : current) {
			Progress p = new Progress();
			p.setUsername(q.getAnsweredBy().getUsername());
			if (q.getAnsweredBy().getGroup() != null) {
				p.setGroupname(q.getAnsweredBy().getGroup().getName());
			} else {
				p.setGroupname("nogroup");
			}
			p.setPracticMark(q.getAnsweredBy().getMark());
			p.setTicketId(q.getAnsweredBy().getTicketId());
			int totalMark = 0;
			int examMark = 0;
			String selectQuery = null;
			String insertQuery = null;
			String deleteQuery = null;
			String updateQuery = null;
			for (AnswerResult answer : q.getAnswers()) {
				examMark += answer.getMarkObtained();
				if (answer.getQuery() != null) {
					if(selectQuery == null) {
						selectQuery = answer.getQuery();
					} else if (insertQuery == null) {
						insertQuery = answer.getQuery();
					} else if (updateQuery == null) {
						updateQuery = answer.getQuery();
					} else {
						deleteQuery = answer.getQuery();
					}
				}
			}
			p.setPercent(q.getAnswers().size()*100.0/19);
			totalMark = examMark + (q.getAnsweredBy().getMark()==null?0:q.getAnsweredBy().getMark());
			p.setTotalMark(totalMark);
			p.setExamMark(examMark);
			p.setSelectQuery(selectQuery);
			p.setInsertQuery(insertQuery);
			p.setDeleteQuery(deleteQuery);
			p.setUpdateQuery(updateQuery);
			for (AnswerResult answer : q.getAnswers()) {
				if (answer.getMarkObtained() < answer.getQuestion().getMark()) {
					p.getWrongAnswers().add(new WrongAnswer(answer));
				}
			}
			result.add(p);
		}
		return result;
	}

	@Override
	public List<Group> getAllGroups() {
		return groupDao.findAll();
	}
}
