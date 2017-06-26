package nl.mikero.spiner.core.transformer;

import java.io.OutputStream;

import nl.mikero.spiner.core.twine.model.TwStorydata;

/**
 * Transforms a Twine Story to another format.
 */
public interface Transformer {
    /**
     * Transforms a Twine story to another format.
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
