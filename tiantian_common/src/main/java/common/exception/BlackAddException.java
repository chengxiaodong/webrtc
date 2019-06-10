package common.exception;

public class BlackAddException extends Exception{
	public BlackAddException() {
        super();
    }

    public BlackAddException(String message) {
        super(message);
    }

    public BlackAddException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlackAddException(Throwable cause) {
        super(cause);
    }
}
