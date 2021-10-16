package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonStudyTrackerStorage studyTrackerStorage = new JsonStudyTrackerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(studyTrackerStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6, "default"));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void studyTrackerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonStudyTrackerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonStudyTrackerStorageTest} class.
         */
        StudyTracker original = getTypicalStudyTracker();
        storageManager.saveStudyTracker(original);
        ReadOnlyStudyTracker retrieved = storageManager.readStudyTracker().get();
        assertEquals(original, new StudyTracker(retrieved));
    }

    @Test
    public void getStudyTrackerFilePath() {
        assertNotNull(storageManager.getStudyTrackerFilePath());
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

}
