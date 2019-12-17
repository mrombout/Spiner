package nl.mikero.spiner.core.transformer.epub.embedder;

import com.vladsch.flexmark.ast.Image;

/**
 * Factory for retrieving the embedders for the different types of Pegdown
 * nodes.
 */
public interface EmbedderFactory {
    /**
     * Returns the appropriate embedder that can embed a {@link com.vladsch.flexmark.ast.Image} in an EPUB document.
     *
     * @param node image input node
     * @return appropriate embedder for input node
     */
    Embedder get(Image node);
}
