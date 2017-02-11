package nl.mikero.spiner.core.transformer.latex.model.command;

/**
 * Represents the `\\usepackage{}` LaTeX command.
 */
public class UsePackageCommand extends BasicCommand {
    private static final String COMMAND_NAME = "usepackage";

    /**
     * Constructs a new UsePackageCommand.
     */
    public UsePackageCommand() {
        super(COMMAND_NAME);
    }
}
