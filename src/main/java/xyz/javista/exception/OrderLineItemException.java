package xyz.javista.exception;

public class OrderLineItemException extends Exception{
    public enum FailReason{
        ORDER_NOT_EXIST, CANNOT_CREATE_ITEM, ORDER_EXPIRED,

    }

    FailReason failReason;

    public FailReason getFailReason() {
        return failReason;
    }

    public void setFailReason(FailReason failReason) {
        this.failReason = failReason;
    }

    public OrderLineItemException(FailReason failReason) {
        this.failReason = failReason;
    }
}
