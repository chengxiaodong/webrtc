package common.exception;

public class CancelBlackException extends Exception{
	public CancelBlackException() {
        super();
    }

    public CancelBlackException(String message) {
        super(message);
    }

    public CancelBlackException(String message, Throwable cause) {
        super(message, cause);
    }

    public CancelBlackException(Throwable cause) {
        super(cause);
    }
}
