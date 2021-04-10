package m19.core;


import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class User implements Comparable<User>, Serializable {
	private static final long serialVersionUID = 201901101352L;
	
	private int _id;
	private boolean _isActive;
	private String _name;
	private String _email;
	private UserBehavior _userBehavior;
	private List<Request> _requests = new LinkedList<>();
	private List<Notification> _notifications = new LinkedList<>();
	private List<Integer> _worksUserWantsNotification = new LinkedList<>();
	private int _fine;
	private int _followedRequestsOnTime;
	
	
	
	public User(String name, String email) {
		_isActive = true;
		_name = name;
		_email = email;
		_userBehavior = UserBehavior.NORMAL;
		_fine = 0;
	}
	
	protected boolean isActive() {
		return _isActive;
	}
	
	protected void setId(int id) {
		_id = id;
	}
	
	public int getId() {
		return _id;
	}
	
	protected int getFine() {
		return _fine;
	}
	
	protected void updateFine(int nDays) {
		_fine += nDays*5;
	}
	
	protected void payFine(int date) {
		_fine = 0;
		for (Request request : _requests) {
			if(request.getDeadline() < date) {
				return;
			}
		}
		this.activateUser();
	}
	
	
	protected List<Notification> getNotifications(){
		return _notifications;
	}
	
	protected void removeNotifications() {
		_notifications.clear();
	}
	
	public void addNotification(Work work) {
		Notification notification = new Notification(work.toString());
		_notifications.add(notification);
		_worksUserWantsNotification.remove(Integer.valueOf(work.getId()));
	}
	
	
	protected void suspendUser() {
		_isActive = false;
	}
	
	protected void activateUser() {
		_isActive = true;
	}

	
	public String toString() {
		String active;
		String result;
	
		if (isActive())
		 	active = "ACTIVO";
		else 
			active = "SUSPENSO";
		
		result = Integer.toString(_id) + " - " + _name + " - " + _email + " - " + _userBehavior.toString() + " - " + active; 
		
		if(_fine>=0) {
			if (!_isActive) {
			result+= " - EUR " + Integer.toString(_fine);
			}
		}
		return result;
	}
	
	
	protected void checkBehavior(boolean wasOnTime) {
			if (wasOnTime && _followedRequestsOnTime >= 0) {
				_followedRequestsOnTime++;
				if(	_followedRequestsOnTime == 3 && _userBehavior == UserBehavior.FALTOSO) {
					_userBehavior = UserBehavior.NORMAL;
				}else if(_followedRequestsOnTime == 5 && _userBehavior == UserBehavior.NORMAL) {
					_userBehavior = UserBehavior.CUMPRIDOR;
				}
			}else if(wasOnTime && _followedRequestsOnTime < 0) {
				_followedRequestsOnTime = 1;
				
			}else if(!wasOnTime && _followedRequestsOnTime <= 0) {
				_followedRequestsOnTime--;
				if(_followedRequestsOnTime == -3 && _userBehavior == UserBehavior.NORMAL) {
					_userBehavior = UserBehavior.FALTOSO;
				}
			}else if(!wasOnTime && _followedRequestsOnTime > 0) {
				_followedRequestsOnTime = -1;	
				if(_userBehavior == UserBehavior.CUMPRIDOR){
					_userBehavior = UserBehavior.NORMAL;
				}
			}
	}
	
	@Override
	public int compareTo(User user) {
		String _otherName = user.getUsername();
	    return this.getUsername().compareTo(_otherName);
	}
	
	@Override
	public boolean equals(Object obj) {
	    return (obj instanceof User) && ((User) obj)._id == _id;
	}
	
	protected String getUsername() {
		return _name;
	}
	
	protected String getEmail() {
		return _email;
	}
	
	
	protected List<Request> getRequests(){
		return _requests;
	}
	
	protected int numRequests() {
		return _requests.size();
	}
	
	protected UserBehavior getUserBehavior() {
		return _userBehavior;
	}


	protected void addUserRequest(Request newRequest) {
		_requests.add(0, newRequest);
	}


	
	protected List<Integer> getWorksUserWantsNotification() {
		return _worksUserWantsNotification;
	}
	
	

}
