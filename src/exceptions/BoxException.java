package exceptions;

public abstract class BoxException extends RuntimeException {
        public BoxException() {
        }

        public BoxException(String msg) {
            super(msg);
        }
}

