package nl.mikero.spiner.core.transformer.latex.model.command;

import nl.mikero.spiner.core.transformer.latex.model.LatexContainer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A LaTeX environment.
 */
public class Environment extends BasicCommand {
    private static final String CMD_BEGIN = "begin";
    private static final String CMD_END = "end";

    private final LatexContainer container;

    private final Command beginCommand;
    private final Command endCommand;

    /**
     * Constructs a new Environment.
     *
     * @param name environment name
     */
    public Environment(final String name) {
        super(CMD_BEGIN);
        Objects.requireNonNull(name);

        this.container = new LatexContainer();

        this.beginCommand = new BasicCommand(CMD_BEGIN).parameters().add(name).done();
        this.endCommand = new BasicCommand(CMD_END).parameters().add(name).done();

        parameters().add(name);
    }

    /**
     * Add a new {@link Command} to this environment.
     *
     * @param command command to add to environment
     * @return this
     */
    public final Environment addCommand(final Command command) {
        Objects.requireNonNull(command);
        container.addCommand(command);

        return this;
    }

    /**
     * Returns the {@link LatexContainer} this environment uses.
     *
     * @return container of this environment
     */
    public final LatexContainer getContainer() {
        return container;
    }

    /**
     * Renders environment as a valid LaTeX environment string.
     *
     * @return this environment as a LaTex string
     */
    @Override
    public final String toString() {
        List<String> commandStrings = container.getCommands().stream()
                .map(command -> "\t" + command.toString().replace("\n", "\n\t")).collect(Collectors.toList());
        String environmentContent = String.join("\n", commandStrings);

        return String.format("%s%n%s%n%s", beginCommand.toString(), environmentContent, endCommand.toString());
    }
}
