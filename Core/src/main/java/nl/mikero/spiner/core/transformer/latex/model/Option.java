package nl.mikero.spiner.core.transformer.latex.model;

/**
 * A LaTex command option.
 */
import java.util.Objects;

public class Option {
    private final String name;
    private final String value;

    /**
     * Constructs a new Option.
     *
     * @param name option name
     * @param value option value
     */
    public Option(String name, String value) {
        this.name = Objects.requireNonNull(name);
        this.value = value;

        if(this.name.isEmpty())
            throw new IllegalArgumentException("Option name must not be empty.");
        if(this.value != null && this.value.isEmpty())
            throw new IllegalArgumentException("Option value must not be empty, perhaps you meant to use null?");
    }

    /**
     * Renders this option as a LaTeX option.
     *
     * @return LaTeX option
     */
    @Override
    public String toString() {
        if(value != null)
            return String.format("%s=%s", name, value);
        return name;
    }
}
