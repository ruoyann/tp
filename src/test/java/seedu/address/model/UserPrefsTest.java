package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setGuiSettings(null));
    }

    @Test
    public void setStudyTrackerFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setStudyTrackerFilePath(null));
    }

    @Test
    public void setUserAliases_nullAliases_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setUserAliases(null));
    }

    @Test
    public void equals() {
        UserPrefs userPrefs = new UserPrefs(); // default values
        UserPrefs changedGuiSettings = new UserPrefs();
        changedGuiSettings.setGuiSettings(
                new GuiSettings(2103, 2103, 21, 22, "default"));
        UserPrefs changedFilePath = new UserPrefs();
        changedFilePath.setStudyTrackerFilePath(Paths.get("hi"));
        UserPrefs changedAliasList = new UserPrefs();
        List<Alias> aliasList = Arrays.asList(
                new Alias("ls", "list"),
                new Alias("pwd", "list")
        );
        changedAliasList.setUserAliases(aliasList);

        // same object -> true
        assertTrue(userPrefs.equals(userPrefs));

        // null -> false
        assertFalse(userPrefs.equals(null));

        // wrong type -> false
        assertFalse(userPrefs.equals(new Alias("ls", "list")));

        // different GuiSettings -> false
        assertFalse(userPrefs.equals(changedGuiSettings));

        // different filePath -> false
        assertFalse(userPrefs.equals(changedFilePath));

        // different aliasList -> false
        assertFalse(userPrefs.equals(changedAliasList));
    }

    @Test
    void hashCode_test() {
        UserPrefs userPrefs = new UserPrefs(); // default values
        UserPrefs changedGuiSettings = new UserPrefs();
        changedGuiSettings.setGuiSettings(
                new GuiSettings(2103, 2103, 21, 22, "dark"));
        UserPrefs changedFilePath = new UserPrefs();
        changedFilePath.setStudyTrackerFilePath(Paths.get("hi"));
        UserPrefs changedAliasList = new UserPrefs();
        List<Alias> aliasList = Arrays.asList(
                new Alias("ls", "list"),
                new Alias("pwd", "list")
        );
        changedAliasList.setUserAliases(aliasList);

        assertEquals(userPrefs.hashCode(), userPrefs.hashCode());
        assertNotEquals(userPrefs.hashCode(), changedGuiSettings.hashCode());
        assertNotEquals(userPrefs.hashCode(), changedFilePath.hashCode());
        assertNotEquals(userPrefs.hashCode(), changedAliasList.hashCode());
    }

}
