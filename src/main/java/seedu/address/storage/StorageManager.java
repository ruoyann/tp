package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of StudyTracker data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private StudyTrackerStorage studyTrackerStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code StudyTrackerStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(StudyTrackerStorage studyTrackerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.studyTrackerStorage = studyTrackerStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ StudyTracker methods ==============================

    @Override
    public Path getStudyTrackerFilePath() {
        return studyTrackerStorage.getStudyTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyStudyTracker> readStudyTracker() throws DataConversionException, IOException {
        return readStudyTracker(studyTrackerStorage.getStudyTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyStudyTracker> readStudyTracker(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return studyTrackerStorage.readStudyTracker(filePath);
    }

    @Override
    public void saveStudyTracker(ReadOnlyStudyTracker studyTracker) throws IOException {
        saveStudyTracker(studyTracker, studyTrackerStorage.getStudyTrackerFilePath());
    }

    @Override
    public void saveStudyTracker(ReadOnlyStudyTracker studyTracker, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        studyTrackerStorage.saveStudyTracker(studyTracker, filePath);
    }

}
