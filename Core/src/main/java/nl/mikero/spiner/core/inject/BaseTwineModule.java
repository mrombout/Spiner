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
import nl.mikero.spiner.core.twine.TwineArchiveRepairer;
import nl.mikero.spiner.core.twine.TwinePublishedRepairer;
import nl.mikero.spiner.core.twine.TwineRepairer;
import org.apache.commons.codec.digest.DigestUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.plugins.PegDownPlugins;

public abstract class BaseTwineModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TwineRepairer.class).annotatedWith(ArchiveRepairer.class).to(TwineArchiveRepairer.class);
        bind(TwineRepairer.class).annotatedWith(PublishedRepairer.class).to(TwinePublishedRepairer.class);
    }

    @Provides
    public EmbedderFactory provideEmbedderFactoy() {
        return new HashEmbedderFactory(DigestUtils.getSha256Digest());
    }

    @Provides
    public ImageEmbedder provideImageEmbedder() {
        return new ImageEmbedder(DigestUtils.getSha256Digest());
    }

    @Provides
    public PegDownProcessor providePegDownProcessor(TwineLinkSerializer twineLinkSerializer) {
        return new PegDownProcessor(Extensions.WIKILINKS, PegDownPlugins.builder()
                .withPlugin(TwineLinkParser.class)
                .withHtmlSerializer(twineLinkSerializer)
                .build()
        );
    }

    @Provides
    public TwineStoryEpubTransformer provideTwineStoryEpubTransformer(PegDownProcessor pegDownProcessor, TwineLinkRenderer twineLinkRenderer, ResourceEmbedder resourceEmbedder) {
        return new TwineStoryEpubTransformer(pegDownProcessor, twineLinkRenderer, resourceEmbedder);
    }
}
