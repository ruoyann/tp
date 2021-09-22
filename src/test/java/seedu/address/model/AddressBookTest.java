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

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getStudySpotList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateStudySpots_throwsDuplicateStudySpotException() {
        // Two persons with the same identity fields
        StudySpot editedAlice = new StudySpotBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<StudySpot> newStudySpots = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newStudySpots);

        assertThrows(DuplicateStudySpotException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasStudySpot_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasStudySpot(null));
    }

    @Test
    public void hasStudySpot_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasStudySpot(ALICE));
    }

    @Test
    public void hasStudySpot_personInAddressBook_returnsTrue() {
        addressBook.addStudySpot(ALICE);
        assertTrue(addressBook.hasStudySpot(ALICE));
    }

    @Test
    public void hasStudySpot_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addStudySpot(ALICE);
        StudySpot editedAlice = new StudySpotBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasStudySpot(editedAlice));
    }

    @Test
    public void getStudySpotList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getStudySpotList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<StudySpot> persons = FXCollections.observableArrayList();

        AddressBookStub(Collection<StudySpot> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<StudySpot> getStudySpotList() {
            return persons;
        }
    }

}
