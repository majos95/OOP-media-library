package m19.core.exception;

public class RuleViolationException extends Exception {
	
	private int _ruleIx;
	
	private static final long serialVersionUID = 201901101348L;
	
	public RuleViolationException(Exception cause) {
		super(cause);
	}
	
	public RuleViolationException(int ruleIx) {
		_ruleIx = ruleIx;
	}
	
	public int getIndex() {
		return _ruleIx;
	}

}
