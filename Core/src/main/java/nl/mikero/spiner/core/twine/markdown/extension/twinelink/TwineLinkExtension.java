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
    public void rendererOptions(MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.linkRefProcessorFactory(new TwineLinkLinkRefProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        rendererBuilder.nodeRendererFactory(new TwineLinkNodeRenderer.Factory());
        rendererBuilder.linkResolverFactory(new TwineLinkLinkResolver.Factory());
    }
}
