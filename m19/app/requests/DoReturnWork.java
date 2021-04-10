package m19.app.requests;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;

/**
 * 4.4.2. Return a work.
 */
public class DoReturnWork extends Command<LibraryManager> {

	Input<Integer> _userId;
	Input<Integer> _workId;

  /**
   * @param receiver
   */
  public DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);
    _userId = _form.addIntegerInput(Message.requestUserId());
    _workId = _form.addIntegerInput(Message.requestWorkId());  
   
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
@Override
  public final void execute() throws DialogException {
	  _form.parse();
	  int fine = _receiver.returnWork(_userId.value(), _workId.value());
	  _receiver.checkUsersWorksNotifications(_workId.value());
	  if(fine>0) {
		  _display.popup(Message.showFine(_userId.value(), fine));
		  Form form = new Form();
		  Input<String> answer;
		  answer = form.addStringInput(Message.requestFinePaymentChoice());
		  form.parse();
		  if(answer.value().equals("s")) {
			  _receiver.payFine(_userId.value());
		  }
	  }
  }

}
