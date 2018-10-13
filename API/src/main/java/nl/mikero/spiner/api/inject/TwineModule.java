package nl.mikero.spiner.api.inject;

import nl.mikero.spiner.core.inject.AbstractTwineModule;

/**
 * Configures Google Guice dependency injection framework.
 */
public class TwineModule extends AbstractTwineModule {
    @Override
    protected final void configure() {
        super.configure();
        bind(TwineModule.class).toInstance(this);
    }
}
