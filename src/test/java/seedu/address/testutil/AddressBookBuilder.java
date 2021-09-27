package seedu.address.testutil;

import seedu.address.model.StudyTracker;
import seedu.address.model.studyspot.StudySpot;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code StudyTracker ab = new AddressBookBuilder().withStudySpot("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private StudyTracker studyTracker;

    public AddressBookBuilder() {
        studyTracker = new StudyTracker();
    }

    public AddressBookBuilder(StudyTracker studyTracker) {
        this.studyTracker = studyTracker;
    }

    /**
     * Adds a new {@code StudySpot} to the {@code StudyTracker} that we are building.
     */
    public AddressBookBuilder withStudySpot(StudySpot studySpot) {
        studyTracker.addStudySpot(studySpot);
        return this;
    }

    public StudyTracker build() {
        return studyTracker;
    }
}
