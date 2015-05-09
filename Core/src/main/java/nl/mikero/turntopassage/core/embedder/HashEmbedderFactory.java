package nl.mikero.turntopassage.core.embedder;

import com.google.inject.Inject;
import org.pegdown.ast.ExpImageNode;

import java.security.MessageDigest;

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
    public HashEmbedderFactory(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

    @Override
    public Embedder get(ExpImageNode node) {
        if (imageEmbedder == null) {
            imageEmbedder = new ImageEmbedder(messageDigest);
        }

        return imageEmbedder;
    }
}