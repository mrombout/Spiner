package nl.mikero.spiner.core.transformer;

import nl.mikero.spiner.core.twine.model.TwStorydata;

import java.io.OutputStream;

/**
 * Transforms a Twine Story to another format.
 */
public interface Transformer {
    /**
     *
     * @param story story to transform
     * @param outputStream output stream to write transformed story to
     */
    void transform(TwStorydata story, OutputStream outputStream);

    /**
     * Returns the extension belonging to the file format this transformer supports.
     *
     * @return file extension, without a leading <code>.</code>
     */
    String getExtension();
}
