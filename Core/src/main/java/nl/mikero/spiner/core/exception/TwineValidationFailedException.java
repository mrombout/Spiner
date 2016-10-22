package nl.mikero.spiner.core.exception;

public class TwineValidationFailedException extends Exception {
    public TwineValidationFailedException(String message, Exception exception) {
        super(message, exception);
    }
}
