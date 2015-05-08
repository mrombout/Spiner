package nl.mikero.turntopassage.core.pegdown.plugin;

import org.parboiled.common.StringUtils;
import org.pegdown.LinkRenderer;
import org.pegdown.ast.ExpImageNode;

import java.text.MessageFormat;

import static org.pegdown.FastEncoder.encode;

/**
 * Serializes a ExpImageNode into a standard HTML url.
 *
 * @see TwineLinkNode
 */
public class TwineImageLinkSerializer extends LinkRenderer {
    public Rendering render(ExpImageNode node, String text) {
        Rendering rendering = new Rendering(node.url, text);

        return StringUtils.isEmpty(node.title) ? rendering : rendering.withAttribute("title", encode(node.title));
    }
}
