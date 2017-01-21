package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LatexContainer {
    private final List<Command> commands;

    /**
     * Constructs a new LatexDocument.
     */
    public LatexContainer() {
        this.commands = new ArrayList<>();
    }

    /**
     * Add a command to this document.
     *
     * @param command command to add, may not be null
     */
    public void addCommand(Command command) {
        Objects.requireNonNull(command);

        this.commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Render this document as a valid LaTeX document.
     *
     * @return valid LaTeX document content
     */
    @Override
    public String toString() {
        List<String> commandStrings = getCommands().stream().map(Object::toString).collect(Collectors.toList());
        return String.join("\n", commandStrings);
    }
}
