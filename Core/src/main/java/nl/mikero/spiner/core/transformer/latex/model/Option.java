package nl.mikero.spiner.core.transformer.latex.model;

/**
 * A LaTex command option.
 */
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
        this.name = name;
        this.value = value;
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
