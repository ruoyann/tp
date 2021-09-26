package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.ALICE;
import static seedu.address.testutil.TypicalStudySpots.getTypicalAddressBook;

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
        StudyTracker newData = getTypicalAddressBook();
        studyTracker.resetData(newData);
        assertEquals(newData, studyTracker);
    }

    @Test
    public void resetData_withDuplicateStudySpots_throwsDuplicateStudySpotException() {
        // Two study spots with the same identity fields
        StudySpot editedAlice = new StudySpotBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<StudySpot> newStudySpots = Arrays.asList(ALICE, editedAlice);
        StudyTrackerStub newData = new StudyTrackerStub(newStudySpots);

        assertThrows(DuplicateStudySpotException.class, () -> studyTracker.resetData(newData));
    }

    @Test
    public void hasStudySpot_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> studyTracker.hasStudySpot(null));
    }

    @Test
    public void hasStudySpot_personNotInAddressBook_returnsFalse() {
        assertFalse(studyTracker.hasStudySpot(ALICE));
    }

    @Test
    public void hasStudySpot_personInAddressBook_returnsTrue() {
        studyTracker.addStudySpot(ALICE);
        assertTrue(studyTracker.hasStudySpot(ALICE));
    }

    @Test
    public void hasStudySpot_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        studyTracker.addStudySpot(ALICE);
        StudySpot editedAlice = new StudySpotBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(studyTracker.hasStudySpot(editedAlice));
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
    }

}
