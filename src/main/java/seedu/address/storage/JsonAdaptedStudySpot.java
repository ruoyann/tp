package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Email;
import seedu.address.model.studyspot.Favourite;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link StudySpot}.
 */
class JsonAdaptedStudySpot {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "StudySpot's %s field is missing!";

    private final String name;
    private final String rating;
    private final String email;
    private final String address;
    private final String favourite;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedAmenity> amenities = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudySpot} with the given study spot details.
     */
    @JsonCreator
    public JsonAdaptedStudySpot(@JsonProperty("name") String name, @JsonProperty("rating") String rating,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("favourite") String favourite, @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
            @JsonProperty("amenities") List<JsonAdaptedAmenity> amenities) {
        this.name = name;
        this.rating = rating;
        this.email = email;
        this.address = address;
        this.favourite = favourite;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (amenities != null) {
            this.amenities.addAll(amenities);
        }
    }

    /**
     * Converts a given {@code StudySpot} into this class for Jackson use.
     */
    public JsonAdaptedStudySpot(StudySpot source) {
        name = source.getName().fullName;
        rating = source.getRating().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        favourite = source.getFavourite().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        amenities.addAll(source.getAmenities().stream()
                .map(JsonAdaptedAmenity::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted study spot object into the model's {@code StudySpot} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted study spot.
     */
    public StudySpot toModelType() throws IllegalValueException {
        final List<Tag> studySpotTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            studySpotTags.add(tag.toModelType());
        }

        final List<Amenity> studySpotAmenities = new ArrayList<>();
        for (JsonAdaptedAmenity amenity : amenities) {
            studySpotAmenities.add(amenity.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(rating)) {
            throw new IllegalValueException(Rating.MESSAGE_CONSTRAINTS);
        }
        final Rating modelRating = new Rating(rating);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (favourite == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Favourite.class.getSimpleName()));
        }
        if (!Favourite.isValidFavourite(favourite)) {
            throw new IllegalValueException(Favourite.MESSAGE_CONSTRAINTS);
        }
        final Favourite modelFavourite = new Favourite(Boolean.parseBoolean(favourite));

        final Set<Tag> modelTags = new HashSet<>(studySpotTags);
        final Set<Amenity> modelAmenities = new HashSet<>(studySpotAmenities);
        return new StudySpot(modelName, modelRating, modelEmail, modelAddress, modelFavourite,
                modelTags, modelAmenities);
    }

}
