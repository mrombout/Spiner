package nl.mikero.spiner.core.transformer.latex.model.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Environment extends AbstractCommand {
    private final List<Command> commands;

    private final Command beginCommand;
    private final Command endCommand;

    public Environment(String environment) {
        super("begin");

        this.commands = new ArrayList<>();

        this.beginCommand = new AbstractCommand("begin").parameters().add(environment).done();
        this.endCommand = new AbstractCommand("end").parameters().add(environment).done();

        parameters().add(environment);
    }

    public Environment addCommand(Command command) {
        Objects.requireNonNull(command);
        this.commands.add(command);

        return this;
    }

    @Override
    public String toString() {
        List<String> commandStrings = commands.stream()
                .map(command -> "\t" + command.toString().replace("\n", "\n\t")).collect(Collectors.toList());
        String environmentContent = String.join("\n", commandStrings);

        return String.format("%s%n%s%n%s", beginCommand.toString(), environmentContent, endCommand.toString());
    }
}
