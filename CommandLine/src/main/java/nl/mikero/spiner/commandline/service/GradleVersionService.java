package nl.mikero.spiner.commandline.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GradleVersionService implements VersionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GradleVersionService.class);

    public String get() {
        Properties prop = new Properties();

        try(InputStream input = GradleVersionService.class.getClassLoader().getResourceAsStream("version.properties");) {
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            return prop.getProperty("version", "UNKNOWN");
        } catch (IOException e) {
            LOGGER.error("Couldn't read version from version.properties.", e);
            return "UNKNOWN";
        }
    }
}
