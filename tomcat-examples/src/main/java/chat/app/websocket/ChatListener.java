package chat.app.websocket;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ChatListener implements ServletContextListener {

	// private ChatWorker worker;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ChatListener initialized");
		// ChatAnnotation.startThread();
		// Thread thread = new Thread(worker);
		// thread.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ChatListener destroyed");
		ChatWebSocket.stopThread();
		// worker.setRunning(false);
		// worker.notify();
	}

}
