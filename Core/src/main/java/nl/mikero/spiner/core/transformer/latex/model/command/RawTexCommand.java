package nl.mikero.spiner.core.transformer.latex.model.command;

public class RawTexCommand implements Command {
    private final String text;

    public RawTexCommand(String text) {
        this.text = text;
    }
}
