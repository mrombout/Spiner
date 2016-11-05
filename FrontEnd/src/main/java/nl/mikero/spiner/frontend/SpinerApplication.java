package nl.mikero.spiner.frontend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import nl.mikero.spiner.frontend.inject.TwineModule;
import nl.mikero.spiner.frontend.main.ApplicationView;

/**
 * Bootstraps and starts up the JavaFX application.
 */
public class SpinerApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new TwineModule());
        ApplicationView applicationView = injector.getInstance(ApplicationView.class);

        Scene scene = new Scene(applicationView.getView());

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.setTitle("Spiner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Launches the Spiner GUI application.
     *
     * @param args options arguments, currently not accepting any
     */
    public static void main(String[] args) {
        launch(args);
    }
}
