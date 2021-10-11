package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;

public class JsonAdaptedAliasTest {

    private static final Alias LS_LIST_ALIAS = new Alias("ls", "list");

    @Test
    void getCommandWord() {
        JsonAdaptedAlias alias = new JsonAdaptedAlias(LS_LIST_ALIAS);

        assertTrue(alias.getAliasCommandWord().equals("list"));
    }

    @Test
    public void toModelType_invalidFields_throwsIllegalValueException() {
        JsonAdaptedAlias invalidUseralias = new JsonAdaptedAlias("list", "list");
        assertThrows(IllegalValueException.class, Alias.MESSAGE_ALIAS_CONSTRAINTS, invalidUseralias::toModelType);

        JsonAdaptedAlias invalidCommandWord = new JsonAdaptedAlias("ls", "ls");
        assertThrows(IllegalValueException.class, Alias.MESSAGE_COMMAND_CONSTRAINTS, invalidCommandWord::toModelType);

        JsonAdaptedAlias invalidBoth = new JsonAdaptedAlias("delete", "cs2103");
        assertThrows(IllegalValueException.class, Alias.MESSAGE_ALIAS_CONSTRAINTS, invalidBoth::toModelType);

        JsonAdaptedAlias nullValues = new JsonAdaptedAlias(null, "cs2103");
        assertThrows(IllegalValueException.class, Alias.MESSAGE_ALIAS_CONSTRAINTS, nullValues::toModelType);

        JsonAdaptedAlias nullValues2 = new JsonAdaptedAlias("ls", null);
        assertThrows(IllegalValueException.class, Alias.MESSAGE_COMMAND_CONSTRAINTS, nullValues2::toModelType);
    }


    @Test
    public void toModelType_validAlias_returnsAlias() throws Exception {
        JsonAdaptedAlias alias = new JsonAdaptedAlias(LS_LIST_ALIAS);
        assertEquals(LS_LIST_ALIAS, alias.toModelType());
    }
}
