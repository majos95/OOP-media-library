package m19.app.requests;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Form;
import pt.tecnico.po.ui.Input;
import m19.app.requests.Message;


/**
 * 4.4.1. Request work.
 */
public class DoRequestWork extends Command<LibraryManager> {

	Input<Integer> _userId;
	Input<Integer> _workId;
  /**
   * @param receiver
   */
  public DoRequestWork(LibraryManager receiver) {
    super(Label.REQUEST_WORK, receiver);
    _userId = _form.addIntegerInput(Message.requestUserId());
    _workId = _form.addIntegerInput(Message.requestWorkId());   
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
 
@Override
	public final void execute() throws DialogException {
		_form.parse();
		int deadline = _receiver.requestWork(_userId.value(), _workId.value());
		if (deadline == -1) {
			Form form = new Form();
			Input<String> answer;
			answer = form.addStringInput(Message.requestReturnNotificationPreference());
			form.parse();
			if(answer.value().equals("s")) {
				_receiver.warnUserWhenWorkIsAvailable(_userId.value(), _workId.value());
			}
		} else{
			_display.popup(Message.workReturnDay(_workId.value(), deadline));
		}
	}
}
