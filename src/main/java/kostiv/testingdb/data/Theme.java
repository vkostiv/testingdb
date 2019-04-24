package kostiv.testingdb.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="THEMES")
@JsonInclude(Include.NON_NULL)
public class Theme {

	@Id
	@GeneratedValue
	@Column(name="THEME_ID")
	private Long id;
	
	@Column(name="THEME_NAME")
	private String name;
	
	@Column(name="THEME_DESCRIPTION")
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy="theme", fetch=FetchType.EAGER)
	private List<Question> questions;
	
	@Column(name="code")
	private String code;
	
	@Column(name="ORDER_ID")
	private Integer orderId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
}
