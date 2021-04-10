package m19.core;

import java.io.Serializable;

public class CheckUserSuspended extends Rule implements Serializable{
	
	private static final long serialVersionUID = 201901101348L;
	
	private User _user;

	public CheckUserSuspended(int id, User user) {
		super(id);
		_user = user;
	}

	@Override
	protected boolean checkValidity() {
		return _user.isActive();	
	}

}
