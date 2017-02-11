package nl.mikero.spiner.core.transformer.epub.embedder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import com.google.inject.Inject;
import nl.siegmann.epublib.domain.Book;
import org.pegdown.ast.AbbreviationNode;
import org.pegdown.ast.AnchorLinkNode;
import org.pegdown.ast.AutoLinkNode;
import org.pegdown.ast.BlockQuoteNode;
import org.pegdown.ast.BulletListNode;
import org.pegdown.ast.CodeNode;
import org.pegdown.ast.DefinitionListNode;
import org.pegdown.ast.DefinitionNode;
import org.pegdown.ast.DefinitionTermNode;
import org.pegdown.ast.ExpImageNode;
import org.pegdown.ast.ExpLinkNode;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.HtmlBlockNode;
import org.pegdown.ast.InlineHtmlNode;
import org.pegdown.ast.ListItemNode;
import org.pegdown.ast.MailLinkNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.OrderedListNode;
import org.pegdown.ast.ParaNode;
import org.pegdown.ast.QuotedNode;
import org.pegdown.ast.RefImageNode;
import org.pegdown.ast.RefLinkNode;
import org.pegdown.ast.ReferenceNode;
import org.pegdown.ast.RootNode;
import org.pegdown.ast.SimpleNode;
import org.pegdown.ast.SpecialTextNode;
import org.pegdown.ast.StrikeNode;
import org.pegdown.ast.StrongEmphSuperNode;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TableBodyNode;
import org.pegdown.ast.TableCaptionNode;
import org.pegdown.ast.TableCellNode;
import org.pegdown.ast.TableColumnNode;
import org.pegdown.ast.TableHeaderNode;
import org.pegdown.ast.TableNode;
import org.pegdown.ast.TableRowNode;
import org.pegdown.ast.TextNode;
import org.pegdown.ast.VerbatimNode;
import org.pegdown.ast.Visitor;
import org.pegdown.ast.WikiLinkNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Runs through all nodes in a pegdown document and embeds any resource that
 * needs embedding.
 */
public class ResourceEmbedder implements Visitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceEmbedder.class);

    private final EmbedderFactory factory;

    private Book book;

    /**
     * Constructs a new ResourceEmbedder.
     *
     * @param factory factory used to create embedders
     */
    @Inject
    public ResourceEmbedder(final EmbedderFactory factory) {
        this.factory = factory;
    }

    /**
     * Runs through all the children of the given root node and embeds any
     * nodes that are embeddable.
     *
     * @param book book to embed resources in
     * @param rootNode root node to run through
     * @see Embedder
     */
    public void embed(final Book book, final RootNode rootNode) {
        Objects.requireNonNull(book);
        Objects.requireNonNull(rootNode);

        this.book = book;
        visit(rootNode);
        this.book = null;
    }

    @Override
    public final void visit(final RootNode node) {
        for (ReferenceNode refNode : node.getReferences()) {
            visitChildren(refNode);
        }
        for (AbbreviationNode abbrNode : node.getAbbreviations()) {
            visitChildren(abbrNode);
        }
        visitChildren(node);
    }

    @Override
    public final void visit(final AbbreviationNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final BlockQuoteNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final BulletListNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final DefinitionListNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final DefinitionNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final DefinitionTermNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final ExpImageNode node) {
        try {
            Embedder embedder = factory.get(node);
            embedder.embed(this.book, createUrlFromString(node.url));
        } catch (IOException e) {
            LOGGER.error("Error during embedding of '{}'", node.url, e);
        }

        visitChildren(node);
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

    @Override
    public final void visit(final ExpLinkNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final HeaderNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final ListItemNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final OrderedListNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final ParaNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final QuotedNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final ReferenceNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final RefImageNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final RefLinkNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final StrikeNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final StrongEmphSuperNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final TableBodyNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final TableCaptionNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final TableCellNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final TableColumnNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final TableHeaderNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final TableNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final TableRowNode node) {
        visitChildren(node);
    }

    @Override
    public final void visit(final SuperNode node) {
        visitChildren(node);
    }

    /**
     * Visits all children of the given node.
     *
     * @param node node to visit all children of
     */
    private void visitChildren(final SuperNode node) {
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public final void visit(final AnchorLinkNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final AutoLinkNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final CodeNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final HtmlBlockNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final InlineHtmlNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final MailLinkNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final SimpleNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final SpecialTextNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final VerbatimNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final WikiLinkNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final TextNode node) { /* can't contain embeddedable resources */ }

    @Override
    public final void visit(final Node node) { /* can't contain embeddedable resources */ }
}
