package nl.mikero.spiner.core.transformer.epub.embedder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import com.google.inject.Inject;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import nl.siegmann.epublib.domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runs through all nodes in a pegdown document and embeds any resource that
 * needs embedding.
 */
public class ResourceEmbedder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceEmbedder.class);

    private final EmbedderFactory factory;

    private Book book;

    private final NodeVisitor visitor = new NodeVisitor(
            new VisitHandler<>(Image.class, this::visit)
    );

    /**
     * Constructs a new ResourceEmbedder.
     *
     * @param factory factory used to create embedders
     */
    @Inject
    public ResourceEmbedder(final EmbedderFactory factory) {
        this.factory = factory;
    }

    public void visit(final Image image) {
        try {
            Embedder embedder = factory.get(image);

            URL urlFromString = createUrlFromString(image.getUrl().unescape());
            if (urlFromString != null) {
                embedder.embed(this.book, urlFromString);
                image.setUrl(BasedSequenceImpl.of(embedder.getHref(urlFromString)));
            }
        } catch (IOException e) {
            LOGGER.error("Error during embedding of '{}'", image.getUrl().unescape(), e);
        }

        visitor.visitChildren(image);
    }

    /**
     * Runs through all the children of the given root node and embeds any
     * nodes that are embeddable.
     *
     * @param book book to embed resources in
     * @param rootNode root node to run through
     * @see Embedder
     */
    public void embed(final Book book, final Node rootNode) {
        Objects.requireNonNull(book);
        Objects.requireNonNull(rootNode);

        this.book = book;
        visitor.visit(rootNode);
        this.book = null;
    }

    /**
     * Creates an URL from a string.
     *
     * @param url string to create url from
     * @return url from string
     */
    private URL createUrlFromString(final String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.error("Error creating url '{}'", url, e);
        }

        return null;
    }
}
