package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMENITY_CHARGER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OPERATING_HOURS_DECK;
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
        StudySpot editedStarbucks = new StudySpotBuilder(STARBUCKS)
                .withRating(VALID_RATING_DECK).withOperatingHours(VALID_OPERATING_HOURS_DECK)
                .withAddress(VALID_ADDRESS_DECK).withFavourite(true).withTags(VALID_TAG_QUIET).build();
        assertTrue(STARBUCKS.isSameStudySpot(editedStarbucks));

        // different name, all other attributes same -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withName(VALID_NAME_DECK).build();
        assertFalse(STARBUCKS.isSameStudySpot(editedStarbucks));

        // name differs in case, all other attributes same -> returns true
        StudySpot editedDeck = new StudySpotBuilder(DECK).withName(VALID_NAME_DECK.toLowerCase()).build();
        assertTrue(DECK.isSameStudySpot(editedDeck));

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

        editedStarbucks = new StudySpotBuilder(STARBUCKS).withAmenities(VALID_AMENITY_CHARGER).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));

        // different operating hours  -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withOperatingHours(VALID_OPERATING_HOURS_DECK).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));

        // different address -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withAddress(VALID_ADDRESS_DECK).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));

        // different favourite -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withFavourite(true).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));

        // different tags -> returns false
        editedStarbucks = new StudySpotBuilder(STARBUCKS).withTags(VALID_TAG_QUIET).build();
        assertFalse(STARBUCKS.equals(editedStarbucks));
    }

    @Test
    public void hashCode_test() {
        StudySpot starbucks = new StudySpotBuilder(STARBUCKS).build();

        assertEquals(new StudySpotBuilder(STARBUCKS).build().hashCode(), starbucks.hashCode());
        assertNotEquals(new StudySpotBuilder(DECK).build().hashCode(), starbucks.hashCode());
    }

    @Test
    public void toString_stringReturned() {
        String defaultConfigAsString = "Starbucks; Rating: 4; Address: UTown;"
                + " Favourite: true; Studied Hours: 100; Tags: [coffee]; Amenities: [wifi]";

        StudySpot starbucks = new StudySpotBuilder(STARBUCKS)
                .withStudiedHours("100").withFavourite(true).build();

        assertEquals(defaultConfigAsString, starbucks.toString());
    }
}
