package nl.mikero.turntopassage.frontend.wizard;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.controlsfx.dialog.Wizard;

import java.io.File;

/**
 * Allows the user to set the input and output file for the transformation.
 */
public class InputOutputWizardPane extends AbstractFxmlWizardPane {
    @FXML
    private TextField inputField;
    @FXML
    private TextField outputField;

    /**
     * Constructs a new InputOutputWizardPane.
     */
    public InputOutputWizardPane() {
        super(InputOutputWizardPane.class.getResource("/pane/InputOutputWizardPane.fxml"));

        setHeaderTitle("Input/Output");
        setHeaderText("Please select your input and output location.");
    }

    @Override
    public void onExitingPage(Wizard wizard) {
        ObservableMap<String, Object> settings = wizard.getSettings();

        settings.put("input", inputField.getText());
        settings.put("output", outputField.getText());
    }

    public void onActionBrowseInputButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("HTML file", "*.html"));
        if(!inputField.getText().isEmpty()) {
            fileChooser.setInitialDirectory(new File(inputField.getText()).getParentFile());
        }
        File inputFile = fileChooser.showOpenDialog(getScene().getWindow());
        if(inputFile != null) {
            inputField.setText(inputFile.getAbsolutePath());
        }
    }

    public void onActionBrowseOutputButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("EPUB file", "*.epub"));
        if(!outputField.getText().isEmpty()) {
            fileChooser.setInitialDirectory(new File(outputField.getText()).getParentFile());
        }
        File outputFile = fileChooser.showSaveDialog(getScene().getWindow());
        if(outputFile != null) {
            outputField.setText(outputFile.getAbsolutePath());
        }
    }
}
