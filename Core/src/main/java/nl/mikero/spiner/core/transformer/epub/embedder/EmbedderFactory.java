package nl.mikero.spiner.core.transformer.epub.embedder;

import java.net.URL;

/**
 * Factory for retrieving the embedders for the different types of Pegdown
 * nodes.
 */
public interface EmbedderFactory {
    /**
     * Returns the appropriate embedder that can embed the resource that the given URI points to in an EPUB document.
     *
     * @param url uri to resource to embed
     * @return appropriate embedder for given uri
     */
    Embedder get(URL url);
}
