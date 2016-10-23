package nl.mikero.spiner.core.exception;

/**
 * Thrown when the validation of a Twine file fails.
 */
public class TwineValidationFailedException extends Exception {

    /**
     * Constructs a new TwineValidationFailedExcetion.
     *
     * @param message exception message
     * @param cause cause of this exception
     */
    public TwineValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
