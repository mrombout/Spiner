package nl.mikero.spiner.frontend.main;

import java.io.*;
import java.util.Optional;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.google.inject.Inject;
import nl.mikero.spiner.core.exception.TwineRepairFailedException;
import nl.mikero.spiner.core.exception.TwineTransformationFailedException;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.latex.LatexTransformer;
import nl.mikero.spiner.frontend.SpinerApplication;
import nl.mikero.spiner.frontend.dialog.ExceptionDialog;
import nl.mikero.spiner.frontend.TransformTask;
import nl.mikero.spiner.frontend.control.DropFileChooser;
import nl.mikero.spiner.frontend.exception.FxmlLoadFailedException;
import nl.mikero.spiner.frontend.io.FileInputOutputStream;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main Application GUI.
 */
public class ApplicationView {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationView.class);

    private static final String LOG_MSG_TRANSFORM_FAIL = "Could not transform document.";

    private final Alert errorAlert;

    private double xOffset;
    private double yOffset;

    @Inject
    private TransformService transformService;
    @Inject
    private TwineStoryEpubTransformer epubTransformer;
    @Inject
    private LatexTransformer latexTransformer;

    @FXML
    private BorderPane applicationView;
    @FXML
    private DropFileChooser dropFileChooser;
    @FXML
    private Button transformButton;
    @FXML
    private ToggleButton epubFormatButton;
    @FXML
    private ToggleButton latexFormatButton;

    @FXML
    private HBox header;

    private SpinerApplication application;
    private Stage primaryStage;

    /**
     * Constructs a new ApplicatieView.
     *
     * @param application application
     * @param stage parent stage
     */
    public ApplicationView(final SpinerApplication application, final Stage stage) {
        this.errorAlert = new Alert(Alert.AlertType.ERROR);

        this.application = application;
        this.primaryStage = stage;
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

        header.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        header.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    @FXML
    protected void onCloseButtonClicked(final ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    protected void onHelpButtonClicked(final ActionEvent actionEvent) {
        application.getHostServices().showDocument("https://spiner.readme.io");
    }

    @FXML
    protected void onTransformButtonClicked(final ActionEvent actionEvent) {
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

        try {
            TransformTask task = createTransformTask(inputFile, outputFile);
            new Thread(task).start();
        } catch (IOException e) {
            LOGGER.error(LOG_MSG_TRANSFORM_FAIL, e);
            handleException(e, inputFile);
        }
    }

    private TransformTask createTransformTask(final File inputFile, final File outputFile) throws IOException {
        FileInputOutputStream finout = new FileInputOutputStream(inputFile, outputFile);

        TransformTask task = new TransformTask(transformService, getTransformer(), finout.getInputStream(), finout.getOutputStream());
        task.stateProperty().addListener((observable, oldState, newState) -> {
            if(newState.equals(Worker.State.SUCCEEDED))
                dropFileChooser.completeProgress();

            if(newState.equals(Worker.State.SUCCEEDED) || newState.equals(Worker.State.FAILED)) {
                try {
                    finout.close();
                } catch (IOException e) {
                    throw new TwineTransformationFailedException(LOG_MSG_TRANSFORM_FAIL, e);
                }
            }
        });
        task.exceptionProperty().addListener((observable, oldException, newException) -> {
            LOGGER.error(LOG_MSG_TRANSFORM_FAIL, newException);
            handleException(newException, inputFile);
        });

        return task;
    }

    private void handleException(final Throwable throwable, final File inputFile) {
        dropFileChooser.stopProgress();

        Throwable actualThrowable = throwable;
        if(throwable instanceof TwineTransformationFailedException)
            actualThrowable = throwable.getCause();

        if(actualThrowable instanceof FileNotFoundException) {
            String title = String.format("File '%s' could not be found.", inputFile.toString());
            String content = title + " Do you want to select a different file and try again?";

            showErrorAndRetry(title, content);
        } else if(actualThrowable instanceof TwineRepairFailedException) {
            String title = String.format("File '%s' could not be repaired.", inputFile.toString());
            String content = title + " The file might be in a format that Spiner does not understand. Do you want to select a different file and try again?";

            showErrorAndRetry(title, content);
        } else if(actualThrowable instanceof IOException) {
            ExceptionDialog exceptionDialog = new ExceptionDialog(actualThrowable);
            exceptionDialog.showAndWait();
        }
    }

    private Transformer getTransformer() {
        return epubFormatButton.isSelected() ? epubTransformer : latexTransformer;
    }

    private void showErrorAndRetry(final String title, final String content) {
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

    private String getOutputPath(final String path) {
        return String.format("%s.%s", FilenameUtils.removeExtension(path), getTransformer().getExtension());
    }

    public void setPrimaryStage(final Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
