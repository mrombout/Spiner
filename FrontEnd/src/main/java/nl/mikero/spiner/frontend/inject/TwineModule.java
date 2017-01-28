package nl.mikero.spiner.frontend.inject;

import nl.mikero.spiner.core.inject.BaseTwineModule;

/**
 * Configures Google Guice dependency injection framework.
 */
public class TwineModule extends BaseTwineModule {
    @Override
    protected void configure() {
        super.configure();
        bind(TwineModule.class).toInstance(this);
    }
}
