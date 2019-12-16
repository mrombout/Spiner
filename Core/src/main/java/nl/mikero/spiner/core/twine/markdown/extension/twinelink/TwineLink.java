package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class TwineLink extends TwineNode {
    public TwineLink(boolean linkIsFirst) {
        super(linkIsFirst);
    }

    public TwineLink(BasedSequence chars, boolean linkIsFirst) {
        super(chars, linkIsFirst);
    }
}
