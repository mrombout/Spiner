package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LatexDocument {
    private final List<Command> commands;

    public LatexDocument() {
        this.commands = new ArrayList<>();
    }

    public void addCommand(Command command) {
        Objects.requireNonNull(command);

        this.commands.add(command);
    }

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
