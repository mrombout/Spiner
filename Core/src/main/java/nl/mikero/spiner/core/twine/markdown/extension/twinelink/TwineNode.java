package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class TwineNode extends Node implements DoNotDecorate {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence link = BasedSequence.NULL;
    protected BasedSequence textSeparatorMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
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
        if (linkIsFirst) {
            return new BasedSequence[]{
                    openingMarker,
                    link,
                    textSeparatorMarker,
                    text,
                    closingMarker
            };
        } else {
            return new BasedSequence[]{
                    openingMarker,
                    text,
                    textSeparatorMarker,
                    link,
                    closingMarker
            };
        }
    }

    public BasedSequence getText() {
        return text;
    }

    public BasedSequence getLink() {
        return link;
    }

    public void setLinkChars(BasedSequence linkChars) {
        int length = linkChars.length();
        openingMarker = linkChars.subSequence(0, 2);
        closingMarker= linkChars.subSequence(length - 2, length);

        text = linkChars.subSequence(0, length);
    }
}
