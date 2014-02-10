package chat.domain;

public class Customer extends Chatter {

	@Override
	public String getChatterType() {
		return Chatter.CUSTOMER;
	}

}
