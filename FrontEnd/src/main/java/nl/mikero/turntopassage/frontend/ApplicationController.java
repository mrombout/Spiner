package nl.mikero.turntopassage.frontend;

import com.google.inject.Inject;
import javafx.collections.ObservableMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import nl.mikero.turntopassage.core.service.TransformOptions;
import nl.mikero.turntopassage.core.service.TwineService;
import nl.mikero.turntopassage.frontend.wizard.ConfirmWizardPane;
import nl.mikero.turntopassage.frontend.wizard.InputOutputWizardPane;
import nl.mikero.turntopassage.frontend.wizard.MetadataWizardPane;
import nl.siegmann.epublib.domain.Author;
import nl.siegmann.epublib.domain.Date;
import nl.siegmann.epublib.domain.Identifier;
import nl.siegmann.epublib.domain.Metadata;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.dialog.Wizard.LinearFlow;
import org.controlsfx.dialog.WizardPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        wizard.setReadSettings(false);

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

                Metadata metadata = new Metadata();
                if(settings.containsKey("author")) {
                    metadata.addAuthor(new Author((String) settings.get("author")));
                }
                if(settings.containsKey("contributor")) {
                    metadata.addContributor(new Author((String) settings.get("contributor")));
                }
                if(settings.containsKey("date")) {
                    metadata.addDate(new Date(((LocalDate) settings.get("date")).toString()));
                }
                if(settings.containsKey("language")) {
                    metadata.setLanguage((String) settings.get("language"));
                }
                if(settings.containsKey("rights")) {
                    metadata.setRights(Collections.singletonList((String) settings.get("rights")));
                }
                if(settings.containsKey("title")) {
                    metadata.addTitle((String) settings.get("title"));
                }
                if(settings.containsKey("subject")) {
                    metadata.setSubjects(Collections.singletonList((String) settings.get("subject")));
                }
                if(settings.containsKey("format")) {
                    metadata.setFormat((String) settings.get("format"));
                }
                if(settings.containsKey("type")) {
                    metadata.setTypes(Collections.singletonList((String) settings.get("type")));
                }
                if(settings.containsKey("description")) {
                    metadata.setDescriptions(Collections.singletonList((String) settings.get("description")));
                }
                if(settings.containsKey("publisher")) {
                    metadata.setPublishers(Collections.singletonList((String) settings.get("publisher")));
                }
                TransformOptions transformOptions = new TransformOptions(metadata);

                try {
                    File inputFile = new File(String.valueOf(settings.get("input")));
                    File outputFile = new File(String.valueOf(settings.get("output")));
                    InputStream inputStream = new FileInputStream(inputFile);
                    OutputStream outputStream = new FileOutputStream(outputFile);

                    twineService.transform(inputStream, outputStream, transformOptions);
                } catch (FileNotFoundException e) {
                    LOGGER.error("Could not transform file.", e);
                    errorAlert.setContentText(e.getMessage());
                }
            }
        });
    }
}
