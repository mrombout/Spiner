package nl.mikero.spiner.core.exception;

/**
 * Exception thrown when input does not contain any &lt;tw-storydata&gt; nodes.
 */
public class NoStorydataException extends TwineRepairFailedException {
    /**
     * Constructs a new NoStorydataException.
     */
    public NoStorydataException() {
        super("Input does not contain any <tw-storydata> nodes.", null);
    }
}
