package nl.mikero.turntopassage.frontend.wizard;

/**
 * Allows the user to change the metadata to be included in the EPUB.
 */
public class MetadataWizardPane extends AbstractFxmlWizardPane {
    public MetadataWizardPane() {
        super(MetadataWizardPane.class.getResource("/pane/MetadataWizardPane.fxml"));

        setHeaderTitle("General Information");
        setHeaderText("Please provide some general information about your story");
    }
}
