package nl.mikero.turntopassage.core.exception;

import java.io.IOException;
import java.util.Objects;

public class TwineRepairFailedException extends RuntimeException {

    public TwineRepairFailedException(String message, Exception exception) {
        super(message, exception);
    }

}
