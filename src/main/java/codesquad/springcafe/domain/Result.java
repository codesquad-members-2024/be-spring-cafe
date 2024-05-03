package codesquad.springcafe.domain;

public class Result {
    private boolean valid;
    private String message;

    public Result(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public static Result fail(String message) {
        return new Result(false, message);
    }

    public static Result ok() {
        return new Result(true, "댓글 삭제를 성공했습니다");
    }
}
