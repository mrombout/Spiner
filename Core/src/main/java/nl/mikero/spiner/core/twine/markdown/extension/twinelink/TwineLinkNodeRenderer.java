package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class TwineLinkNodeRenderer implements NodeRenderer {
    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(TwineLink.class, this::render));
        return set;
    }

    private void render(TwineLink node, NodeRendererContext context, HtmlWriter html) {
        if (!context.isDoNotRenderLinks()) {
            html.attr("href", node.getPassage() + ".xhtml");
            html.srcPos(node.getChars()).withAttr().tag("a");
            context.renderChildren(node);
            html.tag("/a");
        }
    }

    public static class Factory implements NodeRendererFactory {
        @Override
        public NodeRenderer apply(DataHolder options) {
            return new TwineLinkNodeRenderer();
        }
    }
}
