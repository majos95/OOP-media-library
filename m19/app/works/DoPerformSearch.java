package m19.app.works;

import m19.core.LibraryManager;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

import m19.app.works.Message;


/**
 * 4.3.3. Perform search according to miscellaneous criteria.
 */
public class DoPerformSearch extends Command<LibraryManager> {
	
	Input<String> _searchTerm;
	
  public DoPerformSearch(LibraryManager m) {
    super(Label.PERFORM_SEARCH, m);
    _searchTerm = _form.addStringInput(Message.requestSearchTerm());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
	  _form.parse();
	  for(Object work: _receiver.searchWorks(_searchTerm.value())) {
		  _display.addLine(work.toString());
	  }
	  _display.display();
  }
  
}
