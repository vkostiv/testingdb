package kostiv.testingdb.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="TEST_RESULTS")
@JsonInclude(Include.NON_NULL)
public class TestResult {

	@Id
	@GeneratedValue
	@Column(name="TEST_ID")
	private Long id;
	
	@Column(name="TEST_COMPLETED")
	private Long dateCompleted;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	@OneToOne
	@JoinColumn(name="TEST_QUESTIONARIE")
	private Questionarie questionarie;
	
	@Column(name="TEST_AVG_MARK")
	private Double averageMark;
	
	@Column(name="TEST_PASSED")
	private Boolean passed;
	
	@Column(name="TEST_TIME_USED")
	private Long timeUsed;
	
	@Column(name="TEST_AVG_TIME_PER_QUESTION")
	private Long averageTimePerTask;
	
	@Column(name="TEST_TOTAL_MARK")
	private Integer totalMark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDateCompleted() {
		return dateCompleted;
	}
	public void setDateCompleted(Long dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Questionarie getQuestionarie() {
		return questionarie;
	}
	public void setQuestionarie(Questionarie marks) {
		this.questionarie = marks;
	}
	public Double getAverageMark() {
		return averageMark;
	}
	public void setAverageMark(Double averageMark) {
		this.averageMark = averageMark;
	}
	public Boolean getPassed() {
		return passed;
	}
	public void setPassed(Boolean passed) {
		this.passed = passed;
	}
	public Long getTimeUsed() {
		return timeUsed;
	}
	public void setTimeUsed(Long timeUsed) {
		this.timeUsed = timeUsed;
	}
	public Long getAverageTimePerTask() {
		return averageTimePerTask;
	}
	public void setAverageTimePerTask(Long averageTimePerTask) {
		this.averageTimePerTask = averageTimePerTask;
	}
	public int getTotalMark() {
		return totalMark;
	}
	public void setTotalMark(int i) {
		totalMark = i;
	}
}
