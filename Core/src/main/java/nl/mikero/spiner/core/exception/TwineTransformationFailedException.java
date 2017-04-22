package nl.mikero.spiner.core.exception;

/**
 * Thrown when the transforming of a Twine document failed for reasons other than IO.
 */
public class TwineTransformationFailedException extends RuntimeException {

    /**
     * Constructs a new TwineTransformationFailedException.
     *
     * @param cause cause of this cause
     */
    public TwineTransformationFailedException(final Throwable cause) {
        super("Could not transform document", cause);
    }

}
