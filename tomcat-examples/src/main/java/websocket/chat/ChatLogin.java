package websocket.chat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.log.MyLogger;

@WebServlet(name = "chatLogin", urlPatterns = "/websocket/login")
public class ChatLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String user = req.getParameter("user");
		String password = req.getParameter("password");

		MyLogger.print("user: " + user + ", password: " + password);

		req.setAttribute("user", user);

		resp.sendRedirect("chat.xhtml");
	}

}
