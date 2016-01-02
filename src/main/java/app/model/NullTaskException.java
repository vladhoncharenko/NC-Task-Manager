
package app.model;
/**
 * Null Task Exception
 * 
 * @author Vlad Honcharenko
 * @version 1.0
 */

public class NullTaskException extends Exception {

	private static final long serialVersionUID = 1L;

	public NullTaskException() {

	}

	public NullTaskException(String message) {
		super(message);
	}

}
