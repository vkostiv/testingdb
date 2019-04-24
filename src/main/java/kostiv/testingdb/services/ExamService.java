package kostiv.testingdb.services;

import java.util.List;

import kostiv.testingdb.data.exchange.ExamDTO;

public interface ExamService {

	List<ExamDTO> getExamsForExport();

	String importExams(List<ExamDTO> exams);

}
