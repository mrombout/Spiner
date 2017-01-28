package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.BasicCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Collection of {@link Parameter}.
 */
public class Parameters {
    private final BasicCommand parent;
    private final List<Parameter> parameters;

    /**
     * Constructs a new Parameters.
     *
     * @param parent parent command
     */
    public Parameters(final BasicCommand parent) {
        this.parent = Objects.requireNonNull(parent);
        this.parameters = new ArrayList<>();
    }

    /**
     * Adds a new {@link Parameter} to this collection.
     *
     * @param value parameter to add
     * @return this
     */
    public Parameters add(final String value) {
        this.parameters.add(new Parameter(value));

        return this;
    }

    public BasicCommand and() {
        return parent;
    }

    public BasicCommand done() {
        return parent;
    }

    /**
     * Renders this collection to a valid LaTeX string.
     *
     * @return latex representation of parameter collection
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Parameter parameter : parameters) {
            sb.append(parameter.toString());
        }

        return sb.toString();
    }
}
