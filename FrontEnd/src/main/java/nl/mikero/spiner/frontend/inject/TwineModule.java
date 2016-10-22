package nl.mikero.spiner.frontend.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import nl.mikero.spiner.core.TwineArchiveRepairer;
import nl.mikero.spiner.core.TwinePublishedRepairer;
import nl.mikero.spiner.core.TwineRepairer;
import nl.mikero.spiner.core.embedder.EmbedderFactory;
import nl.mikero.spiner.core.embedder.HashEmbedderFactory;
import nl.mikero.spiner.core.embedder.ImageEmbedder;
import nl.mikero.spiner.core.transformer.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.inject.ArchiveRepairer;
import nl.mikero.spiner.core.inject.PublishedRepairer;
import nl.mikero.spiner.core.pegdown.plugin.TwineLinkParser;
import nl.mikero.spiner.core.pegdown.plugin.TwineLinkSerializer;
import org.apache.commons.codec.digest.DigestUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.plugins.PegDownPlugins;

public class TwineModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(TwineModule.class).toInstance(this);

        bind(TwineStoryEpubTransformer.class);
        bind(TwineRepairer.class).annotatedWith(ArchiveRepairer.class).to(TwineArchiveRepairer.class);
        bind(TwineRepairer.class).annotatedWith(PublishedRepairer.class).to(TwinePublishedRepairer.class);

        mapViews();
        mapMediators();
        mapCommands();
        mapServices();
        mapInfrastructure();
    }

    private void mapViews() {

    }

    private void mapMediators() {

    }

    private void mapCommands() {

    }

    private void mapServices() {

    }

    private void mapInfrastructure() {

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
