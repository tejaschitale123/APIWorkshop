package api.workshop.advanced;

import static java.lang.System.getProperty;
import static java.text.MessageFormat.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class ConfigHelper {
    private static final Properties PROPERTIES = new Properties ();

    static {
        final String configPath = format ("{0}/src/test/resources/config.properties", getProperty ("user.dir"));
        final File configFile = new File (configPath);
        try (final FileInputStream in = new FileInputStream (configFile)) {
            PROPERTIES.load (in);
        } catch (final IOException e) {
            e.printStackTrace ();
        }
    }

    public static String getConfigValue (final String key) {
        final String value = PROPERTIES.getProperty (key);
        if (StringUtils.isEmpty (value)) {
            final String message = format ("{0} key is not set as env vars.", key);
            return Objects.requireNonNull (System.getenv (key), message);
        }
        return PROPERTIES.getProperty (key);
    }

    private ConfigHelper () {
        // Helper class.
    }
}