package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class TwineLinkLinkRefProcessor implements LinkRefProcessor {

    public static final int BRACKET_NESTING_LEVEL = 1;

    @Override
    public boolean getWantExclamationPrefix() {
        return false;
    }

    @Override
    public int getBracketNestingLevel() {
        return BRACKET_NESTING_LEVEL;
    }

    @Override
    public boolean isMatch(BasedSequence nodeChars) {
        if (nodeChars.length() >= 4) {
            return nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
        }

        return false;
    }

    @Override
    public Node createNode(BasedSequence nodeChars) {
        return new TwineLink(nodeChars, false);
    }

    @Override
    public BasedSequence adjustInlineText(Document document, Node node) {
        assert (node instanceof TwineNode);
        TwineNode twineNode = (TwineNode) node;
        return twineNode.getText().ifNull(twineNode.getPassage());
    }

    @Override
    public boolean allowDelimiters(BasedSequence chars, Document document, Node node) {
        return false;
    }

    @Override
    public void updateNodeElements(Document document, Node node) {
        // no elements to update
    }

    public static class Factory implements LinkRefProcessorFactory {
        @Override
        public LinkRefProcessor apply(Document document) {
            return new TwineLinkLinkRefProcessor();
        }

        @Override
        public boolean getWantExclamationPrefix(DataHolder options) {
            return false;
        }

        @Override
        public int getBracketNestingLevel(DataHolder options) {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
