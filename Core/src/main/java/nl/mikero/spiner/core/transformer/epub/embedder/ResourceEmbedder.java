package nl.mikero.spiner.core.transformer.epub.embedder;

import com.google.inject.Inject;
import nl.siegmann.epublib.domain.Book;
import org.pegdown.ast.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

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
    public void visit(final RootNode node) {
        for (ReferenceNode refNode : node.getReferences()) {
            visitChildren(refNode);
        }
        for (AbbreviationNode abbrNode : node.getAbbreviations()) {
            visitChildren(abbrNode);
        }
        visitChildren(node);
    }

    @Override
    public void visit(final AbbreviationNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final BlockQuoteNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final BulletListNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final DefinitionListNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final DefinitionNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final DefinitionTermNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final ExpImageNode node) {
        try {
            Embedder embedder = factory.get(node);
            embedder.embed(this.book, createUrlFromString(node.url));
        } catch (IOException e) {
            LOGGER.error("Error during embedding of '{}'", node.url, e);
        }

        visitChildren(node);
    }

    private URL createUrlFromString(final String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.error("Error creating url '{}'", url, e);
        }

        return null;
    }

    @Override
    public void visit(final ExpLinkNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final HeaderNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final ListItemNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final OrderedListNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final ParaNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final QuotedNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final ReferenceNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final RefImageNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final RefLinkNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final StrikeNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final StrongEmphSuperNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final TableBodyNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final TableCaptionNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final TableCellNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final TableColumnNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final TableHeaderNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final TableNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final TableRowNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final SuperNode node) {
        visitChildren(node);
    }

    protected void visitChildren(SuperNode node) {
        for (Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(final AnchorLinkNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final AutoLinkNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final CodeNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final HtmlBlockNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final InlineHtmlNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final MailLinkNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final SimpleNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final SpecialTextNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final VerbatimNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final WikiLinkNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final TextNode node) { /* can't contain embeddedable resources */ }
    @Override
    public void visit(final Node node) { /* can't contain embeddedable resources */ }
}
