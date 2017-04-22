package nl.mikero.spiner.core;

import sun.misc.resources.Messages;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides convenience methods for translated messages.
 */
public class ResourceMessagesBundle {
    /**
     * Returns the ResourceMessagesBundle resource bundle for the default locale.
     *
     * @return resource bundle for default locale
     */
    public static ResourceBundle getBundle() {
        return getBundle(Locale.getDefault());
    }

    /**
     * Returns the ResourceMessagesBundle resource bundle for the given locale.
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
        return getBundle(Locale.ENGLISH).getString(key);
    }
}
