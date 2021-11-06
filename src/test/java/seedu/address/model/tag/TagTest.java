package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void constructor_tooLongTagName_throwsIllegalArgumentException() {
        String tooLongTagName = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123_"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ123_";
        assertThrows(IllegalArgumentException.class, () -> new Tag(tooLongTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }



    @Test
    public void equals() {
        Tag warm = new Tag("warm");
        Tag cold = new Tag("cold");

        // same object -> returns true
        assertTrue(warm.equals(warm));

        // same values -> returns true
        Tag warmCopy = new Tag("warm");
        assertTrue(warmCopy.equals(warm));

        // different types -> returns false
        assertFalse(warm.equals(1));

        // null -> returns false
        assertFalse(cold.equals(null));

        // different favourite -> returns false
        assertFalse(warm.equals(cold));
    }

}
