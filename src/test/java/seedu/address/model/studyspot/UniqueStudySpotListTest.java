package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.ALICE;
import static seedu.address.testutil.TypicalStudySpots.BOB;

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
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueStudySpotList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueStudySpotList.add(ALICE);
        assertTrue(uniqueStudySpotList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueStudySpotList.add(ALICE);
        StudySpot editedAlice = new StudySpotBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueStudySpotList.contains(editedAlice));
    }

    @Test
    public void add_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.add(null));
    }

    @Test
    public void add_duplicateStudySpot_throwsDuplicateStudySpotException() {
        uniqueStudySpotList.add(ALICE);
        assertThrows(DuplicateStudySpotException.class, () -> uniqueStudySpotList.add(ALICE));
    }

    @Test
    public void setStudySpot_nullTargetStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.setStudySpot(null, ALICE));
    }

    @Test
    public void setStudySpot_nullEditedStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.setStudySpot(ALICE, null));
    }

    @Test
    public void setStudySpot_targetStudySpotNotInList_throwsStudySpotNotFoundException() {
        assertThrows(StudySpotNotFoundException.class, () -> uniqueStudySpotList.setStudySpot(ALICE, ALICE));
    }

    @Test
    public void setStudySpot_editedStudySpotIsSameStudySpot_success() {
        uniqueStudySpotList.add(ALICE);
        uniqueStudySpotList.setStudySpot(ALICE, ALICE);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(ALICE);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpot_editedStudySpotHasSameIdentity_success() {
        uniqueStudySpotList.add(ALICE);
        StudySpot editedAlice = new StudySpotBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueStudySpotList.setStudySpot(ALICE, editedAlice);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(editedAlice);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpot_editedStudySpotHasDifferentIdentity_success() {
        uniqueStudySpotList.add(ALICE);
        uniqueStudySpotList.setStudySpot(ALICE, BOB);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(BOB);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpot_editedStudySpotHasNonUniqueIdentity_throwsDuplicateStudySpotException() {
        uniqueStudySpotList.add(ALICE);
        uniqueStudySpotList.add(BOB);
        assertThrows(DuplicateStudySpotException.class, () -> uniqueStudySpotList.setStudySpot(ALICE, BOB));
    }

    @Test
    public void remove_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsStudySpotNotFoundException() {
        assertThrows(StudySpotNotFoundException.class, () -> uniqueStudySpotList.remove(ALICE));
    }

    @Test
    public void remove_existingStudySpot_removesStudySpot() {
        uniqueStudySpotList.add(ALICE);
        uniqueStudySpotList.remove(ALICE);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpots_nullUniqueStudySpotList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.setStudySpots((UniqueStudySpotList) null));
    }

    @Test
    public void setStudySpots_uniqueStudySpotList_replacesOwnListWithProvidedUniqueStudySpotList() {
        uniqueStudySpotList.add(ALICE);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(BOB);
        uniqueStudySpotList.setStudySpots(expectedUniqueStudySpotList);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpots_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStudySpotList.setStudySpots((List<StudySpot>) null));
    }

    @Test
    public void setStudySpots_list_replacesOwnListWithProvidedList() {
        uniqueStudySpotList.add(ALICE);
        List<StudySpot> personList = Collections.singletonList(BOB);
        uniqueStudySpotList.setStudySpots(personList);
        UniqueStudySpotList expectedUniqueStudySpotList = new UniqueStudySpotList();
        expectedUniqueStudySpotList.add(BOB);
        assertEquals(expectedUniqueStudySpotList, uniqueStudySpotList);
    }

    @Test
    public void setStudySpots_listWithDuplicateStudySpots_throwsDuplicateStudySpotException() {
        List<StudySpot> listWithDuplicateStudySpots = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateStudySpotException.class, () ->
                uniqueStudySpotList.setStudySpots(listWithDuplicateStudySpots));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueStudySpotList.asUnmodifiableObservableList().remove(0));
    }
}
