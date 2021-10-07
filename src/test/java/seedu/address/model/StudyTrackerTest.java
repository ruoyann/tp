package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_QUIET;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.studyspot.exceptions.DuplicateStudySpotException;
import seedu.address.testutil.StudySpotBuilder;

public class StudyTrackerTest {

    private final StudyTracker studyTracker = new StudyTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), studyTracker.getStudySpotList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studyTracker.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        StudyTracker newData = getTypicalStudyTracker();
        studyTracker.resetData(newData);
        assertEquals(newData, studyTracker);
    }

    @Test
    public void resetData_withDuplicateStudySpots_throwsDuplicateStudySpotException() {
        // Two study spots with the same identity fields
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS)
                .withAddress(VALID_ADDRESS_DECK).withTags(VALID_TAG_QUIET)
                .build();
        List<StudySpot> newStudySpots = Arrays.asList(STARBUCKS, editedStarbucks);
        StudyTrackerStub newData = new StudyTrackerStub(newStudySpots);

        assertThrows(DuplicateStudySpotException.class, () -> studyTracker.resetData(newData));
    }

    @Test
    public void hasStudySpot_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studyTracker.hasStudySpot(null));
    }

    @Test
    public void hasStudySpot_studySpotNotInAddressBook_returnsFalse() {
        assertFalse(studyTracker.hasStudySpot(STARBUCKS));
    }

    @Test
    public void hasStudySpot_studySpotInAddressBook_returnsTrue() {
        studyTracker.addStudySpot(STARBUCKS);
        assertTrue(studyTracker.hasStudySpot(STARBUCKS));
    }

    @Test
    public void hasStudySpot_studySpotWithSameIdentityFieldsInAddressBook_returnsTrue() {
        studyTracker.addStudySpot(STARBUCKS);
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS)
                .withAddress(VALID_ADDRESS_DECK).withTags(VALID_TAG_QUIET)
                .build();
        assertTrue(studyTracker.hasStudySpot(editedStarbucks));
    }

    @Test
    public void getStudySpotList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> studyTracker.getStudySpotList().remove(0));
    }

    /**
     * A stub ReadOnlyStudyTracker whose study spots list can violate interface constraints.
     */
    private static class StudyTrackerStub implements ReadOnlyStudyTracker {
        private final ObservableList<StudySpot> studySpots = FXCollections.observableArrayList();

        StudyTrackerStub(Collection<StudySpot> studySpots) {
            this.studySpots.setAll(studySpots);
        }

        @Override
        public ObservableList<StudySpot> getStudySpotList() {
            return studySpots;
        }

        //TODO
        @Override
        public ObservableList<StudySpot> getFavouriteStudySpotList() {
            throw new AssertionError("This method should not be called.");
        }
    }

}
