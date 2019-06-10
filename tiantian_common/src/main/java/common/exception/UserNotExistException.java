package common.exception;

/**
 * Author：汤小洋
 * Date：2018-04-28 15:47
 * Description：<描述>
 */
public class UserNotExistException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotExistException() {
        super();
    }

    public UserNotExistException(String message) {
        super(message);
    }

    public UserNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotExistException(Throwable cause) {
        super(cause);
    }
}
