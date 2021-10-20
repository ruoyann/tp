package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    public static final List<Alias> DEFAULT_PROGRAM_ALIASES = Arrays.asList(
            new Alias("ls", "list"),
            new Alias("bye", "exit"),
            new Alias("quit", "exit")
    );

    private GuiSettings guiSettings = new GuiSettings();
    private Path studyTrackerFilePath = Paths.get("data" , "studytracker.json");

    // We want the aliases to be editable, so we need to wrap with ArrayList
    // https://stackoverflow.com/questions/2965747
    private List<Alias> userAliases = new ArrayList<>(DEFAULT_PROGRAM_ALIASES);

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setStudyTrackerFilePath(newUserPrefs.getStudyTrackerFilePath());
        setUserAliases(newUserPrefs.getUserAliases());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getStudyTrackerFilePath() {
        return studyTrackerFilePath;
    }

    public void setStudyTrackerFilePath(Path studyTrackerFilePath) {
        requireNonNull(studyTrackerFilePath);
        this.studyTrackerFilePath = studyTrackerFilePath;
    }

    public void setUserAliases(List<Alias> aliases) {
        requireNonNull(aliases);
        this.userAliases = aliases;
    }

    public List<Alias> getUserAliases() {
        return userAliases;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && studyTrackerFilePath.equals(o.studyTrackerFilePath)
                && userAliases.equals(o.userAliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, studyTrackerFilePath, userAliases);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + studyTrackerFilePath);
        sb.append("\nWith user aliases : " + userAliases);
        return sb.toString();
    }

}
