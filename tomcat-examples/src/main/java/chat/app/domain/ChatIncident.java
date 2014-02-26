package chat.app.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "ChatIncident")
public class ChatIncident extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String model;
	@Column
	private String serie;
	@Column
	private String problem;
	@Column
	private String reportedBy;
	@Column
	private String assignedTo;
	@Column
	private Date creationDate;
	@OneToMany(mappedBy = "chatIncident")
	private List<ChatIncidentDetail> chatIncidentDetails;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ChatIncidentDetail> getChatIncidentDetails() {
		return chatIncidentDetails;
	}

	public void setChatIncidentDetails(
			List<ChatIncidentDetail> chatIncidentDetails) {
		this.chatIncidentDetails = chatIncidentDetails;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
