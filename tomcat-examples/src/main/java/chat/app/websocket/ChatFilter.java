package chat.app.websocket;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns = { "/*", "/chat.jsp" })
public class ChatFilter implements Filter {

	private final static Logger LOG = LoggerFactory.getLogger(ChatFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("Initializing filter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Object obj = req.getSession().getAttribute("user");
		if (obj == null) {
			req.getRequestDispatcher("index.jsp").forward(request, response);
		}
	}

	@Override
	public void destroy() {
		LOG.info("Destroying filter");
	}

}
