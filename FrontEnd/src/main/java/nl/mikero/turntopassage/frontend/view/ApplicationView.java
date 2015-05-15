package nl.mikero.turntopassage.frontend.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import nl.mikero.turntopassage.frontend.control.DropFileChooser;

import java.io.IOException;

public class ApplicationView {

    public Parent getView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application.fxml"));

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Could not load Application.fxml", e);
        }
    }

}
