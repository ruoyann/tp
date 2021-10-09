package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.studyspot.StudySpot;

/**
 * Represents the in-memory model of the study tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StudyTracker studyTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<StudySpot> filteredStudySpots;

    /**
     * Initializes a ModelManager with the given studyTracker and userPrefs.
     */
    public ModelManager(ReadOnlyStudyTracker studyTracker, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(studyTracker, userPrefs);

        logger.fine("Initializing with study tracker: " + studyTracker + " and user prefs " + userPrefs);

        this.studyTracker = new StudyTracker(studyTracker);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudySpots = new FilteredList<>(this.studyTracker.getStudySpotList());
    }

    public ModelManager() {
        this(new StudyTracker(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getStudyTrackerFilePath() {
        return userPrefs.getStudyTrackerFilePath();
    }

    @Override
    public void setStudyTrackerFilePath(Path studyTrackerFilePath) {
        requireNonNull(studyTrackerFilePath);
        userPrefs.setStudyTrackerFilePath(studyTrackerFilePath);
    }

    //=========== StudyTracker ================================================================================

    @Override
    public void setStudyTracker(ReadOnlyStudyTracker studyTracker) {
        this.studyTracker.resetData(studyTracker);
    }

    @Override
    public ReadOnlyStudyTracker getStudyTracker() {
        return studyTracker;
    }

    @Override
    public boolean hasStudySpot(StudySpot studySpot) {
        requireNonNull(studySpot);
        return studyTracker.hasStudySpot(studySpot);
    }

    @Override
    public void deleteStudySpot(StudySpot target) {
        studyTracker.removeStudySpot(target);
    }

    @Override
    public void addStudySpot(StudySpot studySpot) {
        studyTracker.addStudySpot(studySpot);
        if (studySpot.isFavourite()) {
            addStudySpotToFavourites(studySpot);
        }
        updateFilteredStudySpotList(PREDICATE_SHOW_ALL_STUDYSPOTS);
    }

    @Override
    public void setStudySpot(StudySpot target, StudySpot editedStudySpot) {
        requireAllNonNull(target, editedStudySpot);

        studyTracker.setStudySpot(target, editedStudySpot);
    }

    /**
     * Returns true if a study spot with the same identity as {@code studySpot} is a favourite in the study tracker.
     *
     * @param studySpot
     */
    @Override
    public boolean isFavouriteStudySpot(StudySpot studySpot) {
        return studyTracker.isFavouriteStudySpot(studySpot);
    }

    /**
     * Adds the given study spot to favourites.
     * {@code study spot} must already exist in the study tracker.
     *
     * @param studySpot
     */
    @Override
    public StudySpot addStudySpotToFavourites(StudySpot studySpot) {
        StudySpot favStudySpot = studyTracker.addStudySpotToFavourites(studySpot);
        return favStudySpot;
    }

    /**
     * Removes the given study spot from favourites.
     * {@code study spot} must already exist in the study tracker.
     *
     * @param studySpot
     */
    @Override
    public StudySpot removeStudySpotFromFavourites(StudySpot studySpot) {
        StudySpot unfavStudySpot = studyTracker.removeStudySpotFromFavourites(studySpot);
        return unfavStudySpot;
    }

    //=========== Filtered StudySpot List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code StudySpot} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<StudySpot> getFilteredStudySpotList() {
        return filteredStudySpots;
    }

    @Override
    public void updateFilteredStudySpotList(Predicate<StudySpot> predicate) {
        requireNonNull(predicate);
        filteredStudySpots.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return studyTracker.equals(other.studyTracker)
                && userPrefs.equals(other.userPrefs)
                && filteredStudySpots.equals(other.filteredStudySpots);
    }

    @Override
    public ObservableList<StudySpot> getFullList() {
        return studyTracker.getStudySpotList();
    }

}
