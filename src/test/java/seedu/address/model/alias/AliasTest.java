package seedu.address.model.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class AliasTest {

    @Test
    public void constructor_validInput_success() {
        Alias expected = new Alias("ls", "list");
        Alias expected2 = new Alias("ls2", "list"); // alphanumeric allowed

        assertEquals(new Alias("ls", "list"), expected);
        assertEquals(new Alias("ls2", "list"), expected2);
    }


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Alias(null, "list"));
        assertThrows(NullPointerException.class, () -> new Alias("ls", null));
        assertThrows(NullPointerException.class, () -> new Alias(null, null));
    }

    @Test
    public void constructor_invalidUserAliasType_throwsIllegalArgumentException() {
        String emptyUserAlias = "";
        String spaceInUserAlias = "list"; // userAlias cannot be a command word
        String invalidWordUserAlias = "list"; // userAlias cannot be a command word

        assertThrows(IllegalArgumentException.class, () -> new Alias(emptyUserAlias, "list"));
        assertThrows(IllegalArgumentException.class, () -> new Alias(spaceInUserAlias, "list"));
        assertThrows(IllegalArgumentException.class, () -> new Alias(invalidWordUserAlias, "list"));
    }

    @Test
    public void constructor_invalidCommandWordType_throwsIllegalArgumentException() {
        String emptyCommandWord = "";
        String invalidWordCommandWord = "park"; // command word doesn't exist
        String dangerousWordCommandWord = "alias"; //cannot alias to an "alias" command
        String alsoDangerousWordCommandWord = "unalias"; //cannot alias to an "unalias" command

        assertThrows(IllegalArgumentException.class, () -> new Alias("ls", emptyCommandWord));
        assertThrows(IllegalArgumentException.class, () -> new Alias("ls", invalidWordCommandWord));
        assertThrows(IllegalArgumentException.class, () -> new Alias("ls", dangerousWordCommandWord));
        assertThrows(IllegalArgumentException.class, () -> new Alias("ls", alsoDangerousWordCommandWord));
    }

    @Test
    void isSameAlias() {
        // only checks if the two Aliases share the same userAlias value
        Alias lsAlias = new Alias("ls", "list");
        Alias lsAliasCopy = new Alias("ls", "list");
        Alias pwdAlias = new Alias("pwd", "list");
        Alias lsDeleteAlias = new Alias("ls", "delete");
        Alias pwdDeleteAlias = new Alias("pwd", "delete");

        // same object -> true
        assertTrue(lsAlias.isSameAlias(lsAlias));

        // same values -> true
        assertTrue(lsAlias.isSameAlias(lsAliasCopy));

        // null check -> false
        assertFalse(lsAlias.isSameAlias(null));

        // different userAlias -> false
        assertFalse(lsAlias.isSameAlias(pwdAlias));

        // different commandWord -> true
        assertTrue(lsAlias.isSameAlias(lsDeleteAlias));

        // different values -> false
        assertFalse(lsAlias.isSameAlias(pwdDeleteAlias));

    }

    @Test
    void equals() {
        Alias lsAlias = new Alias("ls", "list");
        Alias lsAliasCopy = new Alias("ls", "list");
        Alias pwdAlias = new Alias("pwd", "list");
        Alias lsDeleteAlias = new Alias("ls", "delete");
        Alias pwdDeleteAlias = new Alias("pwd", "delete");

        // same object -> true
        assertTrue(lsAlias.equals(lsAlias));

        // same values -> true
        assertTrue(lsAlias.equals(lsAliasCopy));

        // null check -> false
        assertFalse(lsAlias.equals(null));

        // different userAlias -> false
        assertFalse(lsAlias.equals(pwdAlias));

        // different commandWord -> false
        assertFalse(lsAlias.equals(lsDeleteAlias));

        // different values -> false
        assertFalse(lsAlias.equals(pwdDeleteAlias));
    }

    @Test
    void hashCode_test() {
        Alias lsAlias = new Alias("ls", "list");
        Alias lsAliasCopy = new Alias("ls", "list");
        Alias pwdAlias = new Alias("pwd", "list");
        Alias lsDeleteAlias = new Alias("ls", "delete");

        // same object -> same hashcode
        assertTrue(lsAlias.hashCode() == lsAlias.hashCode());

        // different object but same values -> same hashcode
        assertTrue(lsAlias.hashCode() == lsAliasCopy.hashCode());

        // different userAlias value -> different hashcode
        assertFalse(lsAlias.hashCode() == pwdAlias.hashCode());

        // different userAlias value -> different hashcode
        assertFalse(lsAlias.hashCode() == lsDeleteAlias.hashCode());
    }
}
