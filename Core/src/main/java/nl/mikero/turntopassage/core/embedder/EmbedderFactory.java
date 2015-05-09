package nl.mikero.turntopassage.core.embedder;

import org.pegdown.ast.ExpImageNode;

/**
 * Created by Mike on 9/5/2015.
 */
public interface EmbedderFactory {
    Embedder get(ExpImageNode node);
}
