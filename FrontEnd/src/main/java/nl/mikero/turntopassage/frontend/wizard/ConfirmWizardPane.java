package nl.mikero.turntopassage.frontend.wizard;

import javafx.collections.ObservableMap;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.controlsfx.dialog.Wizard;

import java.util.Map.Entry;

/**
 * Shows all wizard settings that allows the user to review the current
 * settings before transforming the EPUB.
 */
public class ConfirmWizardPane extends AbstractWizardPane {
    private final GridPane gridPane;

    /**
     * Constructs a new ConfirmWizardPane.
     */
    public ConfirmWizardPane() {
        setHeaderTitle("Confirm");
        setHeaderText("Is everything correct?");

        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPrefWidth(640);

        setContent(gridPane);
    }

    /**
     * Reads all settings from the wizard and creates labels for each key/value
     * pair.
     *
     * @param wizard the wizard this panel is active on
     */
    @Override
    public void onEnteringPage(Wizard wizard) {
        gridPane.getChildren().clear();

        final ObservableMap<String, Object> settings = wizard.getSettings();
        int rowNum = 0;
        for(Entry<String, Object> entry : settings.entrySet()) {
            Label keyLabel = new Label(entry.getKey());
            keyLabel.getStyleClass().add("key-label");

            Label valueLabel = new Label(String.valueOf(entry.getValue()));

            GridPane.setHgrow(valueLabel, Priority.ALWAYS);
            gridPane.addRow(rowNum++, keyLabel, valueLabel);
        }
    }
}
