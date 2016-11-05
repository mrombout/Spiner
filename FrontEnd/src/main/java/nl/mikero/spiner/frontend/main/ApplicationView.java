package nl.mikero.spiner.frontend.main;

import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import nl.mikero.spiner.core.exception.TwineRepairFailedException;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.epub.EpubTransformer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.latex.LatexTransformer;
import nl.mikero.spiner.frontend.dialog.ExceptionDialog;
import nl.mikero.spiner.frontend.TransformTask;
import nl.mikero.spiner.frontend.control.DropFileChooser;
import nl.mikero.spiner.frontend.exception.FxmlLoadFailedException;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.Optional;

/**
 * Main Application GUI.
 */
public class ApplicationView {
    private static final Image fileImage = new Image("/file.png");

    private final Alert errorAlert;

    @Inject
    private TransformService transformService;
    @Inject
    private TwineStoryEpubTransformer epubTransformer;
    @Inject
    private LatexTransformer latexTransformer;

    @FXML
    private BorderPane application;
    @FXML
    private DropFileChooser dropFileChooser;
    @FXML
    private Button transformButton;
    @FXML
    private ToggleButton epubFormatButton;
    @FXML
    private ToggleButton latexFormatButton;

    /**
     * Constructs a new ApplicationView.
     */
    public ApplicationView() {
        this.errorAlert = new Alert(Alert.AlertType.ERROR);
    }

    public Parent getView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application.fxml"));
        fxmlLoader.setController(this);

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new FxmlLoadFailedException("Application.fxml", e);
        }
    }

    @FXML
    protected void initialize() {
        dropFileChooser.setImage(fileImage);

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("HTML Files (*.html, *.htm, *.xhtml)", "*.html", "*.htm", "*.xhtml");
        dropFileChooser.getExtensionFilters().add(extensionFilter);

        transformButton.setDisable(true);
        dropFileChooser.fileProperty().addListener((observable, oldValue, newValue) -> transformButton.setDisable(false));

        epubFormatButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue && !newValue) {
                latexFormatButton.setSelected(true);
                latexFormatButton.requestFocus();
            }
        });
        latexFormatButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue && !newValue) {
                epubFormatButton.setSelected(true);
                epubFormatButton.requestFocus();
            }
        });
    }

    @FXML
    protected void onTransformButtonClicked(ActionEvent actionEvent) {
        final File inputFile = dropFileChooser.getFile();
        final File outputFile = new File(getOutputPath(inputFile.getAbsolutePath()));

        if(outputFile.exists()) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("File already exists.");
            confirm.setHeaderText(String.format("The file '%s' already exists.", outputFile.getAbsoluteFile()));
            confirm.setContentText("Do you want to overwrite this file?");
            Optional<ButtonType> result = confirm.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.CANCEL) {
                dropFileChooser.openFileChooser();
                return;
            }
        }

        dropFileChooser.startProgress();
        TransformTask task = new TransformTask(transformService, getTransformer(), inputFile, outputFile);
        new Thread(task).start();
        task.stateProperty().addListener((observable, oldState, newState) -> {
            if(newState.equals(Worker.State.SUCCEEDED))
                dropFileChooser.completeProgress();
        });
        task.exceptionProperty().addListener((observable, oldException, newException) -> {
            if(newException instanceof FileNotFoundException) {
                String title = String.format("File '%s' could not be found.", inputFile.toString());
                String content = title + " Do you want to select a different file and try again?";

                showErrorAndRetry(title, content);
            } else if(newException instanceof TwineRepairFailedException) {
                String title = String.format("File '%s' could not be repaired.", inputFile.toString());
                String content = title + " The file might be in a format that Spiner does not understand. Do you want to select a different file and try again?";

                showErrorAndRetry(title, content);
            } else if(newException instanceof IOException) {
                ExceptionDialog exceptionDialog = new ExceptionDialog(newException);
                exceptionDialog.showAndWait();
            }
        });
    }

    private Transformer getTransformer() {
        return epubFormatButton.isSelected() ? epubTransformer : latexTransformer;
    }

    private void showErrorAndRetry(String title, String content) {
        errorAlert.setAlertType(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(content);
        errorAlert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = errorAlert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            dropFileChooser.openFileChooser();
        }
    }

    private String getOutputPath(String path) {
        return String.format("%s.%s", FilenameUtils.removeExtension(path), getTransformer().getExtension());
    }
}
