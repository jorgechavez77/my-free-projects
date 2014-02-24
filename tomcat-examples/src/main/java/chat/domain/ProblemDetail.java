package chat.domain;

import java.io.Serializable;

public class ProblemDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private String model;
	private String serie;
	private String problem;

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

}
