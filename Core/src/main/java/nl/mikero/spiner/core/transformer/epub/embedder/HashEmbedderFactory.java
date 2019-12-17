package nl.mikero.spiner.core.transformer.epub.embedder;

import java.security.MessageDigest;

import com.google.inject.Inject;
import com.vladsch.flexmark.ast.Image;

/**
 * Embeds resources by using a hash as the resource filename and placing them
 * in the appropriate folders for each supported media type.
 * <p>
 * The following folders/mediatypes are used:
 * <dl>
 * <dt>/Images/</dt>
 * <dd>PNG, JPG, GIF and SVG</dd>
 * </dl>
 */
public class HashEmbedderFactory implements EmbedderFactory {

    private final MessageDigest messageDigest;

    private ImageEmbedder imageEmbedder;

    /**
     * Constructs a new HashEmbedderFactory.
     *
     * @param messageDigest message digest to use
     */
    @Inject
    public HashEmbedderFactory(final MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    @Override
    public final Embedder get(final Image node) {
        if (imageEmbedder == null) {
            imageEmbedder = new ImageEmbedder(messageDigest);
        }

        return imageEmbedder;
    }
}
