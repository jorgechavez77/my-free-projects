package chat.service;

import chat.domain.Chatter;
import chat.domain.ProblemDetail;

public interface ChatService {

	Chatter findUser(String user);

	String getClientType(String id);

	void saveProblemDetail(ProblemDetail problemDetail);

}
