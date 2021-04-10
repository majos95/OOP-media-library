package m19.core;

import java.io.Serializable;

public class CheckPriceBoundary extends Rule implements Serializable{
	
	private static final long serialVersionUID = 201901101348L;
	
	private User _user;
	private Work _work;

	public CheckPriceBoundary(int id, User user, Work work) {
		super(id);
		_user = user;
		_work = work;
	}

	@Override
	protected boolean checkValidity() {
		return !(_user.getUserBehavior() != UserBehavior.CUMPRIDOR && _work.getPrice() > 25);
	}

}
