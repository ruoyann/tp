package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Email;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code StudyTracker} with sample data.
 */
public class SampleDataUtil {
    public static StudySpot[] getSampleStudySpots() {
        return new StudySpot[] {
            new StudySpot(new Name("Starbucks UTown"),
                    new Rating("4"),
                    new Email("Starbucks@Utown.com"),
                    new Address("Utown"),
                    getTagSet("coffee", "tea", "me")),
            new StudySpot(new Name("COM1 Tech Hangout"),
                    new Rating("4"),
                    new Email("-"),
                    new Address("COM1"),
                    getTagSet("friendly", "noisy", "crowdedOnThursdays")),
            new StudySpot(new Name("Central Library"),
                    new Rating("3"),
                    new Email("clb@nus.com"),
                    new Address("Next to FASS"),
                    getTagSet("quiet", "cold")),
            new StudySpot(new Name("Outside cool spot"),
                    new Rating("2"),
                    new Email("-"),
                    new Address("NUS COM2"),
                    getTagSet("drinks", "warm")),
            new StudySpot(new Name("Medicine library"),
                    new Rating("5"),
                    new Email("-"),
                    new Address("NUS Medicine"),
                    getTagSet("awesome")),
            new StudySpot(new Name("Mothers basement"),
                    new Rating("1"),
                    new Email("mom@example.com"),
                    new Address("My house"),
                    getTagSet("warm"))
        };
    }

    public static ReadOnlyStudyTracker getSampleAddressBook() {
        StudyTracker sampleAb = new StudyTracker();
        for (StudySpot sampleStudySpot : getSampleStudySpots()) {
            sampleAb.addStudySpot(sampleStudySpot);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
