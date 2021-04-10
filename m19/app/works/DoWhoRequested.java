package m19.app.works;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;

public class DoWhoRequested extends Command<LibraryManager>{

	Input<Integer> _id;

  /**
   * @param receiver
   */
  public DoWhoRequested(LibraryManager receiver) {
    super(Label.AUGMENT_PRICE, receiver);
    _id = _form.addIntegerInput(Message.requestWorkId());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
	  _form.parse();
	  for(Object user: _receiver.getUsersWithRequest(_id.value())) {
		  _display.addLine(user.toString());
	  }
	  _display.display();
  
}

}
