package exceptions;

public abstract class ManagementException extends Exception {
    public ManagementException() {
    }

    public ManagementException(String msg) {
        super(msg);
    }
}

