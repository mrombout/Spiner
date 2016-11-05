package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        if(options.isEmpty())
            return "";
        List<String> optionStrings = options.values().stream().map(Option::toString).collect(Collectors.toList());
        return String.format("[%s]", String.join(",", optionStrings));
    }
}
