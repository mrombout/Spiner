package nl.mikero.turntopassage.frontend.wizard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.dialog.Wizard;

/**
 * Abstract wizard pane that replaces the standard header with one that has
 * adds a title text.
 */
abstract class AbstractWizardPane extends Wizard.WizardPane {

    private final StringProperty headerTitle = new SimpleStringProperty();

    /**
     * Constructs a new AbstractWizardPane.
     */
    protected AbstractWizardPane() {
        this.setHeader(createHeaderPanel());
        this.setGraphic(new ImageView(new Image(AbstractFxmlWizardPane.class.getResource("/icon.png").toString())));
    }

    /**
     * Creates a new header panel that is bound to this wizard panes
     * properties.
     *
     * @return created grid pane
     */
    private GridPane createHeaderPanel() {
        GridPane headerGridPane = new GridPane();
        headerGridPane.getStyleClass().add("header-panel");

        // left side - text
        VBox headerTextVBox = new VBox();
        GridPane.setHgrow(headerTextVBox, Priority.ALWAYS);

        Label headerTitle = new Label(getHeaderTitle());
        headerTitle.getStyleClass().add("header-title");
        headerTitle.textProperty().bind(headerTitleProperty());
        headerTitle.setWrapText(true);
        headerTitle.setAlignment(Pos.CENTER_LEFT);
        headerTextVBox.getChildren().add(headerTitle);

        Label headerLabel = new Label(getHeaderText());
        headerLabel.textProperty().bind(headerTextProperty());
        headerLabel.setWrapText(true);
        headerLabel.setAlignment(Pos.CENTER_LEFT);
        headerTextVBox.getChildren().add(headerLabel);

        headerGridPane.add(headerTextVBox, 0, 0);

        // right side - graphic
        StackPane graphicPane = new StackPane();
        graphicPane.getStyleClass().add("graphic-container");

        Node graphic = getGraphic();
        if(graphic != null) {
            graphicPane.getChildren().add(getGraphic());
        }

        graphicProperty().addListener(observable -> {
            graphicPane.getChildren().clear();
            graphicPane.getChildren().add(getGraphic());
        });

        headerGridPane.add(graphicPane, 1, 0);

        return headerGridPane;
    }

    public String getHeaderTitle() {
        return headerTitle.get();
    }
    public void setHeaderTitle(String headerTitle) {
        this.headerTitle.set(headerTitle);
    }

    public StringProperty headerTitleProperty() {
        return headerTitle;
    }
}
