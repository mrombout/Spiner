package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class TwineLinkLinkRefProcessor implements LinkRefProcessor {

    public static final int BRACKET_NESTING_LEVEL = 1;

    private final TwineLinkOptions options;

    public TwineLinkLinkRefProcessor(Document document) {
        System.out.println(String.format("TwineLinkLinkRefProcessor.TwineLinkLinkRefProcessor(%s)", document));
        this.options = new TwineLinkOptions(document);
    }

    @Override
    public boolean getWantExclamationPrefix() {
        System.out.println("TwineLinkLinkRefProcessor.getWantExclamationPrefix");
        return false;
    }

    @Override
    public int getBracketNestingLevel() {
        System.out.println("TwineLinkLinkRefProcessor.getBracketNestingLevel");
        return BRACKET_NESTING_LEVEL;
    }

    @Override
    public boolean isMatch(BasedSequence nodeChars) {
        System.out.println(String.format("TwineLinkLinkRefProcessor.isMatch(%s)", nodeChars));
        if (nodeChars.length() >= 4) {
            return nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
        }

        return false;
    }

    @Override
    public Node createNode(BasedSequence nodeChars) {
        System.out.println("TwineLinkLinkRefProcessor.createNode");
        return new TwineLink(nodeChars, false);
    }

    @Override
    public BasedSequence adjustInlineText(Document document, Node node) {
        System.out.println(String.format("TwineLinkLinkRefProcessor.adjustInlineText(%s, %s)", document, node));
        assert (node instanceof TwineNode);
        TwineNode twineNode = (TwineNode) node;
        return twineNode.getText().ifNull(twineNode.getLink());
    }

    @Override
    public boolean allowDelimiters(BasedSequence chars, Document document, Node node) {
        System.out.println("TwineLinkLinkRefProcessor.allowDelimiters");
        return false;
    }

    @Override
    public void updateNodeElements(Document document, Node node) {
        System.out.println("TwineLinkLinkRefProcessor.updateNodeElements");
    }

    public static class Factory implements LinkRefProcessorFactory {
        @Override
        public LinkRefProcessor apply(Document document) {
            System.out.println(String.format("TwineLinkLinkRefProcessor.apply(%s)", document));
            return new TwineLinkLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix(DataHolder options) {
            System.out.println(String.format("TwineLinkLinkRefProcessor.getWantExclamationPrefix(%s)", options));
            return false;
        }

        @Override
        public int getBracketNestingLevel(DataHolder options) {
            System.out.println(String.format("TwineLinkLinkRefProcessor.getBracketNestingLevel(%s)", options));
            return BRACKET_NESTING_LEVEL;
        }
    }
}
