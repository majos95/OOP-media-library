package m19.app.main;

import m19.core.LibraryManager;


import m19.app.main.Message;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;


/**
 * 4.1.3. Advance the current date.
 */
public class DoAdvanceDate extends Command<LibraryManager> {

  Input<Integer> _nDays;

  /**
   * @param receiver
   */
  public DoAdvanceDate(LibraryManager receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _nDays = _form.addIntegerInput(Message.requestDaysToAdvance());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    _form.parse();
  
    if( _nDays.value() > 0){
      _receiver.advanceDays(_nDays.value());
    }
  }
}
