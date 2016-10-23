package nl.mikero.spiner.core.embedder;

import org.pegdown.ast.ExpImageNode;

/**
 * Factory for retrieving the embedders for the different types of Pegdown
 * nodes.
 */
public interface EmbedderFactory {
    /**
     * Returns the appropriate embedder that can embed a {@link ExpImageNode} in an EPUB document.
     *
     * @param node image input node
     * @return appropriate embedder for input node
     */
    Embedder get(ExpImageNode node);
}
