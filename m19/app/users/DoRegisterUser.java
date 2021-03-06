package m19.app.users;

import m19.core.LibraryManager;
import m19.core.User;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;


/**
 * 4.2.1. Register new user.
 */
public class DoRegisterUser extends Command<LibraryManager> {

  Input<String> _userName;
  Input<String> _email;


  /**
   * @param receiver
   */
  public DoRegisterUser(LibraryManager receiver) {
    super(Label.REGISTER_USER, receiver);
    _userName = _form.addStringInput(Message.requestUserName());
    _email = _form.addStringInput(Message.requestUserEMail());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    User user =  _receiver.registerUser(_userName.value(),_email.value());
    _display.popup(Message.userRegistrationSuccessful(user.getId()));
    	
  }
}

