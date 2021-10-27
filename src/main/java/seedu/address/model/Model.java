package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudySpot;

/**
 * The API of the Model component.
 */
public interface Model {
    Predicate<StudySpot> PREDICATE_SHOW_ALL_STUDYSPOTS = unused -> true; // always evaluate to true
    Predicate<StudySpot> PREDICATE_SHOW_FAVOURITES = StudySpot::isFavourite;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Adds an alias to user prefs.
     */
    void addAlias(Alias aliasToAdd);

    /**
     * Removes an alias from user prefs.
     */
    void removeAlias(Alias aliasToRemove);

    /**
     * Returns true if an Alias with the same {@code userAlias} exists in the userPrefs.
     */
    boolean hasAlias(Alias alias);

    /**
     * Returns the user prefs' study tracker file path.
     */
    Path getStudyTrackerFilePath();

    /**
     * Sets the user prefs' study tracker file path.
     */
    void setStudyTrackerFilePath(Path studyTrackerFilePath);

    /**
     * Replaces study tracker data with the data in {@code studyTracker}.
     */
    void setStudyTracker(ReadOnlyStudyTracker studyTracker);

    /** Returns the StudyTracker */
    ReadOnlyStudyTracker getStudyTracker();

    /**
     * Returns true if a study spot with the same identity as {@code studySpot} exists in the study tracker.
     */
    boolean hasStudySpot(StudySpot studySpot);

    /**
     * Returns StudySpot with the specified {@code Name} in the study tracker.
     * Otherwise, returns null.
     */
    StudySpot findStudySpot(Name name);

    /**
     * Deletes the given study spot.
     * The study spot must exist in the study tracker.
     */
    void deleteStudySpot(StudySpot target);

    /**
     * Adds the given study spot.
     * {@code study spot} must not already exist in the study tracker.
     */
    void addStudySpot(StudySpot studySpot);

    /**
     * Replaces the given study spot {@code target} with {@code editedStudySpot}.
     * {@code target} must exist in the study tracker.
     * The study spot identity of {@code editedStudySpot}
     * must not be the same as another existing study spot in the study tracker.
     */
    void setStudySpot(StudySpot target, StudySpot editedStudySpot);

    /**
     * Returns true if a study spot with the same identity as {@code studySpot} is a favourite in the study tracker.
     */
    public boolean isFavouriteStudySpot(StudySpot studySpot);

    /**
     * Adds the given study spot to favourites.
     * {@code study spot} must already exist in the study tracker.
     *
     * @return Returns updated study spot.
     */
    StudySpot addStudySpotToFavourites(StudySpot studySpot);


    /**
     * Removes the given study spot from favourites.
     * {@code study spot} must already exist in the study tracker.
     *
     * @return Returns updated study spot.
     */
    StudySpot removeStudySpotFromFavourites(StudySpot studySpot);

    /** Returns an unmodifiable view of the filtered study spot list */
    ObservableList<StudySpot> getFilteredStudySpotList();

    /** Returns an unmodifiable view of the favourite study spots list */
    ObservableList<StudySpot> getFavouriteStudySpotList();

    /**
     * Updates the filter of the filtered study spot list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudySpotList(Predicate<StudySpot> predicate);

    /**
     * Returns an unmodifiable view of the non filtered study spot list
     */
    ObservableList<StudySpot> getFullList();

    /**
     * Returns the top 5 {@code StudySpots} based off the studied hours
     */
    ObservableList<StudySpot> getTopFiveStudySpotList();
}
