package nl.mikero.spiner.core.exception;

import javax.xml.transform.TransformerException;

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
    public TwineTransformationFailedException(String message, TransformerException cause) {
        super(message, cause);
    }
}
