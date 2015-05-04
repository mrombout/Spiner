package nl.mikero.turntopassage.frontend.wizard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

/**
 * Abstract wizard pane that loads a FXML, sets it as the main content and
 * registers itself as the controller.
 */
abstract class AbstractFxmlWizardPane extends AbstractWizardPane {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFxmlWizardPane.class);

    /**
     * Constructs a new AbstractFxmlWizardPane loading the given fxml file.
     *
     * @param fxml fxml file to load and use
     */
    protected AbstractFxmlWizardPane(URL fxml) {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(fxml);

            final Parent root = loader.load();
            setContent(root);
        } catch (IOException e) {
            LOGGER.error("Could not load FXML file '{}'.", fxml, e);
        }
    }
}
