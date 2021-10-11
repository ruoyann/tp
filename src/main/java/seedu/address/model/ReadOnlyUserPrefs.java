package seedu.address.model;

import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getStudyTrackerFilePath();

    List<Alias> getUserAliases();

}
