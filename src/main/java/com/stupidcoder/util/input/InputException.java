package com.stupidcoder.util.input;

public class InputException extends RuntimeException{
    public InputException() {
    }

    public InputException(String message) {
        super(message);
    }

    public InputException(Throwable cause) {
        super(cause);
    }
}
