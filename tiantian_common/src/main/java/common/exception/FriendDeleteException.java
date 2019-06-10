package common.exception;


public class FriendDeleteException extends Exception{
	public FriendDeleteException() {
        super();
    }

    public FriendDeleteException(String message) {
        super(message);
    }

    public FriendDeleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public FriendDeleteException(Throwable cause) {
        super(cause);
    }
}
