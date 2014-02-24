package websocket.chat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chat.domain.ProblemDetail;
import chat.service.ChatService;
import chat.service.ChatServiceImpl;

@WebServlet("/websocket/problemDetail")
public class ProblemDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static Logger LOG = LoggerFactory
			.getLogger(ChatWebSocket.class);

	// It has to be a Spring service
	private ChatService chatService = new ChatServiceImpl();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String model = request.getParameter("modelo");
		String serie = request.getParameter("serie");
		String problem = request.getParameter("problema");

		ProblemDetail problemDetail = new ProblemDetail();
		problemDetail.setModel(model);
		problemDetail.setProblem(problem);
		problemDetail.setSerie(serie);

		try {
			chatService.saveProblemDetail(problemDetail);
			response.sendRedirect("chat.jsp");
		} catch (Exception e) {
			LOG.error("Failed to save problem", e);
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}
	}

}
