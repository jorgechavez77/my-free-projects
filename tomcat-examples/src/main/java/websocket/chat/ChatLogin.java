package websocket.chat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.domain.Chatter;
import chat.log.MyLogger;
import chat.service.ChatService;
import chat.service.ChatServiceImpl;

@WebServlet(name = "chatLogin", urlPatterns = "/websocket/login")
public class ChatLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ChatService chatService = new ChatServiceImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String action = req.getParameter("action");
		if (action.equals("login")) {
			login(req, resp);
		} else {
			logout(req, resp);
		}
	}

	private void login(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String user = req.getParameter("user");
		String password = req.getParameter("password");

		MyLogger.print("user: " + user + ", password: " + password);

		req.getSession();

		Chatter chatter = chatService.findUser(user);

		if (chatter != null) {
			req.getSession().setAttribute("user", chatter);
			resp.sendRedirect("chat.jsp");
		} else {
			req.setAttribute("loginMessage", "User or password are incorrect");
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		}
	}

	private void logout(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.removeAttribute("user");
		resp.sendRedirect("index.jsp");
	}

}
