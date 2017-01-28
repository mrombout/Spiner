package nl.mikero.spiner.core.transformer.latex.model.command;

import java.util.Objects;

public class RawTexCommand implements Command {
    private final String text;

    public RawTexCommand(final String text) {
        this.text = Objects.requireNonNull(text);
    }

    @Override
    public String toString() {
        return text;
    }
}
