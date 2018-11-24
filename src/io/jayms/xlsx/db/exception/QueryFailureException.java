package io.jayms.xlsx.db.exception;

public class QueryFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3904381320582391545L;

	public QueryFailureException() {
		super();
	}
	
	public QueryFailureException(String msg) {
		super(msg);
	}
}
