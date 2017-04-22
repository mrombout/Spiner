package nl.mikero.spiner.core.exception;

/**
 * Thrown when the repairing of a twine input file fails due to reasons other than generic IO.
 *
 * Reasons of failing could be an invalid format, unrecognized format, incompatible twine story format, etc.
 */
public class TwineRepairFailedException extends RuntimeException {

    /**
     * Constructs a new TwineRepairFailedException with the default message.
     *
     * @param cause cause of this exception
     */
    public TwineRepairFailedException(final Throwable cause) {
        this("Could not repair input stream", cause);
    }

    /**
     * Constructs a new TwineRepairFailedException.
     *
     * @param msg the detail message
     * @param cause the cause of this exception
     */
    public TwineRepairFailedException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
