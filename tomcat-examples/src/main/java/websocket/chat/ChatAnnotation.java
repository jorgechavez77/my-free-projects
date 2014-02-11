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
import chat.domain.Chatter;

@ServerEndpoint(value = "/websocket/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatAnnotation {

	private static final Log log = LogFactory.getLog(ChatAnnotation.class);

	// private static final String GUEST_PREFIX = "Guest";
	// private static final AtomicInteger connectionIds = new AtomicInteger(0);
	private static final Set<ChatAnnotation> connections = new CopyOnWriteArraySet<ChatAnnotation>();

	// private String nickname;
	private Session session;
	private HttpSession httpSession;
	private Chatter chatter;

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

		System.out.println("ChatAnnotation.start");
		this.session = session;
		connections.add(this);
		String message = String.format("* %s %s", chatter.getId(),
				"has joined.");
		broadcast(message);
	}

	@OnClose
	public void end() {
		System.out.println("ChatAnnotation:end");
		connections.remove(this);
		String message = String.format("* %s %s", chatter.getId(),
				"has disconnected.");
		broadcast(message);
	}

	@OnMessage
	public void incoming(String message) {
		System.out.println("ChatAnnotation:incoming");
		// Never trust the client
		String filteredMessage = String.format("%s: %s", chatter.getId(),
				HTMLFilter.filter(message.toString()));
		broadcast(filteredMessage);
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		System.out.println("ChatAnnotation:onError");
		log.error("Chat Error: " + t.toString(), t);
	}

	private static void broadcast(String msg) {
		System.out.println("ChatAnnotation.broadcast");
		for (ChatAnnotation client : connections) {
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				log.debug("Chat Error: Failed to send message to client", e);
				connections.remove(client);
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

}
