package nl.mikero.spiner.frontend.exception;

import java.util.Objects;

/**
 * Thrown when an FXML failed to load.
 */
public class FxmlLoadFailedException extends RuntimeException {

    private FxmlLoadFailedException() {
        throw new IllegalAccessError();
    }

    /**
     * Constructs a new FxmlLoadFailedException.
     *
     * @param fileName name of the file that failed to load
     * @param cause cause of this exception
     */
    public FxmlLoadFailedException(final String fileName, final Throwable cause) {
        super(String.format("Could not load '%s'.", fileName), cause);

        Objects.requireNonNull(fileName);
        Objects.requireNonNull(cause);

        if(fileName.isEmpty())
            throw new IllegalArgumentException("Argument 'fileName' may not be empty.");
    }
}
