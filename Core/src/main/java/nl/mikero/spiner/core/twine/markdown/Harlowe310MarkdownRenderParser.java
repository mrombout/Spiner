package nl.mikero.spiner.core.twine.markdown;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class Harlowe310MarkdownRenderParser implements MarkdownParser, MarkdownRenderer {
    public String markdownToHtml(String markdownContent) {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(markdownContent);
        return renderer.render(document);
    }
}
