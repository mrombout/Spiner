package nl.mikero.spiner.core.transformer.latex.pegdown;

/**
 * Provides some helper for printing LaTeX code.
 */
public class LatexPrinter /*extends org.pegdown.Printer*/ {

    /**
     * Prints a command (i.e. `\command`).
     *
     * @param command command to print
     * @return this
     */
    public LatexPrinter printCommand(final String command) {
        return printCommand(command, false);
    }

    /**
     * Prints a command (i.e. `\command{}`).
     *
     * @param command command to print
     * @param emptyParameters whether to include empty `{}`
     * @return this
     */
    public LatexPrinter printCommand(final String command, final boolean emptyParameters) {
        printCommandStart().print(command);
        if(emptyParameters)
            printParamStart().printParamEnd();
        return this;
    }

    /**
     * Prints a command start (i.e. '\').
     *
     * @return this
     */
    public LatexPrinter printCommandStart() {
//        sb.append("\\");
        return this;
    }

    /**
     * Prints a parameter (i.e. {parameter}).
     *
     * @param parameter parameter to print
     * @return this
     */
    public LatexPrinter printParam(final String parameter) {
        printParamStart().print(parameter).printParamEnd();
        return this;
    }

    /**
     * Prints a parameter start token (i.e. '{').
     *
     * @return this
     */
    public LatexPrinter printParamStart() {
//        sb.append('{');
        return this;
    }

    /**
     * Prints a parmeter end token (i.e. '}').
     *
     * @return this
     */
    public LatexPrinter printParamEnd() {
//        sb.append('}');
        return this;
    }

    /**
     * Prints an option (i.e. '[option]').
     *
     * @param option option to print
     * @return this
     */
    public LatexPrinter printOption(final String option) {
        printOptionStart().print(option).printOptionEnd();
        return this;
    }

    /**
     * Prints an option start token (i.e. '[').
     *
     * @return this
     */
    public LatexPrinter printOptionStart() {
//        sb.append('[');
        return this;
    }

    /**
     * Prints an option end token (i.e. ']').
     *
     * @return this
     */
    public LatexPrinter printOptionEnd() {
//        sb.append(']');
        return this;
    }

    /**
     * Prints a character.
     *
     * @param c character to print
     * @return this
     */
    public LatexPrinter print(final char c) {
//        super.print(c);
        return this;
    }

    /**
     * Prints a string.
     *
     * @param string string to print
     * @return this
     */
    public LatexPrinter print(final String string) {
//        super.print(string);
        return this;
    }

    /**
     * Prints a new line.
     *
     * @return this
     */
    public LatexPrinter println() {
//        super.println();
        return this;
    }

    /**
     * Prints an indent/dedent of `delta` spaces.
     *
     * @param delta number of spaces to indent (or dedent).
     * @return this
     */
    public LatexPrinter indent(final int delta) {
//        super.indent(delta);
        return this;
    }
}
