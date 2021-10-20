package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Favourite;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building StudySpot objects.
 */
public class StudySpotBuilder {

    public static final String DEFAULT_NAME = "COM1 Tech Hangout";
    public static final String DEFAULT_RATING = "3";
    public static final String DEFAULT_OPERATING_HOURS = "-";
    public static final String DEFAULT_ADDRESS = "NUS School of Computing";
    public static final String DEFAULT_STUDIED_HOURS = "5";
    public static final boolean DEFAULT_FAVOURITE = false;

    private Name name;
    private Rating rating;
    private OperatingHours operatingHours;
    private Address address;
    private Favourite favourite;
    private StudiedHours studiedHours;
    private Set<Tag> tags;
    private Set<Amenity> amenities;

    /**
     * Creates a {@code StudySpotBuilder} with the default details.
     */
    public StudySpotBuilder() {
        name = new Name(DEFAULT_NAME);
        rating = new Rating(DEFAULT_RATING);
        operatingHours = new OperatingHours(DEFAULT_OPERATING_HOURS);
        address = new Address(DEFAULT_ADDRESS);
        favourite = new Favourite(DEFAULT_FAVOURITE);
        studiedHours = new StudiedHours(DEFAULT_STUDIED_HOURS);
        tags = new HashSet<>();
        amenities = new HashSet<>();
    }

    /**
     * Initializes the StudySpotBuilder with the data of {@code studySpotToCopy}.
     */
    public StudySpotBuilder(StudySpot studySpotToCopy) {
        name = studySpotToCopy.getName();
        rating = studySpotToCopy.getRating();
        operatingHours = studySpotToCopy.getOperatingHours();
        address = studySpotToCopy.getAddress();
        favourite = studySpotToCopy.getFavourite();
        studiedHours = studySpotToCopy.getStudiedHours();
        tags = new HashSet<>(studySpotToCopy.getTags());
        amenities = new HashSet<>(studySpotToCopy.getAmenities());
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
     * Parses the {@code amenities} into a {@code Set<Amenity>} and set it to the {@code StudySpot}
     * that we are building.
     */
    public StudySpotBuilder withAmenities(String ... amenities) {
        this.amenities = SampleDataUtil.getAmenitySet(amenities);
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
     * Sets the {@code OperatingHours} of the {@code StudySpot} that we are building.
     */
    public StudySpotBuilder withOperatingHours(String operatingHours) {
        this.operatingHours = new OperatingHours(operatingHours);
        return this;
    }

    /**
     * Sets the {@code Favourite} of the {@code StudySpot} that we are building.
     */
    public StudySpotBuilder withFavourite(boolean favourite) {
        this.favourite = new Favourite(favourite);
        return this;
    }

    /**
     * Sets the {@code StudiedHours} of the {@code StudySpot} that we are building.
     */
    public StudySpotBuilder withStudiedHours(String studiedHours) {
        this.studiedHours = new StudiedHours(studiedHours);
        return this;
    }

    public StudySpot build() {
        return new StudySpot(name, rating, operatingHours, address, studiedHours, favourite, tags, amenities);
    }

}
