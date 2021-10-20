package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RatingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rating(null));
    }

    @Test
    public void constructor_invalidRating_throwsIllegalArgumentException() {
        String invalidRating = "";
        assertThrows(IllegalArgumentException.class, () -> new Rating(invalidRating));
    }

    @Test
    public void isValidRating() {
        // null rating
        assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid rating
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only
        assertFalse(Rating.isValidRating("91")); // less than 3 numbers
        assertFalse(Rating.isValidRating("rating")); // non-numeric
        assertFalse(Rating.isValidRating("9011p041")); // alphabets within digits
        assertFalse(Rating.isValidRating("9312 1534")); // spaces within digits
        assertFalse(Rating.isValidRating("8")); // number greater than 5
        assertFalse(Rating.isValidRating("0")); // number smaller than 1

        // valid rating
        assertTrue(Rating.isValidRating("3")); // only digits from 1 to 5
        assertTrue(Rating.isValidRating("5"));
        assertTrue(Rating.isValidRating("1"));
    }
}
