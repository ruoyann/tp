package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonSerializableUserPrefs.MESSAGE_DUPLICATE_ALIASES;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;

class JsonSerializableUserPrefsTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableUserPrefsTest");
    private static final Path DUPLICATE_ALIASES_FILE = TEST_DATA_FOLDER.resolve("duplicateAliasesUserPrefs.json");

    @Test
    public void constructor_validUserPrefsSource() throws Exception {
        UserPrefs userPrefsDefault = new UserPrefs();
        JsonSerializableUserPrefs serializedUserPrefs = new JsonSerializableUserPrefs(new UserPrefs());
        assertEquals(userPrefsDefault, serializedUserPrefs.toModelType());
    }

    @Test
    public void constructor_duplicateAliasesInUserPrefsSource_throwsIllegalValueException() throws Exception {
        List<Alias> duplicateAliases = Arrays.asList(
                new Alias("ls", "list"),
                new Alias("ls", "exit")
        );
        UserPrefs userPrefsDuplicates = new UserPrefs();
        userPrefsDuplicates.setUserAliases(duplicateAliases);
        JsonSerializableUserPrefs userPrefs = new JsonSerializableUserPrefs(userPrefsDuplicates);

        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_ALIASES, userPrefs::toModelType);
    }

    @Test
    public void toModelType_duplicateAliasesFile_throwsIllegalValueException() throws Exception {
        JsonSerializableUserPrefs dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ALIASES_FILE,
                JsonSerializableUserPrefs.class).get();
        assertThrows(IllegalValueException.class, MESSAGE_DUPLICATE_ALIASES, dataFromFile::toModelType);
    }

}
