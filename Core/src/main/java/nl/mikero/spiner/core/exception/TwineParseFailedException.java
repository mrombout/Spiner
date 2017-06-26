package nl.mikero.spiner.core.exception;

/**
 * Thrown when parsing of a Twine story fails.
 */
public class TwineParseFailedException extends RuntimeException {
    /**
     * Constructs a new TwineParseFailedException.
     *
     * @param cause throwable that caused the fail
     */
    public TwineParseFailedException(final Throwable cause) {
        super(cause);
    }
}
