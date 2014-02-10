package chat.domain;

public class HelpDesk extends Chatter {

	@Override
	public String getChatterType() {
		return Chatter.HELP_DESK;
	}

}
