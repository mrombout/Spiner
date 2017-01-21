package nl.mikero.spiner.core.pegdown.plugin;

import nl.mikero.spiner.core.transformer.latex.pegdown.LatexEncoder;
import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.VerbatimNode;

public class LatexVerbatimSerializer implements VerbatimSerializer {
    public static final LatexVerbatimSerializer INSTANCE = new LatexVerbatimSerializer();
    
    @Override
    public void serialize(VerbatimNode node, Printer printer) {
        printer.println().print("\\begin{verbatim}").indent(+2).println();
        String text = node.getText();
        while(text.charAt(0) == '\n') {
            printer.print("\\\\");
            text = text.substring(1);
        }
        printer.print(LatexEncoder.encode(text));
        printer.indent(-2).print("\\end{verbatim}");
    }
}
