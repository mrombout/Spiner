package nl.mikero.spiner.core.transformer.epub.embedder;

import nl.siegmann.epublib.domain.Book;

public interface ResourceEmbedder {
    /**
     * Embeds all embeddable resources found in the input content.
     *
     * It also updates the input content so that all resources in there are pointing the new location inside the embedded
     * EPUB. See implementations for more details on how that is done.
     *
     * @param book book to embed resources into
     * @param content content to embed resources for
     * @return modified content with updated locations for embedded resources
     */
    String embed(final Book book, final String content);
}
