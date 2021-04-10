package m19.core;

import java.io.Serializable;

public class CheckNumberRequests extends Rule implements Serializable{
	
	private static final long serialVersionUID = 201901101348L;
	
	private User _user;

	public CheckNumberRequests(int id, User user) {
		super(id);
		_user = user;
	}

	@Override
	protected boolean checkValidity() {
		int _currentNumRequests = _user.numRequests();
		UserBehavior _currentBehavior = _user.getUserBehavior();
		
		if (_currentBehavior == UserBehavior.NORMAL && _currentNumRequests == 3) {
			return false;
		}
		else if (_currentBehavior == UserBehavior.CUMPRIDOR && _currentNumRequests == 5) {
			return false;
		}
		else if (_currentBehavior == UserBehavior.FALTOSO && _currentNumRequests == 1) {
			return false;
		}
		else {
			return true;
		}
	}
}
