package nl.mikero.spiner.core.transformer.latex.model;

import java.util.Objects;

public class Option {
    private final String name;
    private final String value;

    /**
     * Constructs a new Option.
     *
     * @param name goal name
     * @param value goal value
     */
    public Option(final String name, final String value) {
        this.name = Objects.requireNonNull(name);
        this.value = value;

        if(this.name.isEmpty())
            throw new IllegalArgumentException("Option name must not be empty.");
        if(this.value != null && this.value.isEmpty())
            throw new IllegalArgumentException("Option value must not be empty, perhaps you meant to use null?");
    }

    /**
     * Renders this goal as a LaTeX goal.
     *
     * @return LaTeX goal
     */
    @Override
    public final String toString() {
        if(value != null)
            return String.format("%s=%s", name, value);
        return name;
    }
}
