package chat.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import chat.control.ChatRoom;
import chat.domain.Chatter;
import chat.domain.Customer;
import chat.domain.HelpDesk;
import chat.log.MyLogger;

public class ChatManager implements Runnable {

	private boolean running = true;

	private List<ChatRoom> chatRooms = new ArrayList<>();

	public List<ChatRoom> getChatRooms() {
		return this.chatRooms;
	}

	private List<Chatter> awaitingCustomers = new ArrayList<>();
	private List<Chatter> attendingCustomers = new ArrayList<>();
	private List<Chatter> freeHelpDesks = new ArrayList<>();
	private List<Chatter> busyHelpDesks = new ArrayList<>();

	private ChatService chatService = new ChatServiceImpl();

	public void addChatter(String id, HttpServletResponse response) {
		MyLogger.print("register Chatter");
		if (chatService.isHelpDesk(id)) {
			MyLogger.print("id: " + id + " is help desk");
			HelpDesk helpDesk = new HelpDesk();
			helpDesk.setId(id);
			helpDesk.setResponse(response);
			synchronized (freeHelpDesks) {
				freeHelpDesks.add(helpDesk);
			}
		} else {
			MyLogger.print("id: " + id + " is customer");
			Customer customer = new Customer();
			customer.setId(id);
			customer.setResponse(response);
			synchronized (awaitingCustomers) {
				awaitingCustomers.add(customer);
			}
		}
	}

	public void endCustomerHelpDeskThread(Customer customer, HelpDesk helpDesk) {
		synchronized (attendingCustomers) {
			attendingCustomers.remove(customer);
		}
		HelpDesk freeHelpDesk = helpDesk;
		synchronized (busyHelpDesks) {
			busyHelpDesks.remove(helpDesk);
		}
		synchronized (freeHelpDesks) {
			freeHelpDesks.add(freeHelpDesk);
		}
	}

	@Override
	public void run() {
		MyLogger.print("Assigning Chatters");
		while (running) {
			Chatter customer = null;
			Chatter helpDesk = null;
			synchronized (freeHelpDesks) {
				if (!freeHelpDesks.isEmpty()) {
					helpDesk = freeHelpDesks.get(0);
					synchronized (awaitingCustomers) {
						if (!awaitingCustomers.isEmpty()) {
							customer = awaitingCustomers.get(0);
							synchronized (chatRooms) {
								ChatRoom room = new ChatRoom();
								room.addChatter(helpDesk);
								room.addChatter(customer);
								chatRooms.add(room);
								freeHelpDesks.remove(helpDesk);
								awaitingCustomers.remove(customer);
							}
						}
					}
				}
			}
		}
	}

	public List<HttpServletResponse> getResponses(String id) {
		List<HttpServletResponse> responses = new ArrayList<>();
		synchronized (chatRooms) {
			for (ChatRoom chatRoom : chatRooms) {
				boolean found = false;
				for (Chatter chatter : chatRoom.getChatters()) {
					if (chatter.getId().equals(id)) {
						found = true;
					}
				}
				if (found) {
					for (Chatter chatter : chatRoom.getChatters()) {
						MyLogger.print("response from id: " + chatter.getId()
								+ " from room" + chatRoom);
						responses.add(chatter.getResponse());
					}
				}
			}
		}
		synchronized (awaitingCustomers) {
			for (Chatter chatter : awaitingCustomers) {
				if (chatter.getId().equals(id)) {
					MyLogger.print("response from chatter id: "
							+ chatter.getId() + " from awaiting cust");
					responses.add(chatter.getResponse());
				}
			}
		}
		synchronized (freeHelpDesks) {
			for (Chatter chatter : freeHelpDesks) {
				if (chatter.getId().equals(id)) {
					MyLogger.print("response from chatter id: "
							+ chatter.getId() + " from free help desk");
					responses.add(chatter.getResponse());
				}
			}
		}
		return responses;
	}

}
