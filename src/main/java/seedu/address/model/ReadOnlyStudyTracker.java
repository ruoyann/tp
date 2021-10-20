package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.studyspot.StudySpot;

/**
 * Unmodifiable view of an study tracker
 */
public interface ReadOnlyStudyTracker {

    /**
     * Returns an unmodifiable view of the study spots list.
     * This list will not contain any duplicate study spots.
     */
    ObservableList<StudySpot> getStudySpotList();

    /**
     * Returns an unmodifiable view of the favourite study spots list.
     * This list will not contain any duplicate study spots.
     */
    ObservableList<StudySpot> getFavouriteStudySpotList();
}
