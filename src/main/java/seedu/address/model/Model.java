package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.studyspot.StudySpot;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<StudySpot> PREDICATE_SHOW_ALL_STUDYSPOTS = unused -> true;

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
     * Returns the user prefs' study tracker file path.
     */
    Path getStudyTrackerFilePath();

    /**
     * Sets the user prefs' study tracker file path.
     */
    void setStudyTrackerFilePath(Path addressBookFilePath);

    /**
     * Replaces study tracker data with the data in {@code addressBook}.
     */
    void setStudyTracker(ReadOnlyStudyTracker studyTracker);

    /** Returns the StudyTracker */
    ReadOnlyStudyTracker getStudyTracker();

    /**
     * Returns true if a study spot with the same identity as {@code studySpot} exists in the study tracker.
     */
    boolean hasStudySpot(StudySpot studySpot);

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

    /** Returns an unmodifiable view of the filtered study spot list */
    ObservableList<StudySpot> getFilteredStudySpotList();

    /**
     * Updates the filter of the filtered study spot list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudySpotList(Predicate<StudySpot> predicate);
}
