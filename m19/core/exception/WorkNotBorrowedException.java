package m19.core.exception;

public class WorkNotBorrowedException extends Exception {
	private static final long serialVersionUID =201901101348L;
	
	public WorkNotBorrowedException() {
		
	}
	
	public WorkNotBorrowedException(Exception cause) {
		super(cause);
	}

}
