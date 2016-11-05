package nl.mikero.spiner.frontend.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import nl.mikero.spiner.core.twine.TwineArchiveRepairer;
import nl.mikero.spiner.core.twine.TwinePublishedRepairer;
import nl.mikero.spiner.core.twine.TwineRepairer;
import nl.mikero.spiner.core.transformer.epub.embedder.EmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.HashEmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.ImageEmbedder;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.inject.ArchiveRepairer;
import nl.mikero.spiner.core.inject.PublishedRepairer;
import nl.mikero.spiner.core.pegdown.plugin.TwineLinkParser;
import nl.mikero.spiner.core.pegdown.plugin.TwineLinkSerializer;
import org.apache.commons.codec.digest.DigestUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.plugins.PegDownPlugins;

/**
 * Configures Google Guice dependency injection framework.
 */
public class TwineModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TwineModule.class).toInstance(this);

        bind(TwineStoryEpubTransformer.class);
        bind(TwineRepairer.class).annotatedWith(ArchiveRepairer.class).to(TwineArchiveRepairer.class);
        bind(TwineRepairer.class).annotatedWith(PublishedRepairer.class).to(TwinePublishedRepairer.class);
    }

    @Provides
    EmbedderFactory provideEmbedderFactoy() {
        return new HashEmbedderFactory(DigestUtils.getSha256Digest());
    }

    @Provides
    ImageEmbedder provideImageEmbedder() {
        return new ImageEmbedder(DigestUtils.getSha256Digest());
    }

    @Provides
    PegDownProcessor providePegDownProcessor(TwineLinkSerializer twineLinkSerializer) {
        return new PegDownProcessor(Extensions.WIKILINKS, PegDownPlugins.builder()
                .withPlugin(TwineLinkParser.class)
                .withHtmlSerializer(twineLinkSerializer)
                .build()
        );
    }

}
