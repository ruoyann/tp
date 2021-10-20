package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedStudySpot.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.CENTRAL_LIBRARY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Favourite;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudiedHours;

public class JsonAdaptedStudySpotTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_RATING = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_OPERATING_HOURS = "9-10, 9-6";
    private static final String INVALID_FAVOURITE = "falseortrue";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_AMENITY = "carpark";
    private static final String INVALID_STUDIED_HOURS = "2.34";

    private static final String VALID_NAME = CENTRAL_LIBRARY.getName().toString();
    private static final String VALID_RATING = CENTRAL_LIBRARY.getRating().toString();
    private static final String VALID_OPERATING_HOURS = CENTRAL_LIBRARY.getOperatingHours().toString();
    private static final String VALID_ADDRESS = CENTRAL_LIBRARY.getAddress().toString();
    private static final String VALID_FAVOURITE = CENTRAL_LIBRARY.getFavourite().toString();
    private static final String VALID_STUDIED_HOURS = CENTRAL_LIBRARY.getStudiedHours().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = CENTRAL_LIBRARY.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedAmenity> VALID_AMENITIES = CENTRAL_LIBRARY.getAmenities().stream()
            .map(JsonAdaptedAmenity::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validStudySpotDetails_returnsStudySpot() throws Exception {
        JsonAdaptedStudySpot studySpot = new JsonAdaptedStudySpot(CENTRAL_LIBRARY);
        assertEquals(CENTRAL_LIBRARY, studySpot.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(INVALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, VALID_ADDRESS,
                        VALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot = new JsonAdaptedStudySpot(null, VALID_RATING,
                VALID_OPERATING_HOURS, VALID_ADDRESS, VALID_FAVOURITE, VALID_STUDIED_HOURS,
                VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, INVALID_RATING, VALID_OPERATING_HOURS, VALID_ADDRESS,
                        VALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = Rating.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot = new JsonAdaptedStudySpot(VALID_NAME, null, VALID_OPERATING_HOURS,
                VALID_ADDRESS, VALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_invalidOperatingHours_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, INVALID_OPERATING_HOURS, VALID_ADDRESS,
                        VALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = OperatingHours.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_nullOperatingHours_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot = new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, null,
                VALID_ADDRESS, VALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OperatingHours.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, INVALID_ADDRESS,
                        VALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, null,
                        VALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_invalidFavourite_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, VALID_ADDRESS,
                        INVALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = Favourite.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_nullFavourite_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, VALID_ADDRESS,
                        null, VALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Favourite.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_invalidStudiedHours_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, VALID_ADDRESS,
                        VALID_FAVOURITE, INVALID_STUDIED_HOURS, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = StudiedHours.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_nullStudiedHours_throwsIllegalValueException() {
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, VALID_ADDRESS,
                        VALID_FAVOURITE, null, VALID_TAGS, VALID_AMENITIES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudiedHours.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, studySpot::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, VALID_ADDRESS,
                        VALID_FAVOURITE, VALID_STUDIED_HOURS, invalidTags, VALID_AMENITIES);
        assertThrows(IllegalValueException.class, studySpot::toModelType);
    }

    @Test
    public void toModelType_invalidAmenityTypes_throwsIllegalValueException() {
        List<JsonAdaptedAmenity> invalidAmenityTypes = new ArrayList<>(VALID_AMENITIES);
        invalidAmenityTypes.add(new JsonAdaptedAmenity(INVALID_AMENITY));
        JsonAdaptedStudySpot studySpot =
                new JsonAdaptedStudySpot(VALID_NAME, VALID_RATING, VALID_OPERATING_HOURS, VALID_ADDRESS,
                        VALID_FAVOURITE, VALID_STUDIED_HOURS, VALID_TAGS, invalidAmenityTypes);
        assertThrows(IllegalValueException.class, studySpot::toModelType);
    }
}
