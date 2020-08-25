package exceptions;

public abstract class HRException extends Exception {
    public HRException() {
    }

    public HRException(String message) {
        super(message);
    }
}

