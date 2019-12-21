package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataHolder;

public class TwineLinkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    private TwineLinkExtension() {

    }

    public static TwineLinkExtension create() {
        return new TwineLinkExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {
        // no render options supported
    }

    @Override
    public void parserOptions(final MutableDataHolder options) {
        // no parser options supported
    }

    @Override
    public void extend(final Parser.Builder parserBuilder) {
        parserBuilder.linkRefProcessorFactory(new TwineLinkLinkRefProcessor.Factory());
    }

    @Override
    public void extend(final HtmlRenderer.Builder rendererBuilder, final String rendererType) {
        rendererBuilder.nodeRendererFactory(new TwineLinkNodeRenderer.Factory());
    }
}
