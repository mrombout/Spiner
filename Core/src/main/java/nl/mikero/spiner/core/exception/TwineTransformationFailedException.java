package nl.mikero.spiner.core.exception;

/**
 * Thrown when the transforming of a Twine document failed for reasons other than IO.
 */
public class TwineTransformationFailedException extends RuntimeException {

    /**
     * Constructs a new TwineTransformationFailedException.
     *
     * @param message exception message
     * @param cause cause of this cause
     */
    public TwineTransformationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
