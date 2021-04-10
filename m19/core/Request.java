package m19.core;

import java.io.Serializable;

public class Request implements Serializable{
	
	private static final long serialVersionUID = 201901101348L;
	
	private int _id;
	private int _deadline;
	private Work _requestedWork;
	private boolean _delivered;
	
	
	public Request(int id, int deadlineDate, Work work) {
		_id = id;
		_deadline = deadlineDate;
		_requestedWork = work;
	}

	protected Work getWork() {
		return _requestedWork;
	}
	
	protected void returnRequest() {
		_delivered = true;
	}
	
	protected boolean getState() {
		return _delivered;
	}
	
	protected int getDeadline() {
		return _deadline;
	}
	
	protected void setDeadline(int deadline) {
		_deadline = deadline;
	}
	
	protected int getId() {
		return _id;
	}

}
