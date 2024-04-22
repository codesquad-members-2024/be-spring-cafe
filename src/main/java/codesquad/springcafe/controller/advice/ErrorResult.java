package codesquad.springcafe.controller.advice;

public class ErrorResult {
    private int status;
    private String message;

    public ErrorResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public ErrorResult setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
