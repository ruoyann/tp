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
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Rating(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Rating.isValidRating(null));

        // invalid phone numbers
        assertFalse(Rating.isValidRating("")); // empty string
        assertFalse(Rating.isValidRating(" ")); // spaces only
        assertFalse(Rating.isValidRating("91")); // less than 3 numbers
        assertFalse(Rating.isValidRating("phone")); // non-numeric
        assertFalse(Rating.isValidRating("9011p041")); // alphabets within digits
        assertFalse(Rating.isValidRating("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Rating.isValidRating("911")); // exactly 3 numbers
        assertTrue(Rating.isValidRating("93121534"));
        assertTrue(Rating.isValidRating("124293842033123")); // long phone numbers
    }
}
