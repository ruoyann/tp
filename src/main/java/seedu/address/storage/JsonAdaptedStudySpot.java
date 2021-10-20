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
import seedu.address.model.studyspot.Favourite;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link StudySpot}.
 */
class JsonAdaptedStudySpot {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "StudySpot's %s field is missing!";

    private final String name;
    private final String rating;
    private final String operatingHours;
    private final String address;
    private final String favourite;
    private final String studiedHours;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedAmenity> amenities = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudySpot} with the given study spot details.
     */
    @JsonCreator
    public JsonAdaptedStudySpot(@JsonProperty("name") String name, @JsonProperty("rating") String rating,
            @JsonProperty("operatingHours") String operatingHours, @JsonProperty("address") String address,
            @JsonProperty("favourite") String favourite, @JsonProperty("studiedHours") String studiedHours,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
            @JsonProperty("amenities") List<JsonAdaptedAmenity> amenities) {
        this.name = name;
        this.rating = rating;
        this.operatingHours = operatingHours;
        this.address = address;
        this.studiedHours = studiedHours;
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
        operatingHours = source.getOperatingHours().value;
        address = source.getAddress().value;
        studiedHours = source.getStudiedHours().value;
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

        if (operatingHours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OperatingHours.class.getSimpleName()));
        }
        if (!OperatingHours.isValidOperatingHours(operatingHours)) {
            throw new IllegalValueException(OperatingHours.MESSAGE_CONSTRAINTS);
        }
        final OperatingHours modelOperatingHours = new OperatingHours(operatingHours);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (studiedHours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudiedHours.class.getSimpleName()));
        }
        if (!StudiedHours.isValidLoggedHours(studiedHours)) {
            throw new IllegalValueException(StudiedHours.MESSAGE_CONSTRAINTS);
        }
        final StudiedHours modelStudiedHours = new StudiedHours(studiedHours);

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
        return new StudySpot(modelName, modelRating, modelOperatingHours, modelAddress, modelStudiedHours,
                modelFavourite, modelTags, modelAmenities);
    }

}
