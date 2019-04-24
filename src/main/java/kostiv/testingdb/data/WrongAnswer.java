package kostiv.testingdb.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class WrongAnswer {

	private Long id;
	private Integer markObtained;
	private String query;
	private long timeTaken;
	private String questionText;
	private String questionTitle;
	private String schema;
	private String host;
	private String userAgent;
	
	public WrongAnswer(AnswerResult res) {
		this.setId(res.getId());
		this.setMarkObtained(res.getMarkObtained());
		this.setQuery(res.getQuery());
		this.setQuestionText(res.getQuestion().getText());
		this.setSchema(res.getQuestion().getSchema());
		this.setQuestionTitle(res.getQuestion().getTitle());
		this.setHost(res.getHost());
		this.setUserAgent(res.getUserAgent());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMarkObtained() {
		return markObtained;
	}

	public void setMarkObtained(Integer markObtained) {
		this.markObtained = markObtained;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public long getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
}
