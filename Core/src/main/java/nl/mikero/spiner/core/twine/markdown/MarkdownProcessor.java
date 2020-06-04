package nl.mikero.spiner.core.twine.markdown;

import com.vladsch.flexmark.util.ast.Node;

public interface MarkdownProcessor extends MarkdownRenderer, MarkdownParser {
    String markdownToHtml(Node markdownInput);
}
