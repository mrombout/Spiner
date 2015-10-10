package nl.mikero.turntopassage.frontend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.mikero.turntopassage.frontend.inject.TwineModule;
import nl.mikero.turntopassage.frontend.view.ApplicationView;

/**
 * Bootstraps and starts up the JavaFX application.
 */
public class TurnToPassageApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new TwineModule());
        ApplicationView applicationView = injector.getInstance(ApplicationView.class);

        Scene scene = new Scene(applicationView.getView());

        primaryStage.setTitle("TurnToPassage");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
