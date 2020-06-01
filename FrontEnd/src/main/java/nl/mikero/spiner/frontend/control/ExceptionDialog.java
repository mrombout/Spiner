package nl.mikero.spiner.frontend.control;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.common.base.Strings;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Dialog that renders an exception.
 */
public class ExceptionDialog extends Alert {

    public ExceptionDialog(final Throwable exception) {
        this(exception, null, null, null);
    }

    /**
     * Constructs a new ExceptionDialog.
     *
     * @param exception exception to render information of
     */
    public ExceptionDialog(final Throwable exception, final String title, final String headerText, final String contentText) {
        super(AlertType.ERROR);

        if (title != null && !title.isEmpty()) {
            setTitle(title);
        } else {
            setTitle(exception.getClass().getSimpleName());
        }

        getDialogPane().setMinHeight(Region.USE_PREF_SIZE); // show all text for log message, instead of overflowing with ellipsis
        if (headerText != null && !headerText.isEmpty()) {
            setHeaderText(headerText);
        } else {
            setHeaderText("An unexpected error occurred: " + exception.getClass().getSimpleName());
        }

        if (contentText != null && !contentText.isEmpty()) {
            setContentText(contentText);
        } else {
            setContentText(exception.getMessage());
        }

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        exception.printStackTrace(printWriter);

        String exceptionText = stringWriter.toString();

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(false);
        textArea.setStyle("-fx-font-family: 'monospaced';");

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        Label label = new Label("The exception stacktrace was:");

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        getDialogPane().setExpandableContent(expContent);
    }
}
