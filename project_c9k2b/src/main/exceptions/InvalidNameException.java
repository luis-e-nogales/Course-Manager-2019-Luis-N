package exceptions;

public class InvalidNameException extends Exception {
    public String presentException() {
        return "string contained ; or , or :";
    }
}
