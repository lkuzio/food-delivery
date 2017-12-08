package xyz.javista.exception;

public class OrderLineItemException extends Exception{
    public enum FailReason{
        ORDER_NOT_EXIST, CANNOT_CREATE_ITEM, ORDER_EXPIRED, ORDER_ITEM_NOT_EXIST,

    }

    final FailReason failReason;

    public FailReason getFailReason() {
        return failReason;
    }

    public OrderLineItemException(FailReason failReason) {
        this.failReason = failReason;
    }
}
