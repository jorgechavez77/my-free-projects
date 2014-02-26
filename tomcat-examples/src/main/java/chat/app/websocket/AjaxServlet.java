package chat.app.websocket;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import chat.app.domain.ChatIncident;
import chat.app.domain.Chatter;
import chat.app.service.ChatService;

public class AjaxServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ChatService chatService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		ApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(config.getServletContext());
		this.chatService = (ChatService) context.getBean("chatService");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Chatter chatter = (Chatter) req.getSession().getAttribute("user");

		ChatIncident chatIncident = chatter.getChatIncident();

		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("model: ");
		sb.append(chatIncident.getModel());
		sb.append(", ");
		sb.append("serie: ");
		sb.append(chatIncident.getSerie());
		sb.append(", ");
		sb.append("problem: ");
		sb.append(chatIncident.getProblem());
		sb.append("}");

		resp.getWriter().write(sb.toString());
	}

}
