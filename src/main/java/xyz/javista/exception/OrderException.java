package xyz.javista.exception;

public class OrderException extends Exception {
    public enum FailReason {
        ORDER_NOT_EXIST, ORDER_EXPIRED, NOT_ALLOWED

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

    public OrderException(FailReason failReason, String message) {
        this.failReason = failReason;
        this.message = message;
    }
}
