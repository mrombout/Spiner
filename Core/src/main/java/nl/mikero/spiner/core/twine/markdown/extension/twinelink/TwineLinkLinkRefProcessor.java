package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class TwineLinkLinkRefProcessor implements LinkRefProcessor {
    private static final int TWINE_LINK_MINIMAL_LENGTH = 4;
    protected static final int BRACKET_NESTING_LEVEL = 1;

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
        if (nodeChars.length() >= TWINE_LINK_MINIMAL_LENGTH) {
            return nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
        }

        return false;
    }

    @Override
    public Node createNode(final BasedSequence nodeChars) {
        return new TwineLink(nodeChars);
    }

    @Override
    public BasedSequence adjustInlineText(final Document document, final Node node) {
        TwineLink twineLink = (TwineLink) node;
        return twineLink.getText().ifNull(twineLink.getPassage());
    }

    @Override
    public boolean allowDelimiters(final BasedSequence chars, final Document document, final Node node) {
        return false;
    }

    @Override
    public void updateNodeElements(final Document document, final Node node) {
        // no elements to update
    }

    public static class Factory implements LinkRefProcessorFactory {
        @Override
        public LinkRefProcessor apply(final Document document) {
            return new TwineLinkLinkRefProcessor();
        }

        @Override
        public boolean getWantExclamationPrefix(final DataHolder options) {
            return false;
        }

        @Override
        public int getBracketNestingLevel(final DataHolder options) {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
