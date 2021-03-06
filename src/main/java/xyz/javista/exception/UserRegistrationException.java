package xyz.javista.exception;

public class UserRegistrationException extends Exception {
    public enum FailReason {
        USER_ALREADY_EXIST
    }

    final FailReason failReason;

    public UserRegistrationException(FailReason failReason) {
        this.failReason = failReason;
    }

    public FailReason getFailReason() {
        return failReason;
    }

}
