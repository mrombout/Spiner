package nl.mikero.turntopassage.frontend.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import javax.swing.plaf.FileChooserUI;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class DropFileChooser extends BorderPane {

    private FileChooser fileChooser;

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

    @FXML
    private void onStackPaneMouseClicked(MouseEvent mouseEvent) {
        fileChooser.showOpenDialog(this.getScene().getWindow());
    }

}
