package nl.mikero.spiner.core.pegdown.plugin;

import org.pegdown.ast.Node;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.Visitor;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Represents a Twine link to a passage.
 */
public class TwineLinkNode extends TextNode {

    private final String href;

    /**
     * Constructs a new TwineLinkNode with the given parameter as the href and
     * text values.
     *
     * @param value value to use as the href and text of the new node
     */
    public TwineLinkNode(final String value) {
        this(value, value);
    }

    /**
     * Constructs a new TwineLinkNode with the given text and href. While both
     * parameters may not be null, the text parameter may be left empty. In
     * that case, the href serves as the text.
     *
     * @param text text for the new node. If empty, the href value is used as
     *             the value of text
     * @param href href for the new node
     */
    public TwineLinkNode(final String text, final String href) {
        super(text);
        this.href = Objects.requireNonNull(href);

        if (text.isEmpty()) {
            append(href);
        }
    }

    /**
     * Returns the href of this node.
     *
     * @return href of this node
     */
    public String getHref() {
        return href;
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit((Node) this);
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0}|{1}", super.toString(), getHref());
    }
}
