package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.ALICE;
import static seedu.address.testutil.TypicalStudySpots.HOON;
import static seedu.address.testutil.TypicalStudySpots.IDA;
import static seedu.address.testutil.TypicalStudySpots.getTypicalAddressBook;

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
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyStudyTracker> readAddressBook(String filePath) throws Exception {
        return new JsonStudyTrackerStorage(Paths.get(filePath)).readStudyTracker(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatStudyTracker.json"));
    }

    @Test
    public void readAddressBook_invalidStudySpotAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidStudySpotStudyTracker.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidStudySpotAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidStudySpotStudyTracker.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        StudyTracker original = getTypicalAddressBook();
        JsonStudyTrackerStorage jsonStudyTrackerStorage = new JsonStudyTrackerStorage(filePath);

        // Save in new file and read back
        jsonStudyTrackerStorage.saveStudyTracker(original, filePath);
        ReadOnlyStudyTracker readBack = jsonStudyTrackerStorage.readStudyTracker(filePath).get();
        assertEquals(original, new StudyTracker(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addStudySpot(HOON);
        original.removeStudySpot(ALICE);
        jsonStudyTrackerStorage.saveStudyTracker(original, filePath);
        readBack = jsonStudyTrackerStorage.readStudyTracker(filePath).get();
        assertEquals(original, new StudyTracker(readBack));

        // Save and read without specifying file path
        original.addStudySpot(IDA);
        jsonStudyTrackerStorage.saveStudyTracker(original); // file path not specified
        readBack = jsonStudyTrackerStorage.readStudyTracker().get(); // file path not specified
        assertEquals(original, new StudyTracker(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyStudyTracker addressBook, String filePath) {
        try {
            new JsonStudyTrackerStorage(Paths.get(filePath))
                    .saveStudyTracker(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new StudyTracker(), null));
    }
}
