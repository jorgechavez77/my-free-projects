package websocket.chat;

import java.util.ArrayList;
import java.util.List;

public class ChatWorker implements Runnable {

	private List<ChatAnnotation> connections = new ArrayList<>();

	private boolean running;

	public List<ChatAnnotation> getConnections() {
		return connections;
	}

	public void setConnections(List<ChatAnnotation> connections) {
		this.connections = connections;
	}

	public ChatWorker() {
		this.running = true;
	}

	@Override
	public void run() {
		System.out.println("Running ChatWorker");
		while (running) {

		}
	}

}
