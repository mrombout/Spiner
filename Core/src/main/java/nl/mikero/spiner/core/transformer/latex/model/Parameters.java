package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parameters {
    private final AbstractCommand parent;
    private final List<Parameter> parameters;

    public Parameters(final AbstractCommand parent) {
        this.parent = Objects.requireNonNull(parent);
        this.parameters = new ArrayList<>();
    }

    public Parameters add(final String value) {
        this.parameters.add(new Parameter(value));

        return this;
    }

    public AbstractCommand and() {
        return parent;
    }

    public AbstractCommand done() {
        return parent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Parameter parameter : parameters) {
            sb.append(parameter.toString());
        }

        return sb.toString();
    }
}
