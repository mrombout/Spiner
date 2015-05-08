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

    /**
     * Regular Expression to detect external links. The pattern currently
     * matches:
     * <p>
     * <ul>
     * <li><b>http://website.com</b></li>
     * <li><b>https://website.com</b></li></li>
     * <li><b>http://evenwithoutdomain</b></li>
     * </ul>
     * <p>
     * Other protocols are not supported because they would not make a lot of
     * sense on the majority if not all e-reader devices.
     */
    private static final Pattern EXTERNAL_LINK = Pattern.compile("https?:\\/\\/");

    private static final int GROUP_LABEL = 1;
    private static final int GROUP_URL = 2;

    @Override
    public Rendering render(WikiLinkNode node) {
        Matcher matcher = LINKS.matcher(node.getText());
        String url = node.getText();
        String text = node.getText();
        if (matcher.find()) {
            if (matcher.group(GROUP_URL).isEmpty()) {
                url = matcher.group(GROUP_LABEL);
            } else {
                url = matcher.group(GROUP_URL);
            }

            Matcher externalLinkMatcher = EXTERNAL_LINK.matcher(url);
            if(!externalLinkMatcher.find()) {
                url+= ".xhtml";
            }

            text = matcher.group(GROUP_LABEL);
        }
        return new Rendering(url, text);
    }
}
