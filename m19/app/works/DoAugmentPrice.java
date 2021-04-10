package m19.app.works;

import java.util.List;

import m19.core.LibraryManager;
import m19.core.Work;
import m19.core.exception.BadEntrySpecificationException;
import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.Input;

public class DoAugmentPrice extends Command<LibraryManager> {
	
	Input<String> _searchTerm;
	
  public DoAugmentPrice(LibraryManager m) {
    super(Label.AUGMENT_PRICE, m);
    _searchTerm = _form.addStringInput(Message.requestSearchTerm());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
	  _form.parse();
	  try {
		   List<Work> results = _receiver.augmentPrice(_searchTerm.value());
		   int totalAlteracoes =  results.size();
	
		   _display.addLine( "Total de alteracoes : " + Integer.toString(totalAlteracoes));
	  
		  for(Work work: results) {
			  _display.addLine(work.getTitle() + " novo preco: " + work.getPrice());
		  }
	  } catch(BadEntrySpecificationException e) {
		  e.printStackTrace();
	  }
	 
	  _display.display();
  }
  
} 
