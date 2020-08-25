package exceptions;

public abstract class TransportException extends Exception {
    public TransportException() {
    }

    public TransportException(String msg) {
        super(msg);
    }
}

