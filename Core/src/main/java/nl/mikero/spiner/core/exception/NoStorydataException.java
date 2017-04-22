package nl.mikero.spiner.core.exception;

public class NoStorydataException extends TwineRepairFailedException {
    /**
     * Constructs a new TwineRepairFailedException.
     */
    public NoStorydataException() {
        super("Input does not contain any <tw-storydata> nodes.", null);
    }
}
