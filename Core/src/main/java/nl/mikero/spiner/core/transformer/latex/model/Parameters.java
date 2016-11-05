package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;

import java.util.ArrayList;
import java.util.List;

public class Parameters {
    private final AbstractCommand parent;
    private final List<Parameter> parameters;

    public Parameters(AbstractCommand parent) {
        this.parent = parent;
        this.parameters = new ArrayList<>();
    }

    public Parameters add(String value) {
        this.parameters.add(new Parameter(value));

        return this;
    }

    public AbstractCommand and() {
        return parent;
    }

    public AbstractCommand done() {
        return parent;
    }
}
