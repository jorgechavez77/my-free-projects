package chat.domain;

import javax.servlet.http.HttpServletResponse;

public interface Chatter {

	String CUSTOMER = "C";
	String HELP_DESK = "H";

	void setId(String id);

	void setResponse(HttpServletResponse response);

	String getChatterType();

}
