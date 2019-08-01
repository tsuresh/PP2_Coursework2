package exceptions;

public class StoreFullException extends RuntimeException {

    public StoreFullException(String message) {
        super(message);
    }

}
