package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyStudyTracker;

/**
 * A class to access StudyTracker data stored as a json file on the hard disk.
 */
public class JsonStudyTrackerStorage implements StudyTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStudyTrackerStorage.class);

    private Path filePath;

    public JsonStudyTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getStudyTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyStudyTracker> readStudyTracker() throws DataConversionException {
        return readStudyTracker(filePath);
    }

    /**
     * Similar to {@link #readStudyTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyStudyTracker> readStudyTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableStudyTracker> jsonStudyTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializableStudyTracker.class);
        if (jsonStudyTracker.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonStudyTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveStudyTracker(ReadOnlyStudyTracker studyTracker) throws IOException {
        saveStudyTracker(studyTracker, filePath);
    }

    /**
     * Similar to {@link #saveStudyTracker(ReadOnlyStudyTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveStudyTracker(ReadOnlyStudyTracker studyTracker, Path filePath) throws IOException {
        requireNonNull(studyTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStudyTracker(studyTracker), filePath);
    }

}
