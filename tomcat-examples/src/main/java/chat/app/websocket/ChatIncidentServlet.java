package chat.app.websocket;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import chat.app.domain.ChatIncident;
import chat.app.domain.Chatter;
import chat.app.service.ChatService;

@WebServlet("/websocket/chatIncident")
public class ChatIncidentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static Logger LOG = LoggerFactory
			.getLogger(ChatWebSocket.class);

	private ChatService chatService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext());
		this.chatService = (ChatService) context.getBean("chatService");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Chatter chatter = (Chatter) request.getSession().getAttribute("user");

		String model = request.getParameter("modelo");
		String serie = request.getParameter("serie");
		String problem = request.getParameter("problema");

		ChatIncident chatIncident = new ChatIncident();
		chatIncident.setModel(model);
		chatIncident.setProblem(problem);
		chatIncident.setSerie(serie);

		chatIncident.setReportedBy(chatter.getId());
		chatIncident.setCreationDate(new Date());

		try {
			chatService.saveChatIncident(chatIncident);
			ChatIncident incident = chatService
					.findChatIncidentByReporter(chatter.getId());
			LOG.info("chat_incident_id : {}", incident.getId());
			chatter.setChatIncidentId(incident.getId());
			response.sendRedirect("chat.jsp");
		} catch (Exception e) {
			LOG.error("Failed to save problem", e);
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}
	}

}
