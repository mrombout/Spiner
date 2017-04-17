package nl.mikero.spiner.frontend;

import com.google.inject.Guice;
import com.google.inject.Injector;
import nl.mikero.spiner.frontend.inject.TwineModule;
import nl.mikero.spiner.frontend.main.ApplicationView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Locale;
import java.util.ResourceBundle;

import static nl.mikero.spiner.frontend.MessagesBundle.MSG_APP_TITLE;

/**
 * Bootstraps and starts up the JavaFX application.
 */
public class SpinerApplication extends Application {
    private static final String ICON_APPLICATION = "/icon.png";

    @Override
    public final void start(final Stage primaryStage) {
        final Injector injector = Guice.createInjector(new TwineModule());

        final ApplicationView applicationView = new ApplicationView(this, primaryStage);
        injector.injectMembers(applicationView);

        final Scene scene = new Scene(applicationView.getView());

        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(ICON_APPLICATION)));
        primaryStage.setTitle(MSG_APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Launches the Spiner GUI application.
     *
     * @param args options arguments, currently not accepting any
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
