package nl.mikero.spiner.core.exception;

public class TwineTransformationWriteException extends RuntimeException {

    public TwineTransformationWriteException(String message, Exception exception) {
        super(message, exception);
    }
}
