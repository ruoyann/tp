package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.BIZ_PODS;
import static seedu.address.testutil.TypicalStudySpots.COMPUTING_LOUNGE;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;

public class JsonStudyTrackerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonStudyTrackerStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readStudyTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readStudyTracker(null));
    }

    private java.util.Optional<ReadOnlyStudyTracker> readStudyTracker(String filePath) throws Exception {
        return new JsonStudyTrackerStorage(Paths.get(filePath)).readStudyTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readStudyTracker("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readStudyTracker("notJsonFormatStudyTracker.json"));
    }

    @Test
    public void readStudyTracker_invalidStudySpotStudyTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readStudyTracker("invalidStudySpotStudyTracker.json"));
    }

    @Test
    public void readStudyTracker_invalidAndValidStudySpotStudyTracker_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readStudyTracker("invalidAndValidStudySpotStudyTracker.json"));
    }

    @Test
    public void readAndSaveStudyTracker_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempStudyTracker.json");
        StudyTracker original = getTypicalStudyTracker();
        JsonStudyTrackerStorage jsonStudyTrackerStorage = new JsonStudyTrackerStorage(filePath);

        // Save in new file and read back
        jsonStudyTrackerStorage.saveStudyTracker(original, filePath);
        ReadOnlyStudyTracker readBack = jsonStudyTrackerStorage.readStudyTracker(filePath).get();
        assertEquals(original, new StudyTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudySpot(BIZ_PODS);
        original.removeStudySpot(STARBUCKS);
        jsonStudyTrackerStorage.saveStudyTracker(original, filePath);
        readBack = jsonStudyTrackerStorage.readStudyTracker(filePath).get();
        assertEquals(original, new StudyTracker(readBack));

        // Save and read without specifying file path
        original.addStudySpot(COMPUTING_LOUNGE);
        jsonStudyTrackerStorage.saveStudyTracker(original); // file path not specified
        readBack = jsonStudyTrackerStorage.readStudyTracker().get(); // file path not specified
        assertEquals(original, new StudyTracker(readBack));

    }

    @Test
    public void saveStudyTracker_nullStudyTracker_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudyTracker(null, "SomeFile.json"));
    }

    /**
     * Saves {@code studyTracker} at the specified {@code filePath}.
     */
    private void saveStudyTracker(ReadOnlyStudyTracker studyTracker, String filePath) {
        try {
            new JsonStudyTrackerStorage(Paths.get(filePath))
                    .saveStudyTracker(studyTracker, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveStudyTracker_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveStudyTracker(new StudyTracker(), null));
    }
}
