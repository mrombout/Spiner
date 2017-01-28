package nl.mikero.spiner.core.transformer.latex.model.command;

import java.util.Objects;

/**
 * Raw LaTeX command. It is not processed in any way.
 */
public class RawTexCommand implements Command {
    private final String tex;

    /**
     * Constructs a new RawTexCommand.
     *
     * @param latex LateX command
     */
    public RawTexCommand(final String latex) {
        this.tex = Objects.requireNonNull(latex);
    }

    /**
     * Returns the LaTeX command.
     *
     * @return LaTex command
     */
    @Override
    public String toString() {
        return tex;
    }
}
