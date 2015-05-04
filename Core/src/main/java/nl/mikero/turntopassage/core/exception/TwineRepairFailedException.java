package nl.mikero.turntopassage.core.exception;

import java.io.IOException;

public class TwineRepairFailedException extends Exception {

    public TwineRepairFailedException(String message, Exception exception) {
        super(message, exception);
    }

}
