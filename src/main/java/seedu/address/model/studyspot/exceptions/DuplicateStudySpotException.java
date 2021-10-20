package seedu.address.model.studyspot.exceptions;

/**
 * Signals that the operation will result in duplicate StudySpots
 * (StudySpots are considered duplicates if they have the same identity).
 */
public class DuplicateStudySpotException extends RuntimeException {
    public DuplicateStudySpotException() {
        super("Operation would result in duplicate StudySpots");
    }
}
