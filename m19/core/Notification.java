package m19.core;

import java.io.Serializable;


/**
 * Class that represents a notification.
 */
public class Notification implements Serializable {
	private static final long serialVersionUID = 201901101348L;
	
	private String _message;
	
	public Notification(String message) {
		_message = "ENTREGA: " + message;
	}
	
	public String toString() {
		return _message;
	}
	
	
	
}
