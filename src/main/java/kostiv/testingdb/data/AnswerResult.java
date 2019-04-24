package kostiv.testingdb.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="RESULTS")
@JsonInclude(Include.NON_NULL)
public class AnswerResult {

	@Id
	@GeneratedValue
	@Column(name="RESULT_ID")
	private Long id;
	
	@Column(name="RESULT_TIMETAKEN")
	private Long timeTaken;
	
	@Column(name="RESULT_MARK")
	private Integer markObtained;
	
	@ManyToOne
	@JoinColumn(name="QUESTION_ID")
	@JsonIgnore
	private Question question;
	
	@ManyToOne
	@JoinColumn(name="QUESTIONARIE_ID")
	@JsonIgnore
	private Questionarie questionarie;
	
	@Column(name="ANSWER_QUERY")
	private String query;
	
	@Column(name="HOST")
	private String host;
	
	@Column(name="RESULT_TIME")
	private Date time;
	
	@Column(name = "user_agent")
	private String userAgent;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}
	public Integer getMarkObtained() {
		return markObtained;
	}
	public void setMarkObtained(Integer markObtained) {
		this.markObtained = markObtained;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Questionarie getQuestionarie() {
		return questionarie;
	}
	public void setQuestionarie(Questionarie questionarie) {
		this.questionarie = questionarie;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
}
