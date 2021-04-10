package m19.app.users;


import m19.app.users.Message;


import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

import pt.tecnico.po.ui.DialogException;


/**
 * 4.2.2. Show specific user.
 */
public class DoShowUser extends Command<LibraryManager> {

  // FIXME define input fields
  Input<Integer> _id;

  /**
   * @param receiver
   */
  public DoShowUser(LibraryManager receiver) {
    super(Label.SHOW_USER, receiver);
    _id = _form.addIntegerInput(Message.requestUserId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    _display.popup(_receiver.getUser(_id.value()));
    
    }

}
