package nl.mikero.spiner.core.transformer.latex.pegdown;

import org.pegdown.Printer;
import org.pegdown.ast.Node;
import org.pegdown.ast.Visitor;

public interface ToLatexSerializerPlugin {
    /**
     * Visit the given node.
     *
     * @param node The node to visit
     * @param visitor The visitor, for delegating back to handling children, etc
     * @param printer The printer to print output to
     * @return true if this plugin knew how to serialize the node, false otherwise
     */
    boolean visit(Node node, Visitor visitor, Printer printer);
}
