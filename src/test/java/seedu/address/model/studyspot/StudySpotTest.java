package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.BOB;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudySpotBuilder;

public class StudySpotTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        StudySpot person = new StudySpotBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSameStudySpot() {
        // same object -> returns true
        assertTrue(STARBUCKS.isSameStudySpot(STARBUCKS));

        // null -> returns false
        assertFalse(STARBUCKS.isSameStudySpot(null));

        // same name, all other attributes different -> returns true
        StudySpot editedAlice = new StudySpotBuilder(STARBUCKS).withRating(VALID_RATING_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(STARBUCKS.isSameStudySpot(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new StudySpotBuilder(STARBUCKS).withName(VALID_NAME_BOB).build();
        assertFalse(STARBUCKS.isSameStudySpot(editedAlice));

        // name differs in case, all other attributes same -> returns false
        StudySpot editedBob = new StudySpotBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameStudySpot(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new StudySpotBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameStudySpot(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        StudySpot aliceCopy = new StudySpotBuilder(STARBUCKS).build();
        assertTrue(STARBUCKS.equals(aliceCopy));

        // same object -> returns true
        assertTrue(STARBUCKS.equals(STARBUCKS));

        // null -> returns false
        assertFalse(STARBUCKS.equals(null));

        // different type -> returns false
        assertFalse(STARBUCKS.equals(5));

        // different person -> returns false
        assertFalse(STARBUCKS.equals(BOB));

        // different name -> returns false
        StudySpot editedAlice = new StudySpotBuilder(STARBUCKS).withName(VALID_NAME_BOB).build();
        assertFalse(STARBUCKS.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new StudySpotBuilder(STARBUCKS).withRating(VALID_RATING_BOB).build();
        assertFalse(STARBUCKS.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudySpotBuilder(STARBUCKS).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(STARBUCKS.equals(editedAlice));

        // different address -> returns false
        editedAlice = new StudySpotBuilder(STARBUCKS).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(STARBUCKS.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new StudySpotBuilder(STARBUCKS).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(STARBUCKS.equals(editedAlice));
    }
}
