package nl.mikero.spiner.core.exception;

/**
 * Thrown when writing transformation output.
 */
public class TwineTransformationWriteException extends RuntimeException {

    /**
     * Constructs a new TwineTransformationWriteException.
     *
     * @param cause cause of this cause
     */
    public TwineTransformationWriteException(final Throwable cause) {
        super("Could not write transformed input to output stream", cause);
    }
}
