package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_QUIET;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.DECK;
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
    public void resetData_withValidReadOnlyStudyTracker_replacesData() {
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
    public void hasStudySpot_studySpotNotInStudyTracker_returnsFalse() {
        assertFalse(studyTracker.hasStudySpot(STARBUCKS));
    }

    @Test
    public void hasStudySpot_studySpotInStudyTracker_returnsTrue() {
        studyTracker.addStudySpot(STARBUCKS);
        assertTrue(studyTracker.hasStudySpot(STARBUCKS));
    }

    @Test
    public void hasStudySpot_studySpotWithSameIdentityFieldsInStudyTracker_returnsTrue() {
        studyTracker.addStudySpot(STARBUCKS);
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS)
                .withAddress(VALID_ADDRESS_DECK).withTags(VALID_TAG_QUIET)
                .build();
        assertTrue(studyTracker.hasStudySpot(editedStarbucks));
    }

    @Test
    public void setStudySpotInStudyTracker_replaceWithFavourite_returnsTrue() {
        StudySpot favStarbucks = new StudySpotBuilder(STARBUCKS).withFavourite(true).build();
        studyTracker.addStudySpot(favStarbucks);
        studyTracker.addStudySpotToFavourites(favStarbucks);
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS)
                .withAddress(VALID_ADDRESS_DECK).withTags(VALID_TAG_QUIET).withFavourite(true)
                .build();

        // we are testing studyTracker#setStudySpot to correctly replace a favourited study spot
        // in both StudyTracker and the favouritesList
        studyTracker.setStudySpot(favStarbucks, editedStarbucks);
        assertEquals(studyTracker.findStudySpot(STARBUCKS.getName()), editedStarbucks);
        assertTrue(studyTracker.getFavouriteStudySpotList().contains(editedStarbucks));
    }

    @Test
    public void addStudySpotToFavourite_notInStudyTracker_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> studyTracker.addStudySpotToFavourites(STARBUCKS));
    }

    @Test
    public void addStudySpotToFavourite_studySpotInFavourites_returnsTrue() {
        studyTracker.addStudySpot(STARBUCKS);
        studyTracker.addStudySpotToFavourites(STARBUCKS);
        StudySpot favouritedStarbucks = new StudySpotBuilder(STARBUCKS).withFavourite(true).build();
        assertTrue(studyTracker.getFavouriteStudySpotList().contains(favouritedStarbucks));
    }

    @Test
    public void removeStudySpotToFavourite_studySpotInFavourites_returnsTrue() {
        studyTracker.addStudySpot(STARBUCKS);
        studyTracker.addStudySpotToFavourites(STARBUCKS);
        StudySpot favouritedStarbucks = new StudySpotBuilder(STARBUCKS).withFavourite(true).build();
        assertTrue(studyTracker.getFavouriteStudySpotList().contains(favouritedStarbucks));
        studyTracker.removeStudySpotFromFavourites(favouritedStarbucks);
        assertTrue(studyTracker.getFavouriteStudySpotList().isEmpty());
    }

    @Test
    public void isFavouriteStudySpot_notInFavourites_returnsFalse() {
        studyTracker.addStudySpot(STARBUCKS);
        assertFalse(studyTracker.isFavouriteStudySpot(STARBUCKS));
    }


    @Test
    public void isFavouriteStudySpot_inFavourites_returnsTrue() {
        studyTracker.addStudySpot(STARBUCKS);
        studyTracker.addStudySpotToFavourites(STARBUCKS);
        assertTrue(studyTracker.isFavouriteStudySpot(STARBUCKS));
    }

    @Test
    public void getStudySpotList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> studyTracker.getStudySpotList().remove(0));
    }

    @Test
    public void hashCode_test() {
        StudyTracker anotherSt = new StudyTracker();
        StudyTracker deckSt = new StudyTracker();

        studyTracker.addStudySpot(STARBUCKS);
        anotherSt.addStudySpot(STARBUCKS);
        deckSt.addStudySpot(DECK);

        assertEquals(anotherSt.hashCode(), studyTracker.hashCode());
        assertNotEquals(deckSt.hashCode(), studyTracker.hashCode());
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
