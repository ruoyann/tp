package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;
import seedu.address.model.amenity.Amenity;
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
                    getTagSet("coffee", "tea", "me"),
                    getAmenitySet("wifi")),
            new StudySpot(new Name("COM1 Tech Hangout"),
                    new Rating("4"),
                    new Email("-"),
                    new Address("COM1"),
                    getTagSet("friendly", "noisy", "crowdedOnThursdays"),
                    getAmenitySet("wifi")),
            new StudySpot(new Name("Central Library"),
                    new Rating("3"),
                    new Email("clb@nus.com"),
                    new Address("Next to FASS"),
                    getTagSet("quiet", "cold"),
                    getAmenitySet("wifi")),
            new StudySpot(new Name("Outside cool spot"),
                    new Rating("2"),
                    new Email("-"),
                    new Address("NUS COM2"),
                    getTagSet("drinks", "warm"),
                    getAmenitySet("wifi")),
            new StudySpot(new Name("Medicine library"),
                    new Rating("5"),
                    new Email("-"),
                    new Address("NUS Medicine"),
                    getTagSet("awesome"),
                    getAmenitySet("wifi")),
            new StudySpot(new Name("Mothers basement"),
                    new Rating("1"),
                    new Email("mom@example.com"),
                    new Address("My house"),
                    getTagSet("warm"),
                    getAmenitySet("wifi"))
        };
    }

    public static ReadOnlyStudyTracker getSampleStudyTracker() {
        StudyTracker sampleSt = new StudyTracker();
        for (StudySpot sampleStudySpot : getSampleStudySpots()) {
            sampleSt.addStudySpot(sampleStudySpot);
        }
        return sampleSt;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an amenity set containing the list of strings given.
     */
    public static Set<Amenity> getAmenitySet(String... strings) {
        return Arrays.stream(strings)
                .map(Amenity::new)
                .collect(Collectors.toSet());
    }
}
