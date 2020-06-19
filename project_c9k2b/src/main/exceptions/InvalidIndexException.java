package exceptions;

public class InvalidIndexException extends RuntimeException {
    public String presentException() {
        return "invalid index";
    }
}
