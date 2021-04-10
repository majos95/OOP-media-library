package m19.core;

import java.io.Serializable;
import java.util.List;

public class CheckRequestTwice extends Rule implements Serializable{
	
	private static final long serialVersionUID = 201901101348L;
	
	private User _user;
	private Work _work;

	public CheckRequestTwice(int id, User user, Work work) {
		super(id);
		_user = user;
		_work = work;
	}

	@Override
	protected boolean checkValidity() {
		List<Request> _userRequests = _user.getRequests();
		for (Request request: _userRequests) {
			if (request.getWork().getId() == _work.getId() && !request.getState()){
				return false;
			}
		}
		return true;	
	}
}
