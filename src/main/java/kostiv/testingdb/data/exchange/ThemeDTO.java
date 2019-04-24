package kostiv.testingdb.data.exchange;

import java.util.ArrayList;
import java.util.List;

import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.Theme;

public class ThemeDTO {
	public Long id;
	public String name;
	public String description;
	public List<QuestionDTO> questions;
	
	public String code;
	
	public ThemeDTO() {
		super();
	}
	
	public ThemeDTO(Theme theme) {
		this();
		id = theme.getId();
		name = theme.getName();
		description = theme.getDescription();
		questions = new ArrayList<>();
		for (Question question : theme.getQuestions()) {
			questions.add(new QuestionDTO(question));
		}
		code = theme.getCode();
	}
	
	public Theme toEntity() {
		Theme result = new Theme();
		result.setId(id);
		result.setName(name);
		result.setDescription(description);
		result.setQuestions(new ArrayList<Question>());
		for (QuestionDTO question : questions) {
			result.getQuestions().add(question.toEntity());
		}
		result.setCode(code);
		return result;
	}

}
