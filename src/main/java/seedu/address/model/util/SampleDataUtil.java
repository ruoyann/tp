package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Favourite;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
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
                    new OperatingHours("0900-2200, 0900-2200"),
                    new Address("University Town, ERC, 138608"),
                    new Favourite(true),
                    getTagSet("coffee", "tea", "smellsGood"),
                    getAmenitySet("wifi", "charger", "food", "aircon")),
            new StudySpot(new Name("COM1 Tech Hangout"),
                    new Rating("4"),
                    new OperatingHours("unspecified"),
                    new Address("B1-01, COM1"),
                    getTagSet("friendly", "noisy", "crowdedOnThursdays"),
                    getAmenitySet("wifi", "charger", "aircon")),
            new StudySpot(new Name("Central Library"),
                    new Rating("3"),
                    new OperatingHours("0900-2200, 0900-2200"),
                    new Address("Next to FASS"),
                    getTagSet("quiet", "cold"),
                    getAmenitySet("wifi", "charger", "aircon")),
            new StudySpot(new Name("Outside cool spot"),
                    new Rating("2"),
                    new OperatingHours("unspecified"),
                    new Address("COM2"),
                    getTagSet("drinks", "warm"),
                    getAmenitySet("wifi", "charger", "food")),
            new StudySpot(new Name("Medicine library"),
                    new Rating("5"),
                    new OperatingHours("unspecified"),
                    new Address("NUS Medicine"),
                    getTagSet("awesome"),
                    getAmenitySet("wifi", "charger", "aircon")),
            new StudySpot(new Name("Home Sweet Home"),
                    new Rating("1"),
                    new OperatingHours("unspecified"),
                    new Address("42 Wallaby Way"),
                    getTagSet("warm"),
                    getAmenitySet("wifi", "charger"))
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
