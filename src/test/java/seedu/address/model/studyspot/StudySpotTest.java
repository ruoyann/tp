package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_QUIET;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.DECK;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudySpotBuilder;

public class StudySpotTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        StudySpot studySpot = new StudySpotBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> studySpot.getTags().remove(0));
    }

    @Test
    public void isSameStudySpot() {
        // same object -> returns true
        assertTrue(STARBUCKS.isSameStudySpot(STARBUCKS));

        // null -> returns false
        assertFalse(STARBUCKS.isSameStudySpot(null));

        // same name, all other attributes different -> returns true
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS).withRating(VALID_RATING_DECK).withEmail(VALID_EMAIL_DECK)
                .withAddress(VALID_ADDRESS_DECK).withTags(VALID_TAG_QUIET).build();
        assertTrue(STARBUCKS.isSameStudySpot(editedStarbucks));

        // different name, all other attributes same -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withName(VALID_NAME_DECK).build();
        assertFalse(STARBUCKS.isSameStudySpot(editedStarbucks));

        // name differs in case, all other attributes same -> returns false
        StudySpot editedDeck = new StudySpotBuilder(DECK).withName(VALID_NAME_DECK.toLowerCase()).build();
        assertFalse(DECK.isSameStudySpot(editedDeck));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_DECK + " ";
        editedDeck = new StudySpotBuilder(DECK).withName(nameWithTrailingSpaces).build();
        assertFalse(DECK.isSameStudySpot(editedDeck));
    }

    @Test
    public void equals() {
        // same values -> returns true
        StudySpot starbucksCopy = new StudySpotBuilder(STARBUCKS).build();
        assertTrue(STARBUCKS.equals(starbucksCopy));

        // same object -> returns true
        assertTrue(STARBUCKS.equals(STARBUCKS));

        // null -> returns false
        assertFalse(STARBUCKS.equals(null));

        // different type -> returns false
        assertFalse(STARBUCKS.equals(5));

        // different study spot -> returns false
        assertFalse(STARBUCKS.equals(DECK));

        // different name -> returns false
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS).withName(VALID_NAME_DECK).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));

        // different rating -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withRating(VALID_RATING_DECK).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));

        // different email -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withEmail(VALID_EMAIL_DECK).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));

        // different address -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withAddress(VALID_ADDRESS_DECK).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));

        // different tags -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withTags(VALID_TAG_QUIET).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));
    }
}
