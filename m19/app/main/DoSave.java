package m19.app.main;

import java.io.IOException;

import m19.core.LibraryManager;
import m19.core.exception.MissingFileAssociationException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;



/**
 * 4.1.1. Save to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<LibraryManager> {

  Input<String> _fileName;

  /**
   * @param receiver
   */
  public DoSave(LibraryManager receiver) {
    super(Label.SAVE, receiver);
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
	 
		try{
		  _receiver.save();
		} catch (MissingFileAssociationException fnnf){
			  _fileName = _form.addStringInput(Message.newSaveAs());
			  _form.parse();
		  try {
			_receiver.saveAs(_fileName.value());
		  } catch (MissingFileAssociationException | IOException e) {
			e.printStackTrace();
		  }
		} catch (Exception e) {
			e.printStackTrace();
	}
  }
}
