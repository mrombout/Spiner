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
import nl.siegmann.epublib.domain.Book;
//import org.pegdown.ast.AbbreviationNode;
//import org.pegdown.ast.AnchorLinkNode;
//import org.pegdown.ast.AutoLinkNode;
//import org.pegdown.ast.BlockQuoteNode;
//import org.pegdown.ast.BulletListNode;
//import org.pegdown.ast.CodeNode;
//import org.pegdown.ast.DefinitionListNode;
//import org.pegdown.ast.DefinitionNode;
//import org.pegdown.ast.DefinitionTermNode;
//import org.pegdown.ast.ExpImageNode;
//import org.pegdown.ast.ExpLinkNode;
//import org.pegdown.ast.HeaderNode;
//import org.pegdown.ast.HtmlBlockNode;
//import org.pegdown.ast.InlineHtmlNode;
//import org.pegdown.ast.ListItemNode;
//import org.pegdown.ast.MailLinkNode;
//import org.pegdown.ast.Node;
//import org.pegdown.ast.OrderedListNode;
//import org.pegdown.ast.ParaNode;
//import org.pegdown.ast.QuotedNode;
//import org.pegdown.ast.RefImageNode;
//import org.pegdown.ast.RefLinkNode;
//import org.pegdown.ast.ReferenceNode;
//import org.pegdown.ast.RootNode;
//import org.pegdown.ast.SimpleNode;
//import org.pegdown.ast.SpecialTextNode;
//import org.pegdown.ast.StrikeNode;
//import org.pegdown.ast.StrongEmphSuperNode;
//import org.pegdown.ast.SuperNode;
//import org.pegdown.ast.TableBodyNode;
//import org.pegdown.ast.TableCaptionNode;
//import org.pegdown.ast.TableCellNode;
//import org.pegdown.ast.TableColumnNode;
//import org.pegdown.ast.TableHeaderNode;
//import org.pegdown.ast.TableNode;
//import org.pegdown.ast.TableRowNode;
//import org.pegdown.ast.TextNode;
//import org.pegdown.ast.VerbatimNode;
//import org.pegdown.ast.Visitor;
//import org.pegdown.ast.WikiLinkNode;
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

    private NodeVisitor visitor = new NodeVisitor(
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

    public void visit(Image image) {
        try {
            Embedder embedder = factory.get(image);
            embedder.embed(this.book, createUrlFromString(image.getUrl().unescape())); // .toString() works?
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
