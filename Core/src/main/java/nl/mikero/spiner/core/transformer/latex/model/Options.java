package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;

import java.util.HashMap;
import java.util.Map;

public class Options {
    private final AbstractCommand parent;
    private final Map<String, Option> options;

    public Options(AbstractCommand parent) {
        this.parent = parent;
        this.options = new HashMap<>();
    }

    public Options add(String name, String value) {
        options.put(name, new Option(name, value));

        return this;
    }

    public Options add(String name) {
        return add(name, null);
    }

    public AbstractCommand and() {
        return parent;
    }

    public AbstractCommand done() {
        return parent;
    }
}
