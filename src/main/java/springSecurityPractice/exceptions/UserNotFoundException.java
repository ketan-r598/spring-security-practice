package springSecurityPractice.exceptions;

public class UserNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String exMsg) {
		super(exMsg);
	}
}
