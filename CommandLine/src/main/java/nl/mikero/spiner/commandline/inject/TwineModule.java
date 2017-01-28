package nl.mikero.spiner.commandline.inject;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import nl.mikero.spiner.commandline.factory.CommandFactory;
import nl.mikero.spiner.commandline.service.GradleVersionService;
import nl.mikero.spiner.commandline.service.VersionService;
import nl.mikero.spiner.core.inject.AbstractTwineModule;

/**
 * Configures Google Guice dependency injection framework.
 */
public class TwineModule extends AbstractTwineModule {
    @Override
    protected void configure() {
        super.configure();

        bind(VersionService.class).to(GradleVersionService.class);

        install(new FactoryModuleBuilder().build(CommandFactory.class));
    }
}
