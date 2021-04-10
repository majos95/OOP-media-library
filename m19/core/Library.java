package m19.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.IOException;
import m19.core.exception.BadEntrySpecificationException;
import m19.core.exception.RuleViolationException;
import m19.core.exception.WorkNotBorrowedException;


/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 201901101348L;
  
  private int _nextUserId;
  private int _nextWorkId;
  
  private Date _date;
  private List<Work> _works;  
  private List<User> _users;
  private List<Request> _requests;

  
  protected Library() {
	  _nextUserId = 0;
	  _nextWorkId = 0;
	  _date = new Date();
	  _works = new LinkedList<Work>();
	  _users = new LinkedList<User>();
	  _requests = new LinkedList<Request>();
  }

  private void advanceUserId() {
	  _nextUserId++;
  }
  
  private void advanceWorkId() {
	  _nextWorkId++;
  }

  protected void importFile(String filename) throws BadEntrySpecificationException, IOException {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }

  protected int getCurrentDate() {
	return _date.getCurrentDate();
  }

  protected void advanceDays(Integer nDays) {
	_date.advanceDay(nDays);
	int currentDate = this.getCurrentDate();
	
	for(Request request: _requests) {
		if (request.getDeadline() < currentDate) {
			for(User user : _users) {
				if(user.getRequests().contains(request)) {
					user.suspendUser();
				}
			}
		}
	}
  }

  protected User registerNewUser(String userName, String eMail) {
	User newUser = new User(userName, eMail);
	newUser.setId(_nextUserId);
	this.advanceUserId();
	_users.add(newUser);	
	return newUser;
  }

	protected List<User> getUsers(){
		return _users;
	}
	
	protected List<User> getOrderedUsers() {
		List<User> ordered = new ArrayList<>(this.getUsers());
		Collections.sort(ordered);
		return ordered;
	}


	protected int totalUsers() {
		
		return _users.size();
	}
	
	protected List<Notification> getUserNotifications(User user){
		return (user.getNotifications());
	}
	
	protected void removeUserNotifications (Integer id) {
		(_users.get(id)).removeNotifications();
	}
	
	protected int totalWorks() {
	
		return _works.size();
	}
	
	protected List<Work> getWorks() {
	
		return _works;
	}


	protected void addWork(Work work) {
		work.setId(_nextWorkId);
		this.advanceWorkId();
		_works.add(work);
	}


	@SuppressWarnings("unused")
	private int getNextWorkId() {
		return _nextWorkId;
	}
	
	@SuppressWarnings("unused")
	private int getNextUserId() {
		return _nextUserId;
	}

	protected void checkRules(User user, Work work) throws RuleViolationException {
		CheckRequestTwice _rule1 = new CheckRequestTwice(1,user, work);
		CheckUserSuspended _rule2 = new CheckUserSuspended(2,user);
		CheckIfAvailable _rule3 = new CheckIfAvailable(3,work);
		CheckNumberRequests _rule4 = new CheckNumberRequests(4,user);
		CheckCategory _rule5 = new CheckCategory(5,work);
		CheckPriceBoundary _rule6 = new CheckPriceBoundary(6, user, work);
		
		if (!_rule1.checkValidity()) {
			throw new RuleViolationException(1);
		}
		else if (!_rule2.checkValidity()) {
			throw new RuleViolationException(2);
		}
		else if (!_rule3.checkValidity()) {
			throw new RuleViolationException(3);
		}
		else if (!_rule4.checkValidity()) {
			throw new RuleViolationException(4);
		}
		else if (!_rule5.checkValidity()) {
			throw new RuleViolationException(5);
		}
		else if (!_rule6.checkValidity()) {
			throw new RuleViolationException(6);
		}
			
		
	}

	protected int addRequest(int userId, int workId) {
		User user = this.getUsers().get(userId);
		Work work = this.getWorks().get(workId);
		int _currentCopies = work.getAllCopies();
		UserBehavior _currentBehavior = user.getUserBehavior();
		int _currentDate = this.getCurrentDate(); 
		int _deadline = _currentDate;
		
		if (_currentBehavior == UserBehavior.FALTOSO) {
			_deadline += 2 ;
		}
		else if(_currentCopies ==1) {
			if(_currentBehavior == UserBehavior.CUMPRIDOR) {
				_deadline += 8;
			}else if(_currentBehavior == UserBehavior.NORMAL){ 
				_deadline += 3;
			}
		}
		else if(_currentCopies > 1 && _currentCopies <=5) {
			if(_currentBehavior == UserBehavior.CUMPRIDOR) {
				_deadline += 15;
			}else if(_currentBehavior == UserBehavior.NORMAL){ 
				_deadline += 8 ;
			}
		}
		else if(_currentCopies > 5) {
			if(_currentBehavior == UserBehavior.CUMPRIDOR) {
				_deadline += 30;
			}else if(_currentBehavior == UserBehavior.NORMAL) {
				_deadline += 15;
			}
		}
		/*to certify that request id is unique it corresponds to the sum
		 * of the current date with userID and workID
		 */
		Request newRequest = new Request(_currentDate+userId+workId, _deadline, work);
		this.addRequest(newRequest);
		user.addUserRequest(newRequest);
		work.requestWork(); /* updates number of copies available*/
		
		return _deadline;
		
	
	}

	private void addRequest(Request newRequest) {
		_requests.add(0, newRequest);
	}


	
	protected int returnWork(User user, Work work) throws WorkNotBorrowedException {
		List<Request> _userRequests = user.getRequests();
		Iterator<Request> iter = _userRequests.iterator();
		Boolean _borrowedWork = false;
		Boolean wasOnTime = true;
		int fine = 0;
		while (iter.hasNext()) {
			Request request = (Request) iter.next();
			if(request.getWork().equals(work) && !request.getState()) {
				_borrowedWork = true;
				work.returnWork();
				if(request.getDeadline() < _date.getCurrentDate()) {
					wasOnTime = false;
					user.updateFine(_date.getCurrentDate() - request.getDeadline());
				}
				request.returnRequest();
				iter.remove();
				fine = user.getFine();
			}
		}
		if(!_borrowedWork) {
			throw new WorkNotBorrowedException();	
		}
		user.checkBehavior(wasOnTime);	
		return fine;
	}
	
	protected void removeUserNotifications (User user) {
		user.removeNotifications();
	}
	
	protected void addWorksUserWantsNotification(Integer userId, Integer workId) {
		(_users.get(userId)).getWorksUserWantsNotification().add(workId);
	}
	
	protected void chekcUserWorksNotification(Work work) {
		for(User user : getUsers()) {
			if (user.getWorksUserWantsNotification().contains(work.getId())) {
				user.addNotification(work);
			}
		}
	}

}
