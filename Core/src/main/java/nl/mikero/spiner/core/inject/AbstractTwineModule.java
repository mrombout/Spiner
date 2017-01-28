package nl.mikero.spiner.core.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import nl.mikero.spiner.core.pegdown.plugin.TwineLinkParser;
import nl.mikero.spiner.core.pegdown.plugin.TwineLinkRenderer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.epub.embedder.EmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.HashEmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.ImageEmbedder;
import nl.mikero.spiner.core.transformer.epub.embedder.ResourceEmbedder;
import nl.mikero.spiner.core.transformer.epub.pegdown.TwineLinkSerializer;
import nl.mikero.spiner.core.transformer.latex.pegdown.ToLatexSerializer;
import nl.mikero.spiner.core.twine.TwineArchiveRepairer;
import nl.mikero.spiner.core.twine.TwinePublishedRepairer;
import nl.mikero.spiner.core.twine.TwineRepairer;
import org.apache.commons.codec.digest.DigestUtils;
import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.Printer;
import org.pegdown.plugins.PegDownPlugins;

/**
 * Configures Guice.
 */
public abstract class AbstractTwineModule extends AbstractModule {
    @Override
    protected final void configure() {
        bind(TwineRepairer.class).annotatedWith(ArchiveRepairer.class).to(TwineArchiveRepairer.class);
        bind(TwineRepairer.class).annotatedWith(PublishedRepairer.class).to(TwinePublishedRepairer.class);
    }

    /**
     * Provides an {@link EmbedderFactory}.
     *
     * @return an embedder factory
     */
    @Provides
    public final EmbedderFactory provideEmbedderFactoy() {
        return new HashEmbedderFactory(DigestUtils.getSha256Digest());
    }

    /**
     * Provides an {@link ImageEmbedder}.
     *
     * @return an image embedder
     */
    @Provides
    public final ImageEmbedder provideImageEmbedder() {
        return new ImageEmbedder(DigestUtils.getSha256Digest());
    }

    /**
     * Provides a PegDownProcessor.
     *
     * @param twineLinkSerializer a twine link serializer
     * @return a pregdown processor
     */
    @Provides
    public final PegDownProcessor providePegDownProcessor(final TwineLinkSerializer twineLinkSerializer) {
        return new PegDownProcessor(Extensions.WIKILINKS, PegDownPlugins.builder()
                .withPlugin(TwineLinkParser.class)
                .withHtmlSerializer(twineLinkSerializer)
                .build()
        );
    }

    /**
     * Provides a {@link TwineStoryEpubTransformer}.
     *
     * @param pegDownProcessor a pegdown processor
     * @param twineLinkRenderer a twine link renderer
     * @param resourceEmbedder a resource embedder
     * @return a twine story epub transformer
     */
    @Provides
    public final TwineStoryEpubTransformer provideTwineStoryEpubTransformer(
            final PegDownProcessor pegDownProcessor,
            final TwineLinkRenderer twineLinkRenderer,
            final ResourceEmbedder resourceEmbedder) {
        return new TwineStoryEpubTransformer(pegDownProcessor, twineLinkRenderer, resourceEmbedder);
    }

    /**
     * Provides a LaTeX serializer.
     *
     * @return a LaTeX serializer
     */
    @Provides
    public final ToLatexSerializer provideToLatexSerializer() {
        return new ToLatexSerializer(new LinkRenderer(), new Printer());
    }
}
