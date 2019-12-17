package nl.mikero.spiner.core.twine.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import nl.mikero.spiner.core.twine.markdown.extension.twinelink.TwineLinkExtension;

public class Harlowe310MarkdownRenderParser implements MarkdownParser, MarkdownRenderer, MarkdownProcessor {
    private final DataHolder options = PegdownOptionsAdapter.flexmarkOptions(true, Extensions.NONE, TwineLinkExtension.create());
    private final Parser parser;
    private final HtmlRenderer renderer;

    public Harlowe310MarkdownRenderParser() {
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    public String markdownToHtml(String markdownContent) {
        Node document = parseMarkdown(markdownContent);
        return renderer.render(document);
    }

    @Override
    public Node parseMarkdown(String markdownContent) {
        return parser.parse(markdownContent);
    }
}
