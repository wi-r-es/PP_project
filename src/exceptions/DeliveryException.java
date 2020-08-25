package exceptions;

public abstract class DeliveryException extends Exception {
    public DeliveryException() {
    }

    public DeliveryException(String msg) {
        super(msg);
    }
}
