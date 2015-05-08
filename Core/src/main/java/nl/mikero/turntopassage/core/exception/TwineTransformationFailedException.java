package nl.mikero.turntopassage.core.exception;

import javax.xml.transform.TransformerException;

public class TwineTransformationFailedException extends RuntimeException {

    public TwineTransformationFailedException(String message, TransformerException exception) {
        super(message, exception);
    }
}
