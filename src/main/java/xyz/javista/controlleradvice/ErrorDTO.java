package xyz.javista.controlleradvice;

public class ErrorDTO {
    private final int status;
    private final String message;

    public ErrorDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
