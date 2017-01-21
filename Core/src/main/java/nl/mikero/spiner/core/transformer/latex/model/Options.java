package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
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
    public Options(AbstractCommand parent) {
        this.parent = Objects.requireNonNull(parent);
        this.options = new HashMap<>();
    }

    /**
     * Add a new option key value pair.
     *
     * @see Option
     * @param name option name
     * @param value option value
     * @return this
     */
    public Options add(String name, String value) {
        options.put(name, new Option(name, value));

        return this;
    }

    /**
     * Add a new option key only.
     *
     * @param name option name
     * @return this
     */
    public Options add(String name) {
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
