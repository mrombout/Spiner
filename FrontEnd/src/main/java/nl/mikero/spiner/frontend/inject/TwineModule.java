package nl.mikero.spiner.frontend.inject;

import nl.mikero.spiner.core.inject.BaseTwineModule;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;

/**
 * Configures Google Guice dependency injection framework.
 */
public class TwineModule extends BaseTwineModule {
    @Override
    protected void configure() {
        super.configure();
        bind(TwineModule.class).toInstance(this);

        bind(TwineStoryEpubTransformer.class);
    }
}
