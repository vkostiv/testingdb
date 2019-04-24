package kostiv.testingdb.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="QUESTIONS")
@JsonInclude(Include.NON_NULL)
public class Question {

	public static final int SINGLE_ANSWER = 0;
	public static final int MULTIPLE_ANSWER = 1;
	public static final int TEXT_ANSWER = 2;
	public static final int CODE_ANSWER = 3;
	public static final int CODE2_ANSWER = 4;
	
	@Id
	@GeneratedValue
	@Column(name="QUESTION_ID")
	private Long id;
	
	@Column(name="QUESTION_TITLE")
	private String title;
	
	@Column(name="QUESTION_INTRO")
	private String intro;
	
	@Column(name="QUESTION_TEXT")
	private String text;
	
	@Column(name="QUESTION_TYPE")
	private Integer type;
	
	@Column(name="QUESTION_MARK")
	private Integer mark;
	
	@Column(name="QUESTION_CHECK_QUERY")
	private String correctResultQuery;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="THEME_ID")
	private Theme theme;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="QUESTION_ID", referencedColumnName="QUESTION_ID")
	private List<AnswerCase> answers;
	
	@Column(name="QUESTION_SCHEMA")
	private String schema;
	
	@Column(name="CODE")
	private String code;
	
//	private List<Questionarie> questioneries;
	
//	@ManyToOne
//	@JoinColumn(name="GROUP_ID")
//	private QuestionGroup group;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<AnswerCase> getAnswers() {
		return answers;
	}
	public void setAnswers(List<AnswerCase> answers) {
		this.answers = answers;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public String getCorrectResultQuery() {
		return correctResultQuery;
	}
	public void setCorrectResultQuery(String correctResultQuery) {
		this.correctResultQuery = correctResultQuery;
	}
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
}
