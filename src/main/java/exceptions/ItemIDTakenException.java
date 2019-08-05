package exceptions;

public class ItemIDTakenException extends RuntimeException {

    public ItemIDTakenException(String message) {
        super(message);
    }

}
