package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OperatingHoursTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OperatingHours(null));
    }

    @Test
    public void constructor_invalidOperatingHours_throwsIllegalArgumentException() {
        String invalidOperatingHours = "";
        assertThrows(IllegalArgumentException.class, () -> new OperatingHours(invalidOperatingHours));
    }

    @Test
    public void isValidOperatingHours() {
        // null OperatingHours
        assertThrows(NullPointerException.class, () -> OperatingHours.isValidOperatingHours(null));

        // blank OperatingHours
        assertFalse(OperatingHours.isValidOperatingHours("")); // empty string
        assertFalse(OperatingHours.isValidOperatingHours(" ")); // spaces only

        // missing parts
        assertFalse(OperatingHours.isValidOperatingHours("0900-2200")); // missing weekend operating hours field
        assertFalse(OperatingHours.isValidOperatingHours("0900-2200 0900-2200")); // missing ',' symbol
        // missing '-' between opening and closing hours
        assertFalse(OperatingHours.isValidOperatingHours("09002200, 09002200"));
        assertFalse(OperatingHours.isValidOperatingHours("0900-2200, 0900-220")); // incomplete closing hour
        assertFalse(OperatingHours.isValidOperatingHours("090-2200, 0900-2200")); // incomplete opening hour

        // invalid parts
        // invalid separator between hours and minutes
        assertFalse(OperatingHours.isValidOperatingHours("09:00-22:00, 09:00-22:00"));
        // invalid separator between operating hours for weekdays and weekends
        assertFalse(OperatingHours.isValidOperatingHours("0900-2200 & 0900-2200"));
        assertFalse(OperatingHours.isValidOperatingHours("0900-2200 - 0900-2200"));
        assertFalse(OperatingHours.isValidOperatingHours("0 900-2200, 0900-2200")); // spaces in time provided
        assertFalse(OperatingHours.isValidOperatingHours("0900--2200, 0900--2200")); // double '-' symbol
        // operating hours start with a hyphen
        assertFalse(OperatingHours.isValidOperatingHours("-0900-2200, 0900-2200"));
        // operating hours end with a hyphen
        assertFalse(OperatingHours.isValidOperatingHours("0900-2200, 0900-2200-"));
        // invalid timings
        assertFalse(OperatingHours.isValidOperatingHours("0900-2400, 0900-2200"));
        assertFalse(OperatingHours.isValidOperatingHours("0900-2200, 0900-2260"));

        // valid OperatingHours
        assertTrue(OperatingHours.isValidOperatingHours("0900-2200, 0900-2200"));
        assertTrue(OperatingHours.isValidOperatingHours("1000-2359, 1200-1830"));
        assertTrue(OperatingHours.isValidOperatingHours("0930-1000, 0900-2200"));
        // valid time intervals
        assertTrue(OperatingHours.isValidOperatingHours("0900-0900, 0900-2200"));
        assertTrue(OperatingHours.isValidOperatingHours("1800-1000, 0900-2200"));
        assertTrue(OperatingHours.isValidOperatingHours("0900-2200, 0930-0900"));
    }
    @Test
    public void hashCode_test() {
        OperatingHours operatingHours = new OperatingHours("0900-0900, 0900-2200");

        assertEquals(new OperatingHours("0900-0900, 0900-2200").hashCode(), operatingHours.hashCode());
        assertNotEquals(new OperatingHours("1800-1000, 1800-1000").hashCode(), operatingHours.hashCode());
    }
}
