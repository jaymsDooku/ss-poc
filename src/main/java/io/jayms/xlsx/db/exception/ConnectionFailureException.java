package io.jayms.xlsx.db.exception;

public class ConnectionFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3904381320582391545L;

	public ConnectionFailureException() {
		super();
	}
	
	public ConnectionFailureException(String msg) {
		super(msg);
	}
}
