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
import java.text.SimpleDateFormat;
import java.util.Date;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import util.HTMLFilter;
import chat.control.ChatRoom;
import chat.domain.Chatter;

@ServerEndpoint(value = "/websocket/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatWebSocket {

	private final static Logger LOG = LoggerFactory
			.getLogger(ChatWebSocket.class);

	private static final Set<ChatWebSocket> clientConnections = new CopyOnWriteArraySet<ChatWebSocket>();
	private static final Set<ChatWebSocket> helperConnections = new CopyOnWriteArraySet<ChatWebSocket>();

	// private String nickname;
	private Session session;
	private HttpSession httpSession;
	private Chatter chatter;

	private boolean isBusy;

	private transient static ChatWorker chatWorker;

	//
	static {
		LOG.info("Starting thread");
		chatWorker = new ChatWorker();
		Thread thread = new Thread(chatWorker, "chat-worker-thread");
		thread.start();
	}

	public static void stopThread() {
		LOG.info("Thread stopped");
		chatWorker.stop();
	}

	@OnOpen
	public void start(Session session, EndpointConfig config) {
		this.httpSession = (HttpSession) config.getUserProperties().get(
				HttpSession.class.getName());

		this.chatter = (Chatter) httpSession.getAttribute("user");
		this.chatter.setChatSocket(this);

		LOG.info("Open session for " + chatter);
		this.session = session;

		// Add socket to different lists
		if (chatter.getType().equals(Chatter.CLIENT)) {
			synchronized (clientConnections) {
				clientConnections.add(this);
				LOG.info(clientConnections.toString());
			}
		} else {
			synchronized (helperConnections) {
				helperConnections.add(this);
				LOG.info(helperConnections.toString());
			}
		}

		String message = String.format("%s %s", chatter.getId(), "has joined.");
		message = formatMessage(message);
		// broadcast(message);
		newBroadcast(message, this);
	}

	@OnClose
	public void end() {
		LOG.info("end");
		// Removing socket from lists
		// Removing chat room

		ChatRoom room = this.chatter.getChatRoom();
		if (room != null) {
			synchronized (clientConnections) {
				clientConnections.remove(this);
				Chatter client = room.getClient();
				synchronized (client) {
					LOG.info("Chatter {} leaves the room", client);
					client.setChatRoom(null);
					client.getChatSocket().isBusy = false;
				}
				LOG.info(clientConnections.toString());
			}

			synchronized (helperConnections) {
				helperConnections.remove(this);
				Chatter helper = room.getHelper();
				synchronized (helper) {
					LOG.info("Chatter {} leaves the room", helper);
					helper.setChatRoom(null);
					helper.getChatSocket().isBusy = false;
				}
				LOG.info(helperConnections.toString());
			}

			room.setClient(null);
			room.setHelper(null);
			room = null;
		} else {
			synchronized (clientConnections) {
				clientConnections.remove(this);
				LOG.info(clientConnections.toString());
			}
			synchronized (helperConnections) {
				helperConnections.remove(this);
				LOG.info(helperConnections.toString());
			}
		}
		//

		String message = String.format("* %s %s", chatter.getId(),
				"has disconnected.");
		broadcast(message);
		// chatWorker.stop();
	}

	@OnMessage
	public void incoming(String message) {
		LOG.info("incoming");
		// Never trust the client
		String filteredMessage = String.format("%s: %s", chatter.getId(),
				HTMLFilter.filter(message.toString()));
		filteredMessage = formatMessage(filteredMessage);
		newBroadcast(filteredMessage, this);
	}

	private static String formatMessage(String filteredMessage) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(sdf.format(new Date()));
		sb.append(")");
		sb.append(" ");
		sb.append(filteredMessage);
		return sb.toString();
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		LOG.info("onError");
		LOG.error("Chat Error: " + t.toString(), t);
	}

	private static void broadcast(String msg) {
		LOG.info("broadcast");
		msg = formatMessage(msg);
		synchronized (clientConnections) {
			for (ChatWebSocket client : clientConnections) {
				LOG.info("Sending message to all clients");
				sendMessage(msg, client);
			}
		}
		synchronized (helperConnections) {
			for (ChatWebSocket client : helperConnections) {
				LOG.info("Sending message to all helpers");
				sendMessage(msg, client);
			}
		}
	}

	private static void sendMessage(String msg, ChatWebSocket client) {
		try {
			synchronized (client) {
				client.session.getBasicRemote().sendText(msg);
			}
		} catch (IOException e) {
			LOG.error("Chat Error: Failed to send message to client", e);
			helperConnections.remove(client);
			try {
				client.session.close();
			} catch (IOException e1) {
				LOG.error("IOException", e1);
			}
			String message = String.format("* %s %s", client.chatter.getId(),
					"has been disconnected.");
			broadcast(message);
		}
	}

	private static class ChatWorker implements Runnable {
		boolean running = false;

		public ChatWorker() {
			running = true;
		}

		@Override
		public void run() {
			LOG.info("Running ChatWorker");
			while (running) {
				synchronized (helperConnections) {
					if (!helperConnections.isEmpty()) {
						for (ChatWebSocket helper : helperConnections) {
							// Look for free helpers
							synchronized (helper) {
								if (!helper.isBusy) {
									// LOG.info("helper {} is available",
									// helper.chatter);
									synchronized (clientConnections) {
										if (!clientConnections.isEmpty()) {
											for (ChatWebSocket client : clientConnections) {
												if (!client.isBusy) {
													// Meet helper and client
													LOG.info(
															"Setting chat room for: {}, {}",
															helper.chatter,
															client.chatter);
													new ChatRoom(
															client.chatter,
															helper.chatter);
													client.isBusy = true;
													helper.isBusy = true;
													String message = formatMessage("* New room for "
															+ client.chatter
																	.getId()
															+ " and "
															+ helper.chatter
																	.getId());
													newBroadcast(message,
															client);
													// client.session.getBasicRemote().sendText("");
													// helper.session.getBasicRemote().sendText("");
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
			LOG.info("Shutting down the thread");
		}

		public void stop() {
			LOG.info("Stopping thread");
			this.running = false;
		}
	}

	/**
	 * 
	 * @param msg
	 * @param socketClient
	 */
	private static void newBroadcast(String msg, ChatWebSocket socketClient) {
		LOG.info("newBroadcast");
		ChatRoom room = socketClient.chatter.getChatRoom();

		try {
			if (room == null) {
				socketClient.session.getBasicRemote().sendText(msg);
			} else {

				Chatter chatClient = room.getClient();
				Chatter chatHelper = room.getHelper();

				if (chatClient != null && chatHelper != null) {
					LOG.info("Sending message to room");
					ChatWebSocket client = chatClient.getChatSocket();
					synchronized (client) {
						client.session.getBasicRemote().sendText(msg);
					}
					ChatWebSocket helper = chatHelper.getChatSocket();
					synchronized (helper) {
						helper.session.getBasicRemote().sendText(msg);
					}
				} else {
					LOG.info("Sending message to one himself");
					synchronized (socketClient) {
						socketClient.session.getBasicRemote().sendText(msg);
					}
				}
			}
		} catch (IOException e) {
			LOG.error("Chat Error: Failed to send message to client", e);
			clientConnections.remove(socketClient);
			try {
				socketClient.session.close();
			} catch (IOException e1) {
				LOG.error("IOException", e1);
			}
			String message = String.format("%s %s",
					socketClient.chatter.getId(), "has been disconnected.");
			broadcast(message);
		}
	}

	@Override
	public String toString() {
		return "{ chatter: " + this.chatter + ", session: " + session.getId()
				+ ", httpSession: " + httpSession.getId() + ", isBusy: "
				+ this.isBusy + "}";
	}

}
