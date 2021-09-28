package seedu.address.model.amenity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents an Amenity in the study tracker.
 * Guarantees: immutable; type is valid as declared in {@link #isValidAmenityType(String)}
 */
public class Amenity {

    public static final String MESSAGE_CONSTRAINTS = "Amenities should only be of the types: %s.";
    public static final String[] VALID_TYPES = {"wifi"};

    public final String amenityType;

    /**
     * Constructs a {@code Amenity}.
     *
     * @param amenityType A valid amenity type.
     */
    public Amenity(String amenityType) {
        requireNonNull(amenityType);
        checkArgument(isValidAmenityType(amenityType), String.format(MESSAGE_CONSTRAINTS, listAllAmenityTypes()));
        this.amenityType = amenityType;
    }

    /**
     * Returns true if a given string is a valid amenity type
     */
    public static boolean isValidAmenityType(String test) {
        return Arrays.asList(VALID_TYPES).contains(test);
    }

    public static String listAllAmenityTypes() {
        if (VALID_TYPES.length == 1) {
            return VALID_TYPES[0];
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < VALID_TYPES.length - 1; i++) {
                sb.append(VALID_TYPES[i]);
                sb.append(", ");
            }
            sb.append(VALID_TYPES[VALID_TYPES.length - 1]);
            return sb.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.amenity.Amenity // instanceof handles nulls
                && amenityType.equals(((seedu.address.model.amenity.Amenity) other).amenityType)); // state check
    }

    @Override
    public int hashCode() {
        return amenityType.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + amenityType + ']';
    }

}

