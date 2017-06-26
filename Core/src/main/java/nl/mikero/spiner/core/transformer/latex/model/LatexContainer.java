package nl.mikero.spiner.core.transformer.latex.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nl.mikero.spiner.core.transformer.latex.model.command.Command;

/**
 * Contains a series of LaTeX {@link Command}s.
 */
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
    public final void addCommand(final Command command) {
        Objects.requireNonNull(command);

        this.commands.add(command);
    }

    /**
     * Returns the commands inside this container.
     *
     * @return commands inside this container
     */
    public final List<Command> getCommands() {
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
