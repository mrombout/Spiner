package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class TwineNode extends Node implements DoNotDecorate {
    private static final String OPENING_MARKER = "[[";
    private static final String CLOSING_MARKER = "]]";
    private static final String FORWARD_LINK_SEPARATOR = "->";
    private static final String BACKWARD_LINK_SEPARATOR = "<-";
    private static final char TWINE1_LINK_SEPARATOR = '|';

    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence separatorMarker = BasedSequence.NULL;
    protected BasedSequence passage = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected final boolean linkIsFirst;

    public TwineNode(boolean linkIsFirst) {
        this.linkIsFirst = linkIsFirst;
    }

    public TwineNode(BasedSequence chars, boolean linkIsFirst) {
        super(chars);
        this.linkIsFirst = linkIsFirst;
        setLinkChars(chars);
    }

    @Override
    public BasedSequence[] getSegments() {
        System.out.println("TwineNode.getSegments");
        return new BasedSequence[]{
                openingMarker,
                passage,
                separatorMarker,
                text,
                closingMarker
        };
    }

    public BasedSequence getText() {
        return text;
    }

    public BasedSequence getPassage() {
        return passage;
    }

    public void setLinkChars(BasedSequence linkChars) {
        int length = linkChars.length();
        openingMarker = linkChars.subSequence(0, OPENING_MARKER.length());
        closingMarker= linkChars.subSequence(length - CLOSING_MARKER.length(), length);

        int forwardLinkIndex = linkChars.lastIndexOf(FORWARD_LINK_SEPARATOR);
        int backwardLinkIndex = linkChars.lastIndexOf(BACKWARD_LINK_SEPARATOR);
        int twine1SeparatorIndex = linkChars.lastIndexOf(TWINE1_LINK_SEPARATOR);

        if (forwardLinkIndex != -1) {
            text = linkChars.subSequence(OPENING_MARKER.length(), forwardLinkIndex);
            passage = linkChars.subSequence(forwardLinkIndex + FORWARD_LINK_SEPARATOR.length(), length - CLOSING_MARKER.length());
        } else if (backwardLinkIndex != -1) {
            passage = linkChars.subSequence(OPENING_MARKER.length(), backwardLinkIndex);
            text = linkChars.subSequence(backwardLinkIndex + BACKWARD_LINK_SEPARATOR.length(), length - CLOSING_MARKER.length());
        } else if (twine1SeparatorIndex != -1) {
            text = linkChars.subSequence(OPENING_MARKER.length(), twine1SeparatorIndex);
            passage = linkChars.subSequence(twine1SeparatorIndex + 1, length - CLOSING_MARKER.length());
        } else {
            passage = linkChars.subSequence(OPENING_MARKER.length(), length - CLOSING_MARKER.length());
            text = passage;
        }
    }
}
