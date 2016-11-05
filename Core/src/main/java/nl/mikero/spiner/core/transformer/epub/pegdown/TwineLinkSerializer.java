package nl.mikero.spiner.core.transformer.epub.pegdown;

import nl.mikero.spiner.core.pegdown.plugin.TwineLinkNode;
import org.pegdown.Printer;
import org.pegdown.ast.Node;
import org.pegdown.ast.Visitor;
import org.pegdown.plugins.ToHtmlSerializerPlugin;

import java.text.MessageFormat;

/**
 * Serializes a TwineLinkNode into a standard HTML url.
 *
 * @see TwineLinkNode
 */
public class TwineLinkSerializer implements ToHtmlSerializerPlugin {
    @Override
    public boolean visit(Node node, Visitor visitor, Printer printer) {
        if (node instanceof TwineLinkNode) {
            TwineLinkNode twineLinkNode = (TwineLinkNode) node;

            printer.print(MessageFormat.format("<a href=\"{0}\">{1}</a>", twineLinkNode.getHref(), twineLinkNode.getText()));
            return true;
        }
        return false;
    }
}
