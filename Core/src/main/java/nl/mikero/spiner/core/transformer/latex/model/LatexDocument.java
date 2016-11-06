package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple object model for a LaTeX document.
 */
public class LatexDocument extends LatexContainer {
    private final List<Command> commands;

    /**
     * Constructs a new LatexDocument.
     */
    public LatexDocument() {
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

    /**
     * Render this document as a valid LaTeX document.
     *
     * @return valid LaTeX document content
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Command command : commands) {
            sb.append(command.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
