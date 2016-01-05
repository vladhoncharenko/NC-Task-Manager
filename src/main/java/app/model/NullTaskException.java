
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

    public NullTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullTaskException(Throwable cause) {
        super(cause);
    }

    protected NullTaskException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
