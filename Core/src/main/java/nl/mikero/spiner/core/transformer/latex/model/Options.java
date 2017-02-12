package nl.mikero.spiner.core.transformer.latex.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import nl.mikero.spiner.core.transformer.latex.model.command.BasicCommand;

/**
 * Represents zero or more options in a LaTeX command.
 */
public class Options {
    private final BasicCommand parent;
    private final Map<String, Option> options;

    /**
     * Constructs a new Options.
     *
     * @param parent command these options belong to
     */
    public Options(final BasicCommand parent) {
        this.parent = Objects.requireNonNull(parent);
        this.options = new HashMap<>();
    }

    /**
     * Add a new goal key value pair.
     *
     * @param name goal name
     * @param value goal value
     * @return this
     * @see Option
     */
    public final Options add(final String name, final String value) {
        options.put(name, new Option(name, value));

        return this;
    }

    /**
     * Add a new goal key only.
     *
     * @param name goal name
     * @return this
     */
    public final Options add(final String name) {
        return add(name, null);
    }

    /**
     * Returns parent.
     *
     * Usage: `new BasicCommand().parameters().add("param1").add("param2").build().options().add("option1").build()`.
     *
     * @return parent
     */
    public final BasicCommand build() {
        return parent;
    }

    /**
     * Renders options as a valid LaTeX options block.
     *
     * @return options as LaTeX options
     */
    @Override
    public final String toString() {
        if(options.isEmpty())
            return "";
        List<String> optionStrings = options.values().stream().map(Option::toString).collect(Collectors.toList());
        return String.format("[%s]", String.join(",", optionStrings));
    }
}
