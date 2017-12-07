package xyz.javista.exception;

public class UserException extends RuntimeException {
    public enum FailReason {
        USER_NOT_FOUND
    }

    private final FailReason failReason;
    private final String message;

    public FailReason getFailReason() {
        return failReason;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public UserException(FailReason failReason, String message) {
        super(message);
        this.failReason = failReason;
        this.message = message;
    }
}
