package nl.mikero.spiner.core.exception;

/**
 * Thrown when writing transformation output.
 */
public class TwineTransformationWriteException extends RuntimeException {

    /**
     * Constructs a new TwineTransformationWriteException.
     *
     * @param message exception message
     * @param cause cause of this cause
     */
    public TwineTransformationWriteException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
