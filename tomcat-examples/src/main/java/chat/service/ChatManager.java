package chat.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import chat.ChatRoom;
import chat.domain.Chatter;
import chat.domain.Customer;
import chat.domain.HelpDesk;
import chat.log.MyLogger;

public class ChatManager {

	private List<Chatter> waitingCustomers = new ArrayList<>();
	private List<Chatter> attendingCustomers = new ArrayList<>();
	private List<Chatter> freeHelpDesks = new ArrayList<>();
	private List<Chatter> busyHelpDesks = new ArrayList<>();

	private ChatService chatService = new ChatServiceImpl();

	public ChatRoom registerChatter(String id, HttpServletResponse response) {
		MyLogger.print("register Chatter");
		ChatRoom room = new ChatRoom();
		if (chatService.isHelpDesk(id)) {
			HelpDesk helpDesk = new HelpDesk();
			helpDesk.setId(id);
			helpDesk.setResponse(response);
			room.setHelpDesk(helpDesk);
			synchronized (freeHelpDesks) {
				freeHelpDesks.add(helpDesk);
			}
		} else {
			Customer customer = new Customer();
			customer.setId(id);
			customer.setResponse(response);
			room.setCustomer(customer);
			synchronized (waitingCustomers) {
				waitingCustomers.add(customer);
			}
		}
		return room;
	}

	public ChatRoom lookFreeHelpDesk(Customer customer) {
		MyLogger.print("lookFreeHelpDesk");
		ChatRoom room = null;
		HelpDesk helpDesk = null;
		synchronized (freeHelpDesks) {
			if (!freeHelpDesks.isEmpty()) {
				helpDesk = (HelpDesk) freeHelpDesks.get(0);
				room = new ChatRoom(customer, helpDesk);
				freeHelpDesks.remove(0);
			}

			if (room != null && helpDesk != null) {
				assignHelpDeskToCustomer(customer, helpDesk);
			}
			MyLogger.print("my room" + room);
			return room;
		}
	}

	public void assignHelpDeskToCustomer(Customer customer, HelpDesk helpDesk) {
		synchronized (busyHelpDesks) {
			busyHelpDesks.add(helpDesk);
		}
		synchronized (attendingCustomers) {
			attendingCustomers.add(customer);
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

}
