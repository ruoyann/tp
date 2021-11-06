package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("A*Star")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("my bedroom")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("3rd floor of rvrc")); // alphanumeric characters
        assertTrue(Name.isValidName("UTown Basketball Court")); // with capital letters
        assertTrue(Name.isValidName("COM2 Seminar Room 2 on the 2nd floor near bus stop")); // long names
    }

    @Test
    public void equals_test() {
        Name name = new Name("Starbucks UTown");

        assertTrue(name.equals(name));
        assertTrue(new Name("Starbucks UTown").equals(name));
        assertFalse(new Name("other").equals(name));
    }

    @Test
    public void hashCode_test() {
        Name name = new Name("Starbucks UTown");

        assertEquals(new Name("Starbucks UTown").hashCode(), name.hashCode());
        assertNotEquals(new Name("home").hashCode(), name.hashCode());
    }
}
