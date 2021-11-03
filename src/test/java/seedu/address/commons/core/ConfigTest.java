package seedu.address.commons.core;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConfigTest {

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void getSetLog_validLog_success() {
        Config c = new Config();

        c.setLogLevel(Level.INFO);
        assertEquals(Level.INFO, c.getLogLevel());
        assertNotEquals(Level.ALL, c.getLogLevel());
    }

    @Test
    public void getSetPath_validPath_success() {
        Config c = new Config();

        c.setUserPrefsFilePath(Paths.get("preferences.json"));
        assertEquals(Paths.get("preferences.json"), c.getUserPrefsFilePath());
        assertNotEquals(Paths.get("unknown.json"), c.getUserPrefsFilePath());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertEquals(defaultConfig, defaultConfig);
        assertNotEquals(defaultConfig, null);
    }

    @Test
    public void hashCode_test() {
        Config c = new Config();
        Config another = new Config();
        Config different = new Config();

        c.setLogLevel(Level.INFO);
        another.setLogLevel(Level.INFO);
        different.setLogLevel(Level.ALL);

        assertEquals(another.hashCode(), c.hashCode());
        assertNotEquals(different.hashCode(), c.hashCode());
    }

}
