package nl.mikero.spiner.core.transformer.latex.model;

import java.util.Objects;

/**
 * A LaTeX command parameter.
 */
public class Parameter {
    private final String value;

    /**
     * Constructs a new Parameter.
     *
     * @param value parameter value
     */
    public Parameter(final String value) {
        this.value = Objects.requireNonNull(value);
        if(this.value.isEmpty())
            throw new IllegalArgumentException("Parameter value must not be empty");
    }

    /**
     * Renders a LaTeX parameter.
     *
     * @return latex parameter
     */
    @Override
    public final String toString() {
        return String.format("{%s}", value);
    }
}
