package seedu.address.testutil;

import seedu.address.model.StudyTracker;
import seedu.address.model.studyspot.StudySpot;

/**
 * A utility class to help with building StudyTracker objects.
 * Example usage: <br>
 *     {@code StudyTracker ab = new StudyTrackerBuilder().withStudySpot("John", "Doe").build();}
 */
public class StudyTrackerBuilder {

    private StudyTracker studyTracker;

    public StudyTrackerBuilder() {
        studyTracker = new StudyTracker();
    }

    public StudyTrackerBuilder(StudyTracker studyTracker) {
        this.studyTracker = studyTracker;
    }

    /**
     * Adds a new {@code StudySpot} to the {@code StudyTracker} that we are building.
     */
    public StudyTrackerBuilder withStudySpot(StudySpot studySpot) {
        studyTracker.addStudySpot(studySpot);
        return this;
    }

    public StudyTracker build() {
        return studyTracker;
    }
}
