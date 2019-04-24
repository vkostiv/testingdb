package kostiv.testingdb.data;

import java.util.ArrayList;
import java.util.List;

public class Progress {

	private String username;
	private String groupname;
	private String ticketId;
	private Integer practicMark;
	private Integer examMark;
	private Integer totalMark;
	private String selectQuery;
	private String insertQuery;
	private String updateQuery;
	private String deleteQuery;
	private Double percent;
	private List<WrongAnswer> wrongAnswers = new ArrayList<WrongAnswer>();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public Integer getPracticMark() {
		return practicMark;
	}
	public void setPracticMark(Integer practicMark) {
		this.practicMark = practicMark;
	}
	public Integer getExamMark() {
		return examMark;
	}
	public void setExamMark(Integer examMark) {
		this.examMark = examMark;
	}
	public Integer getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(Integer totalMark) {
		this.totalMark = totalMark;
	}
	public String getSelectQuery() {
		return selectQuery;
	}
	public void setSelectQuery(String selectQuery) {
		this.selectQuery = selectQuery;
	}
	public String getInsertQuery() {
		return insertQuery;
	}
	public void setInsertQuery(String insertQuery) {
		this.insertQuery = insertQuery;
	}
	public String getUpdateQuery() {
		return updateQuery;
	}
	public void setUpdateQuery(String updateQuery) {
		this.updateQuery = updateQuery;
	}
	public String getDeleteQuery() {
		return deleteQuery;
	}
	public void setDeleteQuery(String deleteQuery) {
		this.deleteQuery = deleteQuery;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	public List<WrongAnswer> getWrongAnswers() {
		return wrongAnswers;
	}
	public void setWrongAnswers(List<WrongAnswer> wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}
