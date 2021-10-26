package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.alias.Alias;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudySpot;

/**
 * Represents the in-memory model of the study tracker data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StudyTracker studyTracker;
    private final UserPrefs userPrefs;
    private final FilteredList<StudySpot> filteredStudySpots;
    private final FilteredList<StudySpot> favouriteStudySpots;

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
        favouriteStudySpots = new FilteredList<>(this.studyTracker.getFavouriteStudySpotList());
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

    @Override
    public void addAlias(Alias aliasToAdd) {
        List<Alias> aliases = userPrefs.getUserAliases();

        // if alias already defined, replace command word with incoming aliasToAdd
        aliases.removeIf(al -> al.userAlias.equals(aliasToAdd.getUserAlias()));

        aliases.add(aliasToAdd);
        userPrefs.setUserAliases(aliases);
    }

    @Override
    public void removeAlias(Alias aliasToRemove) {
        List<Alias> aliases = userPrefs.getUserAliases();

        // if alias already defined, replace command word with incoming aliasToAdd
        // otherwise, do nothing
        aliases.removeIf(al -> al.userAlias.equals(aliasToRemove.getUserAlias()));

        userPrefs.setUserAliases(aliases);
    }


    @Override
    public boolean hasAlias(Alias alias) {
        requireNonNull(alias);
        List<Alias> aliases = userPrefs.getUserAliases();

        return aliases.stream().anyMatch(a -> a.isSameAlias(alias));
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

    /**
     * Returns StudySpot with the specified {@code Name} in the study tracker.
     * Otherwise, returns null.
     *
     * @param name
     */
    @Override
    public StudySpot findStudySpot(Name name) {
        return studyTracker.findStudySpot(name);
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
     */
    @Override
    public StudySpot removeStudySpotFromFavourites(StudySpot studySpot) {
        StudySpot unfavStudySpot = studyTracker.removeStudySpotFromFavourites(studySpot);
        return unfavStudySpot;
    }

    //=========== Filtered StudySpot List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code StudySpot} backed by the internal list of
     * {@code versionedStudyTracker}
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

    /**
     * Returns an unmodifiable view of the list of the top 5 Studied {@code StudySpot}
     */
    @Override
    public ObservableList<StudySpot> getTopFiveStudySpotList() {
        int numOfSpots = 0;
        ObservableList<StudySpot> result = FXCollections.observableArrayList();
        Comparator<StudySpot> comparator = (spotA, spotB) -> {
            int spotAStudiedHours = spotA.getStudiedHours().getHours();
            int spotBStudiedHours = spotB.getStudiedHours().getHours();
            return spotBStudiedHours - spotAStudiedHours;
        };

        //Priority queue based off studied hours, greater the studied hours the greater priority
        PriorityQueue<StudySpot> queue = new PriorityQueue<>(5, comparator);
        ObservableList<StudySpot> fullList = studyTracker.getStudySpotList();

        //Add all elements from fullList into priority queue
        queue.addAll(fullList);

        while (!queue.isEmpty()) {
            if (numOfSpots == 5) {
                break;
            }
            result.add(queue.remove());
            numOfSpots++;
        }
        return result;
    }

    //=========== Favourite StudySpots ===============================================================================

    /**
     * Returns an unmodifiable view of the list of favourite {@code StudySpot} backed by the internal list of
     * {@code versionedStudyTracker}
     */
    @Override
    public ObservableList<StudySpot> getFavouriteStudySpotList() {
        return favouriteStudySpots;
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
