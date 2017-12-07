package xyz.javista.exception;

public class DateTimeConverterException extends Exception {
    public enum FailReason {
        INVALID_DATE_TIME_FORMAT
    }

    final FailReason failReason;

    public DateTimeConverterException(FailReason failReason) {
        this.failReason = failReason;
    }

    public FailReason getFailReason() {
        return failReason;
    }

}
