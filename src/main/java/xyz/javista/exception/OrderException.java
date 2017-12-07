package xyz.javista.exception;

public class OrderException extends Exception {
    public enum FailReason {
        ORDER_NOT_EXIST, ORDER_EXPIRED,

    }

    final FailReason failReason;

    public FailReason getFailReason() {
        return failReason;
    }

    public OrderException(FailReason failReason) {
        this.failReason = failReason;
    }
}
