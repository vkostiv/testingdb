package kostiv.testingdb.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="EXAMS")
@JsonInclude(Include.NON_NULL)
public class Exam {

	@Id
	@GeneratedValue
	@Column(name="EXAM_ID")
	private Long id;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="START_TIME")
	private Date startMoment;
	
	@Column(name="END_TIME")
	private Date endMoment;
	
	@Column(name="COMPLETED")
	private Boolean completed;
	
	@ManyToMany(targetEntity=Theme.class)
	private List<Theme> themes;
	
	@ManyToOne(targetEntity=Group.class)
	@JoinColumn(name="GROUP_ID")
	private List<Group> groups;
	
	@Column(name="CODE")
	private String code;
	
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
	public List<Theme> getThemes() {
		return themes;
	}
	public void setThemes(List<Theme> themes) {
		this.themes = themes;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public Date getStartMoment() {
		return startMoment;
	}
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}
	public Date getEndMoment() {
		return endMoment;
	}
	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
