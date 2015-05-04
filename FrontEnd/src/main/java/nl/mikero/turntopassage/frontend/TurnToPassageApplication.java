package nl.mikero.turntopassage.frontend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.stage.Stage;
import nl.mikero.turntopassage.frontend.inject.TwineModule;

/**
 * Bootstraps and starts up the JavaFX application.
 */
public class TurnToPassageApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new TwineModule());

        // set global stylesheet
        StyleManager.getInstance().addUserAgentStylesheet("Application.css");

        // start application
        ApplicationController instance = injector.getInstance(ApplicationController.class);
        instance.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
