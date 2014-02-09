package chat;

import chat.domain.Customer;
import chat.domain.HelpDesk;

public class ChatRoom {

	private HelpDesk helpDesk;
	private Customer customer;

	public ChatRoom() {
	}
	
	public ChatRoom(Customer customer, HelpDesk helpDesk) {
		this.customer = customer;
		this.helpDesk = helpDesk;
	}

	public HelpDesk getHelpDesk() {
		return helpDesk;
	}

	public void setHelpDesk(HelpDesk helpDesk) {
		this.helpDesk = helpDesk;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
