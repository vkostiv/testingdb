package kostiv.testingdb.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="QUESTIONARIES")
@JsonInclude(Include.NON_NULL)
public class Questionarie {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="ANSWERED_AT")
	private Date when;
	
	@ManyToMany
	@JoinTable(name="questionary_question", joinColumns={@JoinColumn(name="questionary_id")}, inverseJoinColumns={@JoinColumn(name="question_id")})
	private List<Question> questions;
	
	@OneToMany(mappedBy = "questionarie")
	private List<AnswerResult> answers;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID")
	private User answeredBy;
	
	@Column(name="CURRENT")
	private int currentQuestion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public List<AnswerResult> getAnswers() {
		return answers;
	}
	public void setAnswers(List<AnswerResult> answers) {
		this.answers = answers;
	}
	public User getAnsweredBy() {
		return answeredBy;
	}
	public void setAnsweredBy(User answeredBy) {
		this.answeredBy = answeredBy;
	}
	public int getCurrentQuestion() {
		return currentQuestion;
	}
	public void setCurrentQuestion(int currentQuestion) {
		this.currentQuestion = currentQuestion;
	}
}
