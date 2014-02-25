package chat.app.websocket;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import chat.app.domain.ChatIncident;
import chat.app.service.ChatService;

@WebServlet("/websocket/problemDetail")
public class ProblemDetailServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static Logger LOG = LoggerFactory
			.getLogger(ChatWebSocket.class);

	@Autowired
	private ChatService chatService;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String model = request.getParameter("modelo");
		String serie = request.getParameter("serie");
		String problem = request.getParameter("problema");

		ChatIncident chatIncident = new ChatIncident();
		chatIncident.setModel(model);
		chatIncident.setProblem(problem);
		chatIncident.setSerie(serie);

		try {
			chatService.saveChatIncident(chatIncident);
			response.sendRedirect("chat.jsp");
		} catch (Exception e) {
			LOG.error("Failed to save problem", e);
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}
	}

}
