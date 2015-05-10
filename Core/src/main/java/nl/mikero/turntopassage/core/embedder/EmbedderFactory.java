package nl.mikero.turntopassage.core.embedder;

import org.pegdown.ast.ExpImageNode;

/**
 * Factory for retrieving the embedders for the different types of Pegdown
 * nodes.
 */
public interface EmbedderFactory {
    Embedder get(ExpImageNode node);
}
