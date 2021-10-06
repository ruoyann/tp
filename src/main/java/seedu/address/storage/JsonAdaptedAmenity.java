package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.amenity.Amenity;

/**
 * Jackson-friendly version of {@link Amenity}.
 */
class JsonAdaptedAmenity {

    private final String amenityType;

    /**
     * Constructs a {@code JsonAdaptedAmenity} with the given {@code amenityType}.
     */
    @JsonCreator
    public JsonAdaptedAmenity(String amenityType) {
        this.amenityType = amenityType;
    }

    /**
     * Converts a given {@code Amenity} into this class for Jackson use.
     */
    public JsonAdaptedAmenity(Amenity source) {
        amenityType = source.amenityType;
    }

    @JsonValue
    public String getAmenityType() {
        return amenityType;
    }

    /**
     * Converts this Jackson-friendly adapted amenity object into the model's {@code Amenity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted amenity.
     */
    public Amenity toModelType() throws IllegalValueException {
        if (!Amenity.isValidAmenityType(amenityType)) {
            throw new IllegalValueException(String.format(Amenity.MESSAGE_CONSTRAINTS,
                    Amenity.listAllAmenityTypes(Amenity.VALID_TYPES)));
        }
        return new Amenity(amenityType);
    }

}
