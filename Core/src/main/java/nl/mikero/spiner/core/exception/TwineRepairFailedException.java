package nl.mikero.spiner.core.exception;

/**
 * Thrown when the repairing of a twine input file fails due to reasons other than generic IO.
 *
 * Reasons of failing could be an invalid format, unrecognized format, incompatible twine story format, etc.
 */
public class TwineRepairFailedException extends RuntimeException {

    /**
     * Constructs a new TwineRepairFailedException.
     *
     * @param message exception message
     * @param cause cause of this cause
     */
    public TwineRepairFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
