package kostiv.testingdb.data.exchange;

import java.util.ArrayList;
import java.util.List;

import kostiv.testingdb.data.AnswerCase;
import kostiv.testingdb.data.Question;

public class QuestionDTO {
	public Long id;
	public String title;
	public String text;
	public Integer type;
	public Integer mark;
	public String correctResultQuery;
	public List<AnswerCaseDTO> answers;
	public String schema;

	public String code;
	
	public QuestionDTO() {
		super();
	}
	
	public QuestionDTO(Question question) {
		this();
		id = question.getId();
		title = question.getTitle();
		text = question.getText();
		type = question.getType();
		mark = question.getMark();
		correctResultQuery = question.getCorrectResultQuery();
		answers = new ArrayList<>();
		for (AnswerCase answer : question.getAnswers()) {
			answers.add(new AnswerCaseDTO(answer));
		}
		schema = question.getSchema();
		code = question.getCode();
	}
	
	public Question toEntity() {
		Question result = new Question();
		
		result.setId(id);
		result.setTitle(title);
		result.setText(text);
		result.setType(type);
		result.setMark(mark);
		result.setCorrectResultQuery(correctResultQuery);
		result.setSchema(schema);
		result.setAnswers(new ArrayList<AnswerCase>());
		for (AnswerCaseDTO answer : answers) {
			result.getAnswers().add(answer.toEntity());
		}
		result.setCode(code);
		return result;
	}
}
