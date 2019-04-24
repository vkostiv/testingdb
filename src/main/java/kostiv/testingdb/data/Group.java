package kostiv.testingdb.data;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="GROUPS")
@JsonInclude(Include.NON_NULL)
public class Group {

	@Id
	@GeneratedValue
	@Column(name="GROUP_ID")
	private Long id;
	
	@Column(name="TITLE")
	private String name;
	
	@OneToMany(mappedBy="group")
	private List<User> users;
	
//	@ManyToMany
//	@JoinTable(name="GROUP_EXAM")
//	private List<Exam> exams;
	
	@Column(name="CODE")
	private String code;
	
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
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
