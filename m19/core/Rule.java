package m19.core;

import java.io.Serializable;

public abstract class Rule implements Serializable{
	
	private static final long serialVersionUID = 201901101348L;
	
	private int _id;
	
	public Rule(int id) {
		_id = id;
	}
	
	protected int getId() {
		return _id;
	}
	
	protected abstract boolean checkValidity();
}
