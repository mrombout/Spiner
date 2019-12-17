package nl.mikero.spiner.core.twine.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import nl.mikero.spiner.core.twine.markdown.extension.twinelink.TwineLinkExtension;

public class Harlowe310MarkdownRenderParser implements MarkdownParser, MarkdownRenderer, MarkdownProcessor {
    public String markdownToHtml(String markdownContent) {
        DataHolder options = PegdownOptionsAdapter.flexmarkOptions(true, Extensions.NONE, TwineLinkExtension.create());

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdownContent);
        return renderer.render(document);
    }
}
