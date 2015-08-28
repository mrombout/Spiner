package nl.mikero.turntopassage.frontend.view;

import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import nl.mikero.turntopassage.core.service.TwineService;
import nl.mikero.turntopassage.frontend.control.DropFileChooser;
import org.apache.commons.io.FilenameUtils;

import java.io.*;

public class ApplicationView {

    private final TwineService twineService;

    private static final Image fileImage = new Image("/file.png");
    private static final Image progressImage = new Image("/progress.png");
    private static final Image doneImage = new Image("/done.png");

    @FXML
    private DropFileChooser dropFileChooser;
    @FXML
    private Button transformButton;

    @Inject
    public ApplicationView(TwineService twineService) {
        this.twineService = twineService;
    }

    public Parent getView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Application.fxml"));
        fxmlLoader.setController(this);

        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Could not load Application.fxml", e);
        }
    }

    @FXML
    public void initialize() {
        dropFileChooser.setImage(fileImage);

        dropFileChooser.fileProperty().addListener(new ChangeListener<File>() {
            @Override
            public void changed(ObservableValue<? extends File> observable, File oldValue, File newValue) {
                if(newValue != null) {
                    transformButton.setDisable(false);
                }
            }
        });
    }

    public void onTransformButtonClicked(ActionEvent actionEvent) {
        final File inputFile = dropFileChooser.getFile();
        final String inputFilename = inputFile.getAbsolutePath();
        final String outputFilename = FilenameUtils.removeExtension(inputFilename) + ".epub";
        try(InputStream in = new BufferedInputStream(new FileInputStream(inputFile)); OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outputFilename)))) {
            dropFileChooser.setImage(progressImage);
            twineService.transform(in, out);
            dropFileChooser.setImage(doneImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
