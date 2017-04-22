package nl.mikero.spiner.frontend.dialog;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import static nl.mikero.spiner.frontend.FrontEndMessagesBundle.MSG_THE_STACKTRACE_WAS;
import static nl.mikero.spiner.frontend.FrontEndMessagesBundle.MSG_UNEXPECTED_ERROR;

/**
 * Dialog that renders an exception.
 */
public class ExceptionDialog extends Alert {
    /**
     * Constructs a new ExceptionDialog.
     *
     * @param exception exception to render information of
     */
    public ExceptionDialog(final Throwable exception) {
        super(AlertType.ERROR);

        setTitle(exception.getClass().getSimpleName());
        setHeaderText(MessageFormat.format(MSG_UNEXPECTED_ERROR, exception.getClass().getSimpleName()));
        setContentText(exception.getMessage());

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        exception.printStackTrace(printWriter);

        String exceptionText = stringWriter.toString();

        Label label = new Label(MSG_THE_STACKTRACE_WAS);

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        getDialogPane().setExpandableContent(expContent);
    }
}
