package m19.core;

import java.io.Serializable;

public class CheckCategory extends Rule implements Serializable{
	
	private static final long serialVersionUID = 201901101348L;
	
	private Work _work;
	
	public CheckCategory(int id, Work work) {
		super(id);
		_work = work;
	}

	@Override
	protected boolean checkValidity() {
		return !(_work.getCategory() == Category.REFERENCE);
	}

}
