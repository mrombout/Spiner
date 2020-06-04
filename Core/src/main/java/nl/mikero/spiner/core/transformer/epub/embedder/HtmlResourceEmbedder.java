package nl.mikero.spiner.core.transformer.epub.embedder;

import nl.siegmann.epublib.domain.Book;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Embeds all `&lt;img /&gt;` resources into the given EPUB and rewrites all `src` attributes of them to points to the
 * embedded image.
 */
public class HtmlResourceEmbedder implements ResourceEmbedder {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlResourceEmbedder.class);

    private final Parser htmlParser;
    private final EmbedderFactory embedderFactory;

    /**
     * Constructs a new HtmlResourceEmbedder.
     *
     * @param embedderFactory factory used to create embedder for each file
     * @param htmlParser parser used to parse passage content
     */
    @Inject
    public HtmlResourceEmbedder(final EmbedderFactory embedderFactory, final Parser htmlParser) {
        this.embedderFactory = embedderFactory;
        this.htmlParser = htmlParser;
    }

    @Override
    public String embed(Book book, String content) {
        book = Objects.requireNonNull(book);
        content = Objects.requireNonNull(content);

        Document document = htmlParser.parseInput(content, "");

        Elements images = document.getElementsByTag("img");
        for (Element img : images) {
            String imgSrcAttribute = img.attr("src");

            try {
                URL urlFromString = new URL(imgSrcAttribute);

                Embedder embedder = embedderFactory.get(urlFromString);
                embedder.embed(book, urlFromString);

                img.attr("src", embedder.getHref(urlFromString));
            } catch (MalformedURLException e) {
                LOGGER.warn("Error creating uri from '{}'", imgSrcAttribute, e);
            } catch (IOException e) {
                LOGGER.error("Error during embedding of '{}'", imgSrcAttribute, e);
            }
        }

        return document.toString();
    }
}
