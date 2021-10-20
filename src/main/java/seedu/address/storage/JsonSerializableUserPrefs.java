package seedu.address.storage;

import static java.util.Objects.requireNonNullElse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;

/**
 * An Immutable StudyTracker that is serializable to JSON format.
 */
class JsonSerializableUserPrefs {

    public static final String MESSAGE_DUPLICATE_ALIASES = "UserPrefs list contains duplicate alias(es).";

    private GuiSettings guiSettings;
    private Path studyTrackerFilePath;
    private final List<JsonAdaptedAlias> aliases = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUserPrefs} with the given GuiSettings, StudyTrackerFilePath and userAliases.
     *
     * If GuiSettings, studyTrackerFilePath, or aliases not present in JSON file,
     * use the defaults.
     */
    @JsonCreator
    public JsonSerializableUserPrefs(@JsonProperty("guiSettings") GuiSettings guiSettings,
                                     @JsonProperty("studyTrackerFilePath") Path studyTrackerFilePath,
                                     @JsonProperty("userAliases") List<JsonAdaptedAlias> aliases) {

        this.guiSettings = requireNonNullElse(guiSettings, new GuiSettings());
        this.studyTrackerFilePath = requireNonNullElse(studyTrackerFilePath, Paths.get("data", "studytracker.json"));
        this.aliases.addAll(requireNonNullElse(aliases, UserPrefs.DEFAULT_PROGRAM_ALIASES.stream()
                .map(JsonAdaptedAlias::new).collect(Collectors.toList())));
    }

    /**
     * Converts a given {@code ReadOnlyUserPrefs} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUserPrefs}.
     */
    public JsonSerializableUserPrefs(ReadOnlyUserPrefs source) {
        guiSettings = requireNonNullElse(guiSettings, new GuiSettings());
        studyTrackerFilePath = requireNonNullElse(studyTrackerFilePath, Paths.get("data", "studytracker.json"));
        aliases.addAll(source.getUserAliases().stream()
                .map(JsonAdaptedAlias::new).collect(Collectors.toList()));
    }

    /**
     * Converts this userPrefs into the model's {@code UserPrefs} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserPrefs toModelType() throws IllegalValueException {
        UserPrefs userPrefs = new UserPrefs();
        List<Alias> aliases = new ArrayList<>();

        for (JsonAdaptedAlias alias : this.aliases) {
            if (aliases.removeIf(al -> al.userAlias.equals(alias.getUserAlias()))) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ALIASES);
            }
            aliases.add(alias.toModelType());
        }
        userPrefs.setGuiSettings(guiSettings);
        userPrefs.setStudyTrackerFilePath(studyTrackerFilePath);
        userPrefs.setUserAliases(aliases);
        return userPrefs;
    }

}
