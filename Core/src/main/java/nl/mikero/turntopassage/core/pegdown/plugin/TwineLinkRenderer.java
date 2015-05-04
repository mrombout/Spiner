package nl.mikero.turntopassage.core.pegdown.plugin;

import org.pegdown.LinkRenderer;
import org.pegdown.ast.WikiLinkNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Renders a WikiLinkNode as a Twine link.
 *
 * @see WikiLinkNode
 */
public class TwineLinkRenderer extends LinkRenderer {

    /**
     * Regular Expression to match Twine links. The pattern currently matches
     * the following:
     * <p>
     * <ul>
     * <li><b>[[Label|Url]]</b></li>
     * <li><b>[[Url]]</b></li>
     * <li><b>[[Label||Url]]</b>, where the second {@code |} is considered part of the url</li>
     * <li><b>[[Label|Url|More]]</b>, where everything after the first {@code |} is considered part of the url</li>
     * </ul>
     */
    private static final Pattern LINKS = Pattern.compile("([^\\|]+)\\|?(.*)");

    @Override
    public Rendering render(WikiLinkNode node) {
        Matcher matcher = LINKS.matcher(node.getText());
        String url = node.getText();
        String text = node.getText();
        if (matcher.find()) {
            url = (!matcher.group(2).isEmpty() ? matcher.group(2) : matcher.group(1)) + ".xhtml";
            text = matcher.group(1);
        }
        return new Rendering(url, text);
    }
}
