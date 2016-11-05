package nl.mikero.spiner.frontend.exception;

/**
 * Thrown when an FXML failed to load.
 */
public class FxmlLoadFailedException extends RuntimeException {

    /**
     * Constructs a new FxmlLoadFailedException.
     *
     * @param fileName name of the file that failed to load
     * @param cause cause of this exception
     */
    public FxmlLoadFailedException(String fileName, Throwable cause) {
        super(String.format("Could not load '%s'.", fileName), cause);
    }
}
