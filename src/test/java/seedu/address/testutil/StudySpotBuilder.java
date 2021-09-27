package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Email;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building StudySpot objects.
 */
public class StudySpotBuilder {

    public static final String DEFAULT_NAME = "COM1 Tech Hangout";
    public static final String DEFAULT_RATING = "3";
    public static final String DEFAULT_EMAIL = "-";
    public static final String DEFAULT_ADDRESS = "NUS School of Computing";

    private Name name;
    private Rating rating;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    /**
     * Creates a {@code StudySpotBuilder} with the default details.
     */
    public StudySpotBuilder() {
        name = new Name(DEFAULT_NAME);
        rating = new Rating(DEFAULT_RATING);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the StudySpotBuilder with the data of {@code studySpotToCopy}.
     */
    public StudySpotBuilder(StudySpot studySpotToCopy) {
        name = studySpotToCopy.getName();
        rating = studySpotToCopy.getRating();
        email = studySpotToCopy.getEmail();
        address = studySpotToCopy.getAddress();
        tags = new HashSet<>(studySpotToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code StudySpot} that we are building.
     */
    public StudySpotBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code StudySpot} that we are building.
     */
    public StudySpotBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code StudySpot} that we are building.
     */
    public StudySpotBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code StudySpot} that we are building.
     */
    public StudySpotBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code StudySpot} that we are building.
     */
    public StudySpotBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public StudySpot build() {
        return new StudySpot(name, rating, email, address, tags);
    }

}
