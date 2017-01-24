package nl.mikero.spiner.commandline.inject;

import com.google.inject.multibindings.Multibinder;
import nl.mikero.spiner.commandline.goal.*;
import nl.mikero.spiner.core.inject.BaseTwineModule;

/**
 * Configures Google Guice dependency injection framework.
 */
public class TwineModule extends BaseTwineModule {
    @Override
    protected void configure() {
        super.configure();

        Multibinder<Goal> binder = Multibinder.newSetBinder(binder(), Goal.class);
        binder.addBinding().to(HelpGoal.class);
        binder.addBinding().to(VersionGoal.class);
        binder.addBinding().to(TransformGoal.class);
        binder.addBinding().to(WatchGoal.class);
    }
}
