package nl.mikero.spiner.core.transformer.latex.model;

/**
 * A LaTeX parameter.
 */
public class Parameter {
    private final String value;

    /**
     * Constructs a new Parameter.
     *
     * @param value parameter value
     */
    public Parameter(String value) {
        this.value = value;
    }

    /**
     * Renders a LaTeX parameter.
     *
     * @return latex parameter
     */
    @Override
    public String toString() {
        return String.format("{%s}", value);
    }
}
