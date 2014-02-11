package websocket.chat;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ChatListener implements ServletContextListener {

	// private ChatWorker worker;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ChatListener initialized");
		// Thread thread = new Thread(worker);
		// thread.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ChatListener destroyed");
		// worker.setRunning(false);
		// worker.notify();
	}

}
