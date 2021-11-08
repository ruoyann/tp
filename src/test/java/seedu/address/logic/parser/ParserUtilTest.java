package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SPOT;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudySpotBuilder;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_RATING = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_OPERATING_HOURS = "9-10, 9-6";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_TAG_WITH_SPACING = "very crowded";
    private static final String INVALID_AMENITY = "wifii";
    private static final String INVALID_STUDIED_HOURS = "hour";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_RATING = "3";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_OPERATING_HOURS = "0900-2200, 0900-1800";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_TAG_49_CHAR = "thisPlaceIsVeryCrowdedAndNoisyyyyyyyyyyyyyyyyyyyy";
    private static final String VALID_AMENITY_1 = "aircon";
    private static final String VALID_AMENITY_2 = "food";
    private static final String VALID_STUDIED_HOURS = "5";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_SPOT, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_SPOT, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseRating_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRating((String) null));
    }

    @Test
    public void parseRating_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRating(INVALID_RATING));
    }

    @Test
    public void parseRating_validValueWithoutWhitespace_returnsRating() throws Exception {
        Rating expectedRating = new Rating(VALID_RATING);
        assertEquals(expectedRating, ParserUtil.parseRating(VALID_RATING));
    }

    @Test
    public void parseRating_validValueWithWhitespace_returnsTrimmedRating() throws Exception {
        String ratingWithWhitespace = WHITESPACE + VALID_RATING + WHITESPACE;
        Rating expectedRating = new Rating(VALID_RATING);
        assertEquals(expectedRating, ParserUtil.parseRating(ratingWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseOperatingHours_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseOperatingHours((String) null));
    }

    @Test
    public void parseOperatingHours_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOperatingHours(INVALID_OPERATING_HOURS));
    }

    @Test
    public void parseOperatingHours_validValueWithoutWhitespace_returnsOperatingHours() throws Exception {
        OperatingHours expectedOperatingHours = new OperatingHours(VALID_OPERATING_HOURS);
        assertEquals(expectedOperatingHours, ParserUtil.parseOperatingHours(VALID_OPERATING_HOURS));
    }

    @Test
    public void parseOperatingHours_validValueWithWhitespace_returnsTrimmedOperatingHours() throws Exception {
        String operatingHoursWithWhitespace = WHITESPACE + VALID_OPERATING_HOURS + WHITESPACE;
        OperatingHours expectedOperatingHours = new OperatingHours(VALID_OPERATING_HOURS);
        assertEquals(expectedOperatingHours, ParserUtil.parseOperatingHours(operatingHoursWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTag_containsWhiteSpace_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(" "));
    }

    @Test
    public void parseTag_exceedsMaxLength_throwsParseException() {
        assertDoesNotThrow(() -> ParserUtil.parseTag(VALID_TAG_49_CHAR));

        String invalidTagFiftyChar = VALID_TAG_49_CHAR + "y";
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(invalidTagFiftyChar));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseAmenity_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmenity(null));
    }

    @Test
    public void parseAmenity_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAmenity(INVALID_AMENITY));
    }

    @Test
    public void parseAmenity_validValueWithoutWhitespace_returnsAmenity() throws Exception {
        Amenity expectedAmenity = new Amenity(VALID_AMENITY_1);
        assertEquals(expectedAmenity, ParserUtil.parseAmenity(VALID_AMENITY_1));
    }

    @Test
    public void parseAmenity_validValueWithWhitespace_returnsTrimmedAmenity() throws Exception {
        String amenityWithWhitespace = WHITESPACE + VALID_AMENITY_1 + WHITESPACE;
        Amenity expectedAmenity = new Amenity(VALID_AMENITY_1);
        assertEquals(expectedAmenity, ParserUtil.parseAmenity(amenityWithWhitespace));
    }

    @Test
    public void parseAmenities_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAmenities(null));
    }

    @Test
    public void parseAmenities_collectionWithInvalidAmenities_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAmenities(Arrays.asList(VALID_AMENITY_1,
                INVALID_AMENITY)));
    }

    @Test
    public void parseAmenities_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseAmenities(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseAmenities_collectionWithValidAmenities_returnsAmenitySet() throws Exception {
        Set<Amenity> actualAmenitySet = ParserUtil.parseAmenities(Arrays.asList(VALID_AMENITY_1, VALID_AMENITY_2));
        Set<Amenity> expectedAmenitySet = new HashSet<Amenity>(Arrays.asList(new Amenity(VALID_AMENITY_1),
                new Amenity(VALID_AMENITY_2)));

        assertEquals(expectedAmenitySet, actualAmenitySet);
    }

    @Test
    public void parseStudiedHours_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseStudiedHours((String) null));
    }

    @Test
    public void parseStudiedHours_emptyValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudiedHours(""));
    }

    @Test
    public void parseStudiedHours_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudiedHours(INVALID_STUDIED_HOURS));
    }

    @Test
    public void parseStudiedHours_validValueWithoutWhitespace_returnsStudiedHours() throws Exception {
        StudiedHours expectedStudiedHours = new StudiedHours(VALID_STUDIED_HOURS);
        assertEquals(expectedStudiedHours, ParserUtil.parseStudiedHours(VALID_STUDIED_HOURS));
    }

    @Test
    public void parseStudiedHours_validValueWithWhitespace_returnsTrimmedStudiedHours() throws Exception {
        String studiedHoursWithWhitespace = WHITESPACE + VALID_STUDIED_HOURS + WHITESPACE;
        StudiedHours expectedStudiedHours = new StudiedHours(VALID_STUDIED_HOURS);
        assertEquals(expectedStudiedHours, ParserUtil.parseStudiedHours(studiedHoursWithWhitespace));
    }

    @Test
    public void parseStudiedHours_negativeValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseStudiedHours("-10"));
    }

    @Test
    public void parseStudiedHours_largerThanMaxInteger_throwsParseException() {
        String maxIntegerPlusOne = "2147483648";
        assertThrows(ParseException.class, () -> ParserUtil.parseStudiedHours(maxIntegerPlusOne));
    }

    @Test
    public void parseFlags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFlags(null));
    }

    @Test
    public void parseFlags_validValue_returnsStudySpotPredicate() throws Exception {
        // favourite flag
        ArgumentMultimap argumentMultimapFavourite = ArgumentTokenizer.tokenize(" -f", PREFIX_FLAG);
        Predicate<StudySpot> predicateReturnedForFavourite = ParserUtil.parseFlags(argumentMultimapFavourite);
        StudySpot favouriteSpot = new StudySpotBuilder().withFavourite(true).build();
        StudySpot unfavouriteSpot = new StudySpotBuilder().withFavourite(false).build();

        assertTrue(predicateReturnedForFavourite.test(favouriteSpot));
        assertFalse(predicateReturnedForFavourite.test(unfavouriteSpot));

        // tag flag
        String tagArguments = " -t t/quiet";
        ArgumentMultimap argumentMultimapTag = ArgumentTokenizer.tokenize(tagArguments, PREFIX_FLAG);
        Predicate<StudySpot> predicateReturnedForTag = ParserUtil.parseFlags(argumentMultimapTag);
        StudySpot taggedSpot = new StudySpotBuilder().withTags("quiet").build();

        assertTrue(predicateReturnedForTag.test(taggedSpot));

        // amenity flag
        String amenityArguments = " -m m/wifi";
        ArgumentMultimap argumentMultimapAmenity = ArgumentTokenizer.tokenize(amenityArguments, PREFIX_FLAG);
        Predicate<StudySpot> predicateReturnedForAmenity = ParserUtil.parseFlags(argumentMultimapAmenity);
        StudySpot spotWithAmenity = new StudySpotBuilder().withAmenities("wifi").build();

        assertTrue(predicateReturnedForAmenity.test(spotWithAmenity));

        // rating flag
        String ratingArguments = " -r r/4";
        ArgumentMultimap argumentMultimapRating = ArgumentTokenizer.tokenize(ratingArguments, PREFIX_FLAG);
        Predicate<StudySpot> predicateReturnedForRating = ParserUtil.parseFlags(argumentMultimapRating);
        StudySpot spotWithRating = new StudySpotBuilder().withRating("4").build();

        assertTrue(predicateReturnedForRating.test(spotWithRating));
    }

}
