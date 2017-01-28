package nl.mikero.spiner.core.exception;

/**
 * Thrown when TwineXML can't be transformed to the extended format.
 */
public class ExtendTwineXmlTransformFailedException extends RuntimeException {
    /**
     * Constructs a new ExtendTwineXmlTransformFailedException.
     *
     * @param cause why the transform failed
     */
    public ExtendTwineXmlTransformFailedException(final Throwable cause) {
        super(cause);
    }
}
