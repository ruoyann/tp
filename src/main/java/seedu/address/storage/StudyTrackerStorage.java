package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;

/**
 * Represents a storage for {@link StudyTracker}.
 */
public interface StudyTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStudyTrackerFilePath();

    /**
     * Returns StudyTracker data as a {@link ReadOnlyStudyTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyTracker> readStudyTracker() throws DataConversionException, IOException;

    /**
     * @see #getStudyTrackerFilePath()
     */
    Optional<ReadOnlyStudyTracker> readStudyTracker(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStudyTracker} to the storage.
     * @param studyTracker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStudyTracker(ReadOnlyStudyTracker studyTracker) throws IOException;

    /**
     * @see #saveStudyTracker(ReadOnlyStudyTracker)
     */
    void saveStudyTracker(ReadOnlyStudyTracker studyTracker, Path filePath) throws IOException;

}
