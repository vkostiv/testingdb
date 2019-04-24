package kostiv.testingdb.data.exchange;

import kostiv.testingdb.data.AnswerCase;

public class AnswerCaseDTO {
	public Long id;
	public String text;
	public int correct;
	
	public String code;

	public AnswerCaseDTO() {
		super();
	}
	
	public AnswerCaseDTO(AnswerCase answerCase) {
		this();
		id = answerCase.getId();
		text = answerCase.getText();
		correct = answerCase.getCorrect();
		code = answerCase.getCode();
	}
	
	public AnswerCase toEntity() {
		AnswerCase result = new AnswerCase();
		result.setId(id);
		result.setCorrect(correct);
		result.setText(text);
		result.setCode(code);
		return result;
	}
}
