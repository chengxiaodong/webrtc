package common.exception;

public class TbFriendExsitException extends Exception{
	public TbFriendExsitException() {
        super();
    }

    public TbFriendExsitException(String message) {
        super(message);
    }

    public TbFriendExsitException(String message, Throwable cause) {
        super(message, cause);
    }

    public TbFriendExsitException(Throwable cause) {
        super(cause);
    }
}
