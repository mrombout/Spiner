package nl.mikero.spiner.core.pegdown.plugin;

import nl.mikero.spiner.core.transformer.latex.pegdown.LatexEncoder;
import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;

import java.util.Objects;

public class LatexVerbatimSerializer implements VerbatimSerializer {
    public static final LatexVerbatimSerializer INSTANCE = new LatexVerbatimSerializer();
    
    @Override
    public final void serialize(final VerbatimNode node, final Printer printer) {
        Objects.requireNonNull(node);
        Objects.requireNonNull(printer);

        printer.println().print("\\begin{verbatim}");

        String text = node.getText();
        if(!text.isEmpty())
            printer.indent(+2);
        printer.println();

        while(!text.isEmpty() && text.charAt(0) == '\n') {
            printer.print("\\\\").printchkln();
            text = text.substring(1);
        }
        printer.print(LatexEncoder.encode(text));

        printer.indent(-2).println().print("\\end{verbatim}");
    }
}
