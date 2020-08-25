package exceptions;

public abstract class PositionException extends Exception {
    public PositionException() {
    }

    public PositionException(String msg) {
        super(msg);
    }
}

