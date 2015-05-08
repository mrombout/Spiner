package nl.mikero.turntopassage.core.embedder;

import nl.mikero.turntopassage.core.model.TwPassagedata;
import nl.siegmann.epublib.domain.Book;

/**
 * {@inheritDoc}
 *
 * Images are all places in the {@code /Images/} folder. Each image is renamed
 * to the SHA-2 hash of theirs contents.
 *
 * This means that different images that originally had the same name can
 * exist. While images that have different names but the same content will only
 * be saved once.
 */
public class ImageEmbedder implements Embedder {

    /**
     * {@inheritDoc}
     *
     * Images will be read and their hashed content will be the new name of the
     * embedded file. The extension of the file is preserved.
     *
     * @param url url of the image as defined by the user in twine
     */
    public String getHref(String url) {
        return "";
    }

    /**
     * Embeds an image resource inside the EPUB archive.
     *
     * @param book book to embed the image in
     * @param passage passage the image was found in
     * @param url url to the image
     */
    @Override
    public void embed(Book book, TwPassagedata passage, String url) {

    }

}
