package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class StudiedHoursTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudiedHours(null));
    }

    @Test
    public void constructor_invalidStudiedHours_throwsIllegalArgumentException() {
        String invalidStudiedHours = "-5";
        assertThrows(IllegalArgumentException.class, () -> new StudiedHours(invalidStudiedHours));
    }

    @Test
    public void isValidLoggedHours() {
        // null rating
        assertThrows(NullPointerException.class, () -> StudiedHours.isValidLoggedHours(null));

        // invalid hours
        assertFalse(StudiedHours.isValidLoggedHours("")); // empty string
        assertFalse(StudiedHours.isValidLoggedHours(" ")); // spaces only
        assertFalse(StudiedHours.isValidLoggedHours("rating")); // non-numeric
        assertFalse(StudiedHours.isValidLoggedHours("9011p041")); // alphabets within digits
        assertFalse(StudiedHours.isValidLoggedHours("9312 1534")); // spaces within digits
        assertFalse(StudiedHours.isValidLoggedHours("-1")); // number smaller than 0
        assertFalse(StudiedHours.isValidLoggedHours("-100")); // negative numbers

        // valid rating
        assertTrue(StudiedHours.isValidLoggedHours("0"));
        assertTrue(StudiedHours.isValidLoggedHours("10"));
        assertTrue(StudiedHours.isValidLoggedHours("500"));
        assertTrue(StudiedHours.isValidLoggedHours("1"));
    }

    @Test
    public void addHours() throws IllegalValueException {
        StudiedHours tenHours = new StudiedHours("10");
        StudiedHours fiftyHours = new StudiedHours("50");
        StudiedHours maxHours = new StudiedHours(String.valueOf(Integer.MAX_VALUE));
        StudiedHours sixtyHours = new StudiedHours("60");

        assert(tenHours.addHours(fiftyHours).equals(sixtyHours));

        assertThrows(IllegalValueException.class, () -> tenHours.addHours(maxHours));
    }

    @Test
    public void equals_test() {
        StudiedHours hours = new StudiedHours("10");

        assertTrue(hours.equals(hours));
        assertTrue(new StudiedHours("10").equals(hours));
        assertFalse(new StudiedHours("3").equals(hours));
    }
}
