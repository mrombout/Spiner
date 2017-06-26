package nl.mikero.spiner.core.pegdown.plugin;

import java.util.Objects;

import nl.mikero.spiner.core.transformer.latex.pegdown.LatexEncoder;
import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;

/**
 * Serializes verbatim content to the LaTeX verbatim environment.
 */
public class LatexVerbatimSerializer implements VerbatimSerializer {
    public static final LatexVerbatimSerializer INSTANCE = new LatexVerbatimSerializer();

    private static final int INDENT_DEPTH = 2;
    
    @Override
    public final void serialize(final VerbatimNode node, final Printer printer) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(printer);

        printer.println().print("\\begin{verbatim}");

        String text = node.getText();
        if(!text.isEmpty())
            printer.indent(+INDENT_DEPTH);
        printer.println();

        while(!text.isEmpty() && text.charAt(0) == '\n') {
            printer.print("\\\\").printchkln();
            text = text.substring(1);
        }
        printer.print(LatexEncoder.encode(text));

        printer.indent(-INDENT_DEPTH).println().print("\\end{verbatim}");
    }
}
