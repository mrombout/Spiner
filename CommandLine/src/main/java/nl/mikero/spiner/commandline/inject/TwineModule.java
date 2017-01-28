package nl.mikero.spiner.commandline.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import nl.mikero.spiner.commandline.factory.CommandFactory;
import nl.mikero.spiner.core.inject.BaseTwineModule;

/**
 * Configures Google Guice dependency injection framework.
 */
public class TwineModule extends BaseTwineModule {
    @Override
    protected void configure() {
        super.configure();

        install(new FactoryModuleBuilder()
            .build(CommandFactory.class));
    }
}
