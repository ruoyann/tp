package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_QUIET;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.DECK;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.studyspot.exceptions.DuplicateStudySpotException;
import seedu.address.model.studyspot.exceptions.StudySpotNotFoundException;
import seedu.address.testutil.StudySpotBuilder;

public class UniqueStudySpotListTest {

    private final UniqueStudySpotList uniqueStudySpotList = new UniqueStudySpotList();

    @Test
    public void contains_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.contains(null));
    }

    @Test
    public void contains_studySpotNotInList_returnsFalse() {
        assertFalse(uniqueStudySpotList.contains(STARBUCKS));
    }

    @Test
    public void contains_studySpotInList_returnsTrue() {
        uniqueStudySpotList.add(STARBUCKS);
        assertTrue(uniqueStudySpotList.contains(STARBUCKS));
    }

    @Test
    public void contains_studySpotWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudySpotList.add(STARBUCKS);
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS)
                .withAddress(VALID_ADDRESS_DECK).withTags(VALID_TAG_QUIET).build();
        assertTrue(uniqueStudySpotList.contains(editedStarbucks));
    }

    @Test
    public void add_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.add(null));
    }

    @Test
    public void add_duplicateStudySpot_throwsDuplicateStudySpotException() {
        uniqueStudySpotList.add(STARBUCKS);
        assertThrows(DuplicateStudySpotException.class, () -> uniqueStudySpotList.add(STARBUCKS));
    }

    @Test
    public void setStudySpot_nullTargetStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.setStudySpot(null, STARBUCKS));
    }

    @Test
    public void setStudySpot_nullEditedStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.setStudySpot(STARBUCKS, null));
    }

    @Test
    public void setStudySpot_targetStudySpotNotInList_throwsStudySpotNotFoundException() {
        assertThrows(StudySpotNotFoundException.class, () -> uniqueStudySpotList.setStudySpot(STARBUCKS, STARBUCKS));
    }

    @Test
    public void setStudySpot_editedStudySpotIsSameStudySpot_success() {
        uniqueStudySpotList.add(STARBUCKS);
        uniqueStudySpotList.setStudySpot(STARBUCKS, STARBUCKS);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(STARBUCKS);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpot_editedStudySpotHasSameIdentity_success() {
        uniqueStudySpotList.add(STARBUCKS);
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS).withAddress(VALID_ADDRESS_DECK)
                .withTags(VALID_TAG_QUIET).build();
        uniqueStudySpotList.setStudySpot(STARBUCKS, editedStarbucks);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(editedStarbucks);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpot_editedStudySpotHasDifferentIdentity_success() {
        uniqueStudySpotList.add(STARBUCKS);
        uniqueStudySpotList.setStudySpot(STARBUCKS, DECK);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(DECK);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpot_editedStudySpotHasNonUniqueIdentity_throwsDuplicateStudySpotException() {
        uniqueStudySpotList.add(STARBUCKS);
        uniqueStudySpotList.add(DECK);
        assertThrows(DuplicateStudySpotException.class, () -> uniqueStudySpotList.setStudySpot(STARBUCKS, DECK));
    }

    @Test
    public void remove_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.remove(null));
    }

    @Test
    public void remove_studySpotDoesNotExist_throwsStudySpotNotFoundException() {
        assertThrows(StudySpotNotFoundException.class, () -> uniqueStudySpotList.remove(STARBUCKS));
    }

    @Test
    public void remove_existingStudySpot_removesStudySpot() {
        uniqueStudySpotList.add(STARBUCKS);
        uniqueStudySpotList.remove(STARBUCKS);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpots_nullUniqueStudySpotList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.setStudySpots((UniqueStudySpotList) null));
    }

    @Test
    public void setStudySpots_uniqueStudySpotList_replacesOwnListWithProvidedUniqueStudySpotList() {
        uniqueStudySpotList.add(STARBUCKS);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(DECK);
        uniqueStudySpotList.setStudySpots(expectedUniqueStudySpotList);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpots_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.setStudySpots((List<StudySpot>) null));
    }

    @Test
    public void setStudySpots_list_replacesOwnListWithProvidedList() {
        uniqueStudySpotList.add(STARBUCKS);
        List<StudySpot> studySpots = Collections.singletonList(DECK);
        uniqueStudySpotList.setStudySpots(studySpots);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(DECK);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpots_listWithDuplicateStudySpots_throwsDuplicateStudySpotException() {
        List<StudySpot> listWithDuplicateStudySpots = Arrays.asList(STARBUCKS, STARBUCKS);
        assertThrows(DuplicateStudySpotException.class, () ->
                uniqueStudySpotList.setStudySpots(listWithDuplicateStudySpots));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueStudySpotList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void hashCode_test() {
        UniqueStudySpotList anotherStarbucks = new UniqueStudySpotList();
        UniqueStudySpotList anotherDeck = new UniqueStudySpotList();

        anotherStarbucks.add(STARBUCKS);
        anotherDeck.add(DECK);
        uniqueStudySpotList.add(STARBUCKS);

        assertEquals(anotherStarbucks.hashCode(), uniqueStudySpotList.hashCode());
        assertNotEquals(anotherDeck.hashCode(), uniqueStudySpotList.hashCode());
    }
}
