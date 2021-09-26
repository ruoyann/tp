package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;
import seedu.address.model.studyspot.*;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code StudyTracker} with sample data.
 */
public class SampleDataUtil {
    public static StudySpot[] getSampleStudySpots() {
        return new StudySpot[] {
            new StudySpot(new Name("Starbucks UTown"), new Rating("4"), new Email("Starbucks@Utown.com"),
                new Address("U-Town"),
                getTagSet("coffee", "tea", "me")),
            new StudySpot(new Name("COM1 Tech Hangout"), new Rating("4"), new Email("-"),
                new Address("COM1"),
                getTagSet("friendly")),
            new StudySpot(new Name("Central Library"), new Rating("3"), new Email("clb@nus.com"),
                new Address("Next to FASS"),
                getTagSet("quiet", "cold")),
            new StudySpot(new Name("Outside cool spot"), new Rating("2"), new Email("-"),
                new Address("Cool-spot"),
                getTagSet("drinks", "hot")),
            new StudySpot(new Name("Medicine library"), new Rating("5"), new Email("-"),
                new Address("-"),
                getTagSet("awesome")),
            new StudySpot(new Name("Mothers basement"), new Rating("1"), new Email("mom@example.com"),
                new Address("My house"),
                getTagSet("depression"))
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
