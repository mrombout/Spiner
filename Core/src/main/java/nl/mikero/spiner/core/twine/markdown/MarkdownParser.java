package nl.mikero.spiner.core.twine.markdown;

import com.vladsch.flexmark.util.ast.Node;

public interface MarkdownParser {
    Node parseMarkdown(String markdownContent);
}
