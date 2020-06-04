package nl.mikero.spiner.core.twine.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.PegdownExtensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import nl.mikero.spiner.core.twine.markdown.extension.twinelink.TwineLinkExtension;

/**
 * PegdownTransitionMarkdownRenderParser is a replacement of the old Pegdown parser, while still behaving like Pegdown.
 * This class will go away in the future and will be replaced with parser based on a story format.
 */
public class PegdownTransitionMarkdownRenderParser implements MarkdownParser, MarkdownRenderer, MarkdownProcessor {
    private final Parser parser;
    private final HtmlRenderer renderer;

    public PegdownTransitionMarkdownRenderParser() {
        DataHolder options = PegdownOptionsAdapter.flexmarkOptions(true, PegdownExtensions.NONE, TwineLinkExtension.create());

        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    public String markdownToHtml(final Node markdownInput) {
        return renderer.render(markdownInput);
    }

    @Override
    public Node parseMarkdown(final String markdownContent) {
        return parser.parse(markdownContent);
    }
}
