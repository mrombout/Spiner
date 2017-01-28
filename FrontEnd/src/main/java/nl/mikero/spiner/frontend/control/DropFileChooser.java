package nl.mikero.spiner.frontend.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import nl.mikero.spiner.frontend.exception.FxmlLoadFailedException;

import java.io.File;
import java.io.IOException;

/**
 * A file chooser control that allows opening a native file chooser or accepts dropping in files.
 */
public class DropFileChooser extends BorderPane {

    private FileChooser fileChooser;

    private final ObjectProperty<File> fileProperty = new SimpleObjectProperty<>();

    @FXML
    private ProgressIndicator statusIndicator;
    @FXML
    private ImageView statusImage;
    @FXML
    private Label fileLabel;

    /**
     * Constructs a new DropFileChooser.
     */
    public DropFileChooser() {
        loadFxml();
        createFileChooser();
    }

    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/control/drop_file_chooser.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new FxmlLoadFailedException("drop_file_chooser.fxml", e);
        }
    }

    @FXML
    protected void initialize() {
        statusIndicator.setVisible(false);
        statusImage.setVisible(true);
    }

    private void createFileChooser() {
        fileChooser = new FileChooser();
    }

    public ObservableList<FileChooser.ExtensionFilter> getExtensionFilters() {
        return fileChooser.getExtensionFilters();
    }

    @FXML
    private void onStackPaneMouseClicked(final MouseEvent mouseEvent) {
        openFileChooser();
    }

    /**
     * Open and show the native file chooser.
     */
    public void openFileChooser() {
        File file;
        if((file = fileChooser.showOpenDialog(this.getScene().getWindow())) != null) {
            setFile(file);
            fileLabel.setText(getFile().getAbsolutePath());
            fileChooser.setInitialDirectory(getFile().getParentFile());
        }
    }

    /**
     * Property for the currently selected file. May be empty when no file is selected.
     *
     * @return propert for the currently selected file, may be empty when no file is selected.
     */
    public ObjectProperty<File> fileProperty() {
        return fileProperty;
    }

    public File getFile() {
        return fileProperty.get();
    }

    public void setFile(File file) {
        fileProperty.set(file);
    }

    public void startProgress() {
        statusImage.setVisible(false);
        statusIndicator.setVisible(true);
        statusIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }

    public void completeProgress() {
        statusImage.setVisible(false);
        statusIndicator.setVisible(true);
        statusIndicator.setProgress(1);
    }

    public void stopProgress() {
        statusImage.setVisible(true);
        statusIndicator.setVisible(false);
        statusIndicator.setProgress(0);
    }
}
