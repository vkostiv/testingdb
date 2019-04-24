package kostiv.testingdb.data;

import java.util.Date;

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
@Table(name="USERS")
@JsonInclude(Include.NON_NULL)
public class User {

	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private Long id;
	
	@Column(name="USER_NAME")
	private String username;
	
	@Column(name="USER_PASSWORD")
	private String password;
	
	@Column(name="USER_LAST_LOGIN")
	private Date lastLogin;
	
	@Column(name = "USER_TICKET_ID")
	private String ticketId;
	
	@Column(name = "USER_TOKEN")
	private String token;
	
	@Column(name = "USER_MARK")
	private Integer mark;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER,targetEntity=Group.class)
	@JoinColumn(name="GROUP_ID")
	private Group group;
	
	@Column(name = "HOST")
	private String host;
	
	@Column(name = "useragent")
	private String userAgent;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getTicketId() {
		return ticketId;
	}
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getMark() {
		return mark;
	}
	public void setMark(Integer mark) {
		this.mark = mark;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	@Override
	public int hashCode() {
		return ticketId.hashCode();
	}
	
	@Override
	public boolean equals(Object another) {
		if (another != null && another instanceof User) {
			return ticketId != null?ticketId.equals(((User) another).getTicketId()):false;
		} else {
			return false;
		}
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
