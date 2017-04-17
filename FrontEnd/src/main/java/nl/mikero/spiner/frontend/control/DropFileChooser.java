package nl.mikero.spiner.frontend.control;

import java.io.File;
import java.io.IOException;

import nl.mikero.spiner.frontend.MessagesBundle;
import nl.mikero.spiner.frontend.exception.FxmlLoadFailedException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

/**
 * A file chooser control that allows opening a native file chooser or accepts dropping in files.
 */
public class DropFileChooser extends BorderPane {
    private final ObjectProperty<File> fileProperty = new SimpleObjectProperty<>();

    private final FileChooser fileChooser;

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
        super();

        loadFxml();

        fileChooser = new FileChooser();
    }

    /**
     * Loads the FXML view.
     */
    private void loadFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/control/drop_file_chooser.fxml"));
        fxmlLoader.setResources(MessagesBundle.getBundle());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new FxmlLoadFailedException("drop_file_chooser.fxml", e);
        }
    }

    /**
     * Initializes the control.
     */
    @FXML
    protected final void initialize() {
        statusIndicator.setVisible(false);
        statusImage.setVisible(true);
    }

    /**
     * Returns the fileChoosers extension filters.
     *
     * @return fileChoosers extension filters
     */
    public final ObservableList<FileChooser.ExtensionFilter> getExtensionFilters() {
        return fileChooser.getExtensionFilters();
    }

    /**
     * Executed when the control is clicked.
     *
     * Opens the file chooser.
     */
    @FXML
    private void onStackPaneMouseClicked() {
        openFileChooser();
    }

    /**
     * Open and show the native file chooser.
     */
    public final void openFileChooser() {
        File file = fileChooser.showOpenDialog(this.getScene().getWindow());
        if(file != null) {
            setFile(file);
            fileLabel.setText(getFile().getAbsolutePath());
            fileChooser.setInitialDirectory(getFile().getParentFile());
        }
    }

    /**
     * Property for the currently selected file. May be empty when no file is selected.
     *
     * @return property for the currently selected file, may be empty when no file is selected.
     */
    public final ObjectProperty<File> fileProperty() {
        return fileProperty;
    }

    /**
     * Returns the selected file.
     *
     * @return selected file
     */
    public final File getFile() {
        return fileProperty.get();
    }

    /**
     * Sets the selected file.
     *
     * @param file selected file
     */
    public final void setFile(final File file) {
        fileProperty.set(file);
    }

    /**
     * Sets this file chooser to start state.
     */
    public final void startProgress() {
        statusImage.setVisible(false);
        statusIndicator.setVisible(true);
        statusIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
    }

    /**
     * Sets this file chooser to complete state.
     */
    public final void completeProgress() {
        statusImage.setVisible(false);
        statusIndicator.setVisible(true);
        statusIndicator.setProgress(1);
    }

    /**
     * Sets this file choose to stop state.
     */
    public final void stopProgress() {
        statusImage.setVisible(true);
        statusIndicator.setVisible(false);
        statusIndicator.setProgress(0);
    }
}
