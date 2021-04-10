package m19.app.users;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;


/**
 * 4.2.4. Show all users.
 */
public class DoShowUsers extends Command<LibraryManager> {

  /**
   * @param receiver
   */
  public DoShowUsers(LibraryManager receiver) {
    super(Label.SHOW_USERS, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
	for( Object user : _receiver.getUsers()) {
		_display.addLine(user.toString());
	}	
	_display.display();
  }  
}
