package nl.mikero.spiner.core.transformer.latex.model.command;

import nl.mikero.spiner.core.transformer.latex.model.LatexContainer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Environment extends AbstractCommand {
    private final LatexContainer container;

    private final Command beginCommand;
    private final Command endCommand;

    public Environment(String environment) {
        super("begin");
        Objects.requireNonNull(environment);

        this.container = new LatexContainer();

        this.beginCommand = new AbstractCommand("begin").parameters().add(environment).done();
        this.endCommand = new AbstractCommand("end").parameters().add(environment).done();

        parameters().add(environment);
    }

    public Environment addCommand(Command command) {
        Objects.requireNonNull(command);
        container.addCommand(command);

        return this;
    }

    public LatexContainer getContainer() {
        return container;
    }

    @Override
    public String toString() {
        List<String> commandStrings = container.getCommands().stream()
                .map(command -> "\t" + command.toString().replace("\n", "\n\t")).collect(Collectors.toList());
        String environmentContent = String.join("\n", commandStrings);

        return String.format("%s%n%s%n%s", beginCommand.toString(), environmentContent, endCommand.toString());
    }
}
