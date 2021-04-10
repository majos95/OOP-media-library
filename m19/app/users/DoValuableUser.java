package m19.app.users;

import m19.core.LibraryManager;
import m19.core.exception.BadEntrySpecificationException;
import pt.tecnico.po.ui.Command;

public class DoValuableUser  extends Command<LibraryManager> {
	
	public DoValuableUser(LibraryManager receiver) {
	    super(Label.VALUABLE_USER, receiver);
	  }

	public final void execute() {
		try {
			_display.popup(_receiver.getMostValuableUser().toString());
		}catch(BadEntrySpecificationException e) {
			e.printStackTrace();
		}
		
	}
}
