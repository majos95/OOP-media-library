package m19.app.main;

import m19.core.LibraryManager;
import m19.core.exception.ImportFileException;
import m19.app.exception.FileOpenFailedException;
import m19.app.main.Message;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import java.io.IOException;
import java.io.FileNotFoundException;


/**
 * 4.1.1. Open existing document.
 */
public class DoOpen extends Command<LibraryManager> {


  Input<String> _fileName;

  /**
   * @param receiver
   */
  public DoOpen(LibraryManager receiver) {
    super(Label.OPEN, receiver);
    _fileName = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.load(_fileName.value());
    } catch (FileNotFoundException fnfe) {
      throw new FileOpenFailedException(_fileName.value());
    } catch (ClassNotFoundException | ImportFileException | IOException e) {
      e.printStackTrace();
    }
  }

}


  
