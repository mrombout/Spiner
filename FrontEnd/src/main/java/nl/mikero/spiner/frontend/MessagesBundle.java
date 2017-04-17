package nl.mikero.spiner.frontend;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides convenience methods for translated messages.
 */
public class MessagesBundle {
    public static final String MSG_APP_TITLE = getString("msg.application_title");

    public static final String MSG_DOC_WEBSITE = getString("msg.website");
    public static final String MSG_FILE_ALREADY_EXISTS = getString("msg.file_already_exists");
    public static final String MSG_FILES_ALREADY_EXISTS = getString("msg.the_file_already_exists");
    public static final String MSG_DO_YOU_WANT_TO_OVERWRITE = getString("msg.overwrite_this_file");
    public static final String MSG_FILE_NOT_FOUND = getString("msg.file_could_not_be_found");
    public static final String MSG_SELECT_DIFFERENT_FILE = getString("msg.select_a_different_file");
    public static final String MSG_FILE_COULD_NOT_BE_REPAIRED = getString("msg.file_could_not_be_repaired");
    public static final String MSG_FILE_FORMAT_NOT_RECOGNIZED = getString("msg.file_format_not_recognized");

    public static final String MSG_UNEXPECTED_ERROR = getString("msg.unexpected_error");
    public static final String MSG_THE_STACKTRACE_WAS = getString("msg.the_stacktrace_was");

    public static final String MSG_COULD_NOT_LOAD_FXML_FILE = "msg.could_not_load_fxml_file";

    /**
     * Returns the MessagesBundle resource bundle for the default locale.
     *
     * @return resource bundle for default locale
     */
    public static ResourceBundle getBundle() {
        return getBundle(Locale.getDefault());
    }

    /**
     * Returns the MessagesBundle resource bundle for the given locale.
     *
     * @param locale locale to get bundle for
     * @return resource bundle for locale
     */
    private static ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("MessagesBundle", locale);
    }

    /**
     * Returns a translated message string using the users default locale.
     *
     * @param key key to get translated message for
     * @return translated message string for key
     */
    public static String getString(String key) {
        return getBundle().getString(key);
    }

    /**
     * Returns an English message string using the en_US locale.
     *
     * This method is mainly used to provide English messages for exceptions
     * which also provide a localized message.
     *
     * @param key key to get English message for
     * @return English message for key
     */
    public static String getEnglishString(String key) {
        return getBundle(new Locale("en", "US")).getString(key);
    }
}
