package xyz.javista.exception;

public class OrderLineItemException extends Exception{
    public enum FailReason{
        ORDER_NOT_EXIST, CANNOT_CREATE_ITEM, ORDER_EXPIRED, ORDER_ITEM_NOT_EXIST, NOT_ALLOWED

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

    public OrderLineItemException(FailReason failReason, String message) {
        this.failReason = failReason;
        this.message = message;
    }
}
