package nl.mikero.spiner.commandline;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import nl.mikero.spiner.core.inject.AbstractTwineModule;

/**
 * Configures Google Guice dependency injection framework.
 */
public class CommandLineModule extends AbstractTwineModule {
    @Override
    protected void configure() {
        super.configure();

        bind(VersionService.class).to(GradleVersionService.class);

        install(new FactoryModuleBuilder().build(CommandFactory.class));
    }
}
