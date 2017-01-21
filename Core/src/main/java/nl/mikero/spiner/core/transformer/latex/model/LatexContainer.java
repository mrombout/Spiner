package nl.mikero.spiner.core.transformer.latex.model;

import nl.mikero.spiner.core.transformer.latex.model.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LatexContainer {
    private final List<Command> commands;

    public LatexContainer() {
        this.commands = new ArrayList<>();
    }

    public void addCommand(Command command) {
        Objects.requireNonNull(command);

        this.commands.add(command);
    }

    public List<Command> getCommands() {
        return commands;
    }

    @Override
    public String toString() {
        List<String> commandStrings = getCommands().stream().map(Object::toString).collect(Collectors.toList());
        return String.join("\n", commandStrings);
    }
}
