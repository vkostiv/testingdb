package kostiv.testingdb.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="ANSWER_CASES")
@JsonInclude(Include.NON_NULL)
public class AnswerCase {

	@Id
	@GeneratedValue
	@Column(name="CASE_ID")
	private Long id;
	
	@Column(name="CASE_TEXT")
	private String text;
	
	@Column(name="CASE_CORRECT")
	@JsonIgnore
	private int correct;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID")
	@JsonIgnore
	private Question question;
	
	@Column(name="CODE")
	private String code;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int c) {
		correct = c;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question c) {
		question = c;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
