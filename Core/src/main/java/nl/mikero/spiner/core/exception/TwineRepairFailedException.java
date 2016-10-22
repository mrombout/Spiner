package nl.mikero.spiner.core.exception;

public class TwineRepairFailedException extends RuntimeException {

    public TwineRepairFailedException(String message, Exception exception) {
        super(message, exception);
    }

}
