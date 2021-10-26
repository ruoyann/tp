package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudySpot;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAlias(Alias aliasToAdd) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeAlias(Alias aliasToRemove) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAlias(Alias alias) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getStudyTrackerFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudyTrackerFilePath(Path studyTrackerFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addStudySpot(StudySpot studySpot) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudyTracker(ReadOnlyStudyTracker newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyStudyTracker getStudyTracker() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasStudySpot(StudySpot studySpot) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public StudySpot findStudySpot(Name name) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudySpot(StudySpot target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setStudySpot(StudySpot target, StudySpot editedStudySpot) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean isFavouriteStudySpot(StudySpot studySpot) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public StudySpot addStudySpotToFavourites(StudySpot studySpot) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public StudySpot removeStudySpotFromFavourites(StudySpot studySpot) {
        throw new AssertionError("This method should not be called.");

    }

    @Override
    public ObservableList<StudySpot> getFilteredStudySpotList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<StudySpot> getFavouriteStudySpotList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredStudySpotList(Predicate<StudySpot> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<StudySpot> getFullList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<StudySpot> getTopFiveStudySpotList() {
        throw new AssertionError("This method should not be called.");
    }
}
