package nl.mikero.spiner.core.transformer.epub.embedder;

import nl.siegmann.epublib.domain.Book;

import java.io.IOException;
import java.net.URL;

/**
 * Embeds a resources and defines the naming and location in the EPUB file.
 */
public interface Embedder {

    /**
     * Returns a path that resembles the path where the embedded resource will
     * end up in the EPUB file.
     *
     * @param url url of the image as defined by the user in twine
     * @return path of resource inside the EPUB file
     */
    String getHref(URL url) throws IOException;

    /**
     * Embeds the resource at the given {@code url} in the given {@link Book}.
     *
     * @param book book to embed resource in
     * @param url url to resource that should be embedded
     */
    void embed(Book book, URL url) throws IOException;

}
