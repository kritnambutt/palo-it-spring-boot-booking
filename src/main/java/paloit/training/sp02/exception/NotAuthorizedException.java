package paloit.training.sp02.exception;

public class NotAuthorizedException extends RuntimeException {

    private String message;

    public NotAuthorizedException() {}

    public NotAuthorizedException(String msg) {
        super(msg);
        this.message = msg;
    }
}