package xyz.javista.web.controlleradvice;

public class ErrorDTO {
    private int status;
    private String message;

    public ErrorDTO() {

    }

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
