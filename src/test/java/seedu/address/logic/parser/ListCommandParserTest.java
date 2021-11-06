package seedu.address.logic.parser;

import static seedu.address.logic.commands.ListCommand.containsAmenity;
import static seedu.address.logic.commands.ListCommand.containsTag;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;
import static seedu.address.model.Model.PREDICATE_SHOW_FAVOURITES;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, "1", new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null, null, null));
    }

    @Test
    public void parse_emptyTags_throwsParseException() {
        assertParseFailure(parser, " -t t/", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingTags_throwsParseException() {
        assertParseFailure(parser, " -t", ListCommand.MESSAGE_MISSING_TAGS);
    }

    @Test
    public void parse_emptyAmenities_throwsParseException() {
        assertParseFailure(parser, " -m m/", String.format(Amenity.MESSAGE_CONSTRAINTS,
                Amenity.listAllAmenityTypes(Amenity.VALID_TYPES)));
    }

    @Test
    public void parse_missingAmenities_throwsParseException() {
        assertParseFailure(parser, " -m", ListCommand.MESSAGE_MISSING_AMENITIES);
    }

    @Test
    public void parse_emptyRating_throwsParseException() {
        assertParseFailure(parser, " -r r/", Rating.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingRating_throwsParseException() {
        assertParseFailure(parser, " -r", ListCommand.MESSAGE_MISSING_RATING);
    }

    @Test
    public void parse_unknownFlags_throwsParseException() {
        assertParseFailure(parser, " -s", ListCommand.MESSAGE_UNKNOWN_FLAGS);
    }

    @Test
    public void parse_favouriteFlag_returnsListCommand() {
        assertParseSuccess(parser, " -f", new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS.and(PREDICATE_SHOW_FAVOURITES),
                true, null, null, null));
    }

    @Test
    public void parse_tags_returnsListCommand() {
        Tag coldTag = new Tag("cold");
        Tag warmTag = new Tag("warm");
        Set<Tag> tagSet = new HashSet<Tag>(Arrays.asList(coldTag, warmTag));
        Predicate<StudySpot> predicate = containsTag(coldTag).and(containsTag(warmTag));
        assertParseSuccess(parser, " -t t/cold t/warm", new ListCommand(predicate, false, tagSet, null, null));
    }

    @Test
    public void parse_amenities_returnsListCommand() {
        Amenity wifi = new Amenity("wifi");
        Amenity charger = new Amenity("charger");
        Set<Amenity> amenitySet = new HashSet<>(Arrays.asList(wifi, charger));
        Predicate<StudySpot> predicate = containsAmenity(wifi).and(containsAmenity(charger));
        assertParseSuccess(parser, " -m m/wifi m/charger", new ListCommand(predicate, false, null, amenitySet, null));
    }
}
