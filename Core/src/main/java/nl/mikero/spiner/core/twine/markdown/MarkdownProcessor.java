package nl.mikero.spiner.core.twine.markdown;

public interface MarkdownProcessor extends MarkdownRenderer, MarkdownParser {
    String markdownToHtml(String markdownInput);
}
