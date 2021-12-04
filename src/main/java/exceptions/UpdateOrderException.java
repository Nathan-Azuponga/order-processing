package exceptions;

public class UpdateOrderException extends RuntimeException {

    public UpdateOrderException(String message) {
        super(message);
    }
}
