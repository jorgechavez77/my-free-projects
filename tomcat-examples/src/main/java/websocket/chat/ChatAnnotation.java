/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package websocket.chat;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import util.HTMLFilter;
import chat.control.ChatRoom;
import chat.domain.Chatter;

@ServerEndpoint(value = "/websocket/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatAnnotation {

	private static final Log log = LogFactory.getLog(ChatAnnotation.class);

	// private static final String GUEST_PREFIX = "Guest";
	// private static final AtomicInteger connectionIds = new AtomicInteger(0);
	private static final Set<ChatAnnotation> clientConnections = new CopyOnWriteArraySet<ChatAnnotation>();
	private static final Set<ChatAnnotation> helperConnections = new CopyOnWriteArraySet<ChatAnnotation>();

	// private String nickname;
	private Session session;
	private HttpSession httpSession;
	private Chatter chatter;

	private boolean isBusy;

	private transient static ChatWorker chatWorker;

	static {
		System.out.println("Starting thread");
		chatWorker = new ChatWorker();
		Thread thread = new Thread(chatWorker);
		thread.start();
	}

	public ChatAnnotation() {
		System.out.println("ChatAnnotation.new");
		// nickname = GUEST_PREFIX + connectionIds.getAndIncrement();
	}

	@OnOpen
	public void start(Session session, EndpointConfig config) {
		this.httpSession = (HttpSession) config.getUserProperties().get(
				HttpSession.class.getName());

		this.chatter = (Chatter) httpSession.getAttribute("user");
		this.chatter.setChatSocket(this);

		System.out.println("ChatAnnotation.start");
		this.session = session;

		// Adding sockets to different lists
		if (chatter.getType().equals(Chatter.CLIENT)) {
			clientConnections.add(this);
		} else {
			helperConnections.add(this);
		}

		String message = String.format("* %s %s", chatter.getId(),
				"has joined.");
		broadcast(message);
	}

	@OnClose
	public void end() {
		System.out.println("ChatAnnotation.end");
		// Removing socket from lists
		clientConnections.remove(this);
		helperConnections.remove(this);

		String message = String.format("* %s %s", chatter.getId(),
				"has disconnected.");
		broadcast(message);

		chatWorker.stop();
	}

	@OnMessage
	public void incoming(String message) {
		System.out.println("ChatAnnotation.incoming");
		// Never trust the client
		String filteredMessage = String.format("%s: %s", chatter.getId(),
				HTMLFilter.filter(message.toString()));
		// broadcast(filteredMessage);
		if (this.chatter.getChatRoom() != null) {
			newBroadcast(filteredMessage, this);
		} else {
			broadcast(filteredMessage);
		}
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		System.out.println("ChatAnnotation.onError");
		log.error("Chat Error: " + t.toString(), t);
	}

	private static void broadcast(String msg) {
		System.out.println("ChatAnnotation.broadcast");
		for (ChatAnnotation client : clientConnections) {
			System.out.println("Sending message to all clients");
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				log.debug("Chat Error: Failed to send message to client", e);
				clientConnections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String message = String.format("* %s %s",
						client.chatter.getId(), "has been disconnected.");
				broadcast(message);
			}
		}
		for (ChatAnnotation client : helperConnections) {
			System.out.println("Sending message to all helpers");
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				log.debug("Chat Error: Failed to send message to client", e);
				helperConnections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String message = String.format("* %s %s",
						client.chatter.getId(), "has been disconnected.");
				broadcast(message);
			}
		}
	}

	private static class ChatWorker implements Runnable {

		boolean running = false;

		public ChatWorker() {
			running = true;
		}

		@Override
		public void run() {
			System.out.println("Running ChatWorker");
			while (running) {
				synchronized (helperConnections) {
					if (!helperConnections.isEmpty()) {
						for (ChatAnnotation helper : helperConnections) {
							// Look for free helpers
							if (!helper.isBusy) {
								synchronized (clientConnections) {
									if (!clientConnections.isEmpty()) {
										for (ChatAnnotation client : clientConnections) {
											if (!client.isBusy) {
												// Meet helper and client
												System.out
														.println("Setting chat room");
												new ChatRoom(client.chatter,
														helper.chatter);
												client.isBusy = true;
												helper.isBusy = true;
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		public void stop() {
			System.out.println("Stopping thread");
			this.running = false;
		}
	}

	/**
	 * 
	 * @param msg
	 * @param socketClient
	 */
	private static void newBroadcast(String msg, ChatAnnotation socketClient) {
		System.out.println("ChatAnnotation.broadcast");
		ChatRoom room = socketClient.chatter.getChatRoom();

		Chatter chatClient = room.getClient();
		Chatter chatHelper = room.getHelper();

		try {
			if (chatClient != null && chatHelper != null) {
				System.out.println("Sending message to room");
				ChatAnnotation client = chatClient.getChatSocket();
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
				}
				ChatAnnotation helper = chatHelper.getChatSocket();
				synchronized (helper) {
					helper.session.getBasicRemote().sendText(msg);
				}
			} else {
				System.out.println("Sending message to one only");
				synchronized (socketClient) {
					socketClient.session.getBasicRemote().sendText(msg);
				}
			}
		} catch (IOException e) {
			log.debug("Chat Error: Failed to send message to client", e);
			clientConnections.remove(socketClient);
			try {
				socketClient.session.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			String message = String.format("* %s %s",
					socketClient.chatter.getId(), "has been disconnected.");
			broadcast(message);
		}
	}

}
