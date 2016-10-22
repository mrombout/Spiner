package nl.mikero.spiner.frontend.control;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class DropFileChooser extends BorderPane {

    private FileChooser fileChooser;

    private ObjectProperty<File> fileProperty = new SimpleObjectProperty<File>();

    @FXML
    private ImageView statusImage;
    @FXML
    private Label fileLabel;
    private Image image;

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
            throw new RuntimeException("Could not load drop_file_chooser.fxml.", e);
        }
    }

    private void createFileChooser() {
        fileChooser = new FileChooser();
    }

    public ObservableList<FileChooser.ExtensionFilter> getExtensionFilters() {
        return fileChooser.getExtensionFilters();
    }

    @FXML
    private void onStackPaneMouseClicked(MouseEvent mouseEvent) {
        openFileChooser();
    }

    public void openFileChooser() {
        File file;
        if((file = fileChooser.showOpenDialog(this.getScene().getWindow())) != null) {
            setFile(file);
            fileLabel.setText(getFile().getAbsolutePath());
            fileChooser.setInitialDirectory(getFile().getParentFile());
        }
    }

    public ObjectProperty<File> fileProperty() {
        return fileProperty;
    }

    public File getFile() {
        return fileProperty.get();
    }

    public void setFile(File file) {
        fileProperty.set(file);
    }

    public void setImage(Image image) {
        statusImage.setImage(image);
    }
    public Image getImage() { return statusImage.getImage(); }
}
