package nl.mikero.turntopassage.core.embedder;

import nl.mikero.turntopassage.core.model.TwPassagedata;
import nl.siegmann.epublib.domain.Book;

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
    String getHref(String url);

    /**
     * Embeds the resource at the given {@code url} in the given {@link Book}.
     *
     * @param book book to embed resource in
     * @param passage passage the resource url was found
     * @param url url to resource that should be embedded
     */
    void embed(Book book, TwPassagedata passage, String url);

}
