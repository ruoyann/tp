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
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, "1", new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null, null));
    }

    @Test
    public void parse_emptyTags_throwsParseException() {
        assertParseFailure(parser, " -t t/", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_favouriteFlag_returnsListCommand() {
        assertParseSuccess(parser, " -f", new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS.and(PREDICATE_SHOW_FAVOURITES),
                true, null, null));
    }

    @Test
    public void parse_tags_returnsListCommand() {
        Tag coldTag = new Tag("cold");
        Tag warmTag = new Tag("warm");
        Set<Tag> tagSet = new HashSet<Tag>(Arrays.asList(coldTag, warmTag));
        Predicate<StudySpot> predicate = containsTag(coldTag).and(containsTag(warmTag));
        assertParseSuccess(parser, " -t t/cold t/warm", new ListCommand(predicate, false, tagSet, null));
    }

    @Test
    public void parse_amenities_returnsListCommand() {
        Amenity wifi = new Amenity("wifi");
        Amenity charger = new Amenity("charger");
        Set<Amenity> amenitySet = new HashSet<>(Arrays.asList(wifi, charger));
        Predicate<StudySpot> predicate = containsAmenity(wifi).and(containsAmenity(charger));
        assertParseSuccess(parser, " -m m/wifi m/charger", new ListCommand(predicate, false, null, amenitySet));
    }
}
