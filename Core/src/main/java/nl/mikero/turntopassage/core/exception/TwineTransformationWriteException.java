package nl.mikero.turntopassage.core.exception;

import java.io.IOException;

public class TwineTransformationWriteException extends RuntimeException {

    public TwineTransformationWriteException(String message, Exception exception) {
        super(message, exception);
    }
}
