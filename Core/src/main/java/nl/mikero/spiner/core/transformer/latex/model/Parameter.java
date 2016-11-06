package nl.mikero.spiner.core.transformer.latex.model;

/**
 * A LaTeX parameter.
 */
import java.util.Objects;

public class Parameter {
    private final String value;

    /**
     * Constructs a new Parameter.
     *
     * @param value parameter value
     */
    public Parameter(String value) {
        this.value = Objects.requireNonNull(value);
        if(this.value != null && this.value.isEmpty())
            throw new IllegalArgumentException("Parameter value must not be empty");
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
