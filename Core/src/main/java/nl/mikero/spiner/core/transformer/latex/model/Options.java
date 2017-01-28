package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents zero or more options in a LaTeX command.
 */
public class Options {
    private final AbstractCommand parent;
    private final Map<String, Option> options;

    /**
     * Constructs a new Options
     *
     * @param parent command these options belong to
     */
    public Options(final AbstractCommand parent) {
        this.parent = Objects.requireNonNull(parent);
        this.options = new HashMap<>();
    }

    /**
     * Add a new goal key value pair.
     *
     * @see Option
     * @param name goal name
     * @param value goal value
     * @return this
     */
    public Options add(final String name, final String value) {
        options.put(name, new Option(name, value));

        return this;
    }

    /**
     * Add a new goal key only.
     *
     * @param name goal name
     * @return this
     */
    public Options add(final String name) {
        return add(name, null);
    }

    /**
     * @return parent
     */
    public AbstractCommand and() {
        return parent;
    }

    /**
     * @return parent
     */
    public AbstractCommand done() {
        return parent;
    }

    /**
     * Renders options as a valid LaTeX options block.
     *
     * @return options as LaTeX options
     */
    @Override
    public String toString() {
        if(options.isEmpty())
            return "";
        List<String> optionStrings = options.values().stream().map(Option::toString).collect(Collectors.toList());
        return String.format("[%s]", String.join(",", optionStrings));
    }
}
