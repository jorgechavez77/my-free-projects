package chat.domain;

import javax.servlet.http.HttpServletResponse;

public class HelpDesk implements Chatter {

	private String id;
	private HttpServletResponse response;

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getChatterType() {
		return Chatter.HELP_DESK;
	}

}
