package nl.mikero.turntopassage.frontend;

import com.google.inject.Inject;
import javafx.collections.ObservableMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import nl.mikero.turntopassage.core.service.TwineService;
import nl.mikero.turntopassage.frontend.wizard.ConfirmWizardPane;
import nl.mikero.turntopassage.frontend.wizard.InputOutputWizardPane;
import nl.mikero.turntopassage.frontend.wizard.MetadataWizardPane;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.Wizard.LinearFlow;
import org.controlsfx.dialog.WizardPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Provides a graphical interface to the TurnToPassage.Transformer
 * transformation features.
 */
public class ApplicationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

    private final TwineService twineService;

    private final Wizard wizard;
    private final Alert errorAlert;

    /**
     * Constructs a new ApplicationController using the given twineService.
     *
     * @param twineService twine service to use
     */
    @Inject
    public ApplicationController(TwineService twineService) {
        this.twineService = twineService;

        errorAlert = new Alert(AlertType.ERROR);

        wizard = new Wizard(null, "TurnToPassage.Transform");

        WizardPane page1 = new InputOutputWizardPane();
        WizardPane page2 = new MetadataWizardPane();
        WizardPane page3 = new ConfirmWizardPane();

        wizard.setFlow(new LinearFlow(page1, page2, page3));
    }

    /**
     * Shows the application main wizard window.
     */
    public void show() {
        // show wizard and wait for response
        wizard.showAndWait().ifPresent(result -> {
            if (result == ButtonType.FINISH) {
                ObservableMap<String, Object> settings = wizard.getSettings();

                try {
                    File inputFile = new File(String.valueOf(settings.get("inputField")));
                    File outputFile = new File(String.valueOf(settings.get("outputField")));
                    InputStream inputStream = new FileInputStream(inputFile);
                    OutputStream outputStream = new FileOutputStream(outputFile);

                    twineService.transform(inputStream, outputStream);
                } catch (FileNotFoundException e) {
                    LOGGER.error("Could not transform file.", e);
                    errorAlert.setContentText(e.getMessage());
                }
            }
        });
    }
}
