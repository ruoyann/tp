package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OPERATING_HOURS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.OPERATING_HOURS_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.OPERATING_HOURS_DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CROWDED;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_QUIET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OPERATING_HOURS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CROWDED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_QUIET;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudySpots.DECK;
import static seedu.address.testutil.TypicalStudySpots.FRONTIER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudySpotBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        StudySpot expectedStudySpot = new StudySpotBuilder(DECK).withTags(VALID_TAG_CROWDED).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_DECK + RATING_DESC_DECK
                + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK + TAG_DESC_CROWDED, new AddCommand(expectedStudySpot));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_FRONTIER + NAME_DESC_DECK + RATING_DESC_DECK
                + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK + TAG_DESC_CROWDED, new AddCommand(expectedStudySpot));

        // multiple ratings - last rating accepted
        assertParseSuccess(parser, NAME_DESC_DECK + RATING_DESC_FRONTIER + RATING_DESC_DECK
                + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK + TAG_DESC_CROWDED, new AddCommand(expectedStudySpot));

        // multiple OPERATING_HOURSs - last OPERATING_HOURS accepted
        assertParseSuccess(parser, NAME_DESC_DECK + RATING_DESC_DECK + OPERATING_HOURS_DESC_FRONTIER
                + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK + TAG_DESC_CROWDED, new AddCommand(expectedStudySpot));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_DECK + RATING_DESC_DECK + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_FRONTIER
                + ADDRESS_DESC_DECK + TAG_DESC_CROWDED, new AddCommand(expectedStudySpot));

        // multiple tags - all accepted
        StudySpot expectedStudySpotMultipleTags = new StudySpotBuilder(DECK)
                .withTags(VALID_TAG_CROWDED, VALID_TAG_QUIET)
                .build();
        assertParseSuccess(parser, NAME_DESC_DECK + RATING_DESC_DECK + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK
                + TAG_DESC_QUIET + TAG_DESC_CROWDED, new AddCommand(expectedStudySpotMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        StudySpot expectedStudySpot = new StudySpotBuilder(FRONTIER).withTags().build();
        assertParseSuccess(parser, NAME_DESC_FRONTIER + RATING_DESC_FRONTIER
                        + OPERATING_HOURS_DESC_FRONTIER + ADDRESS_DESC_FRONTIER,
                        new AddCommand(expectedStudySpot));
    }

    /**
     * Compulsory fields to be tested for are name and rating
     */
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_DECK + RATING_DESC_DECK + OPERATING_HOURS_DESC_DECK
                        + ADDRESS_DESC_DECK,
                expectedMessage);

        // missing rating prefix
        assertParseFailure(parser, NAME_DESC_DECK + VALID_RATING_DECK + OPERATING_HOURS_DESC_DECK
                        + ADDRESS_DESC_DECK,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_DECK + VALID_RATING_DECK + VALID_OPERATING_HOURS_DECK
                        + VALID_ADDRESS_DECK,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + RATING_DESC_DECK + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK
                + TAG_DESC_QUIET + TAG_DESC_CROWDED, Name.MESSAGE_CONSTRAINTS);

        // invalid rating
        assertParseFailure(parser, NAME_DESC_DECK + INVALID_RATING_DESC + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK
                + TAG_DESC_QUIET + TAG_DESC_CROWDED, Rating.MESSAGE_CONSTRAINTS);

        // invalid operating hours
        assertParseFailure(parser, NAME_DESC_DECK + RATING_DESC_DECK + INVALID_OPERATING_HOURS_DESC
                + ADDRESS_DESC_DECK
                + TAG_DESC_QUIET + TAG_DESC_CROWDED, OperatingHours.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_DECK + RATING_DESC_DECK + OPERATING_HOURS_DESC_DECK
                + INVALID_ADDRESS_DESC
                + TAG_DESC_QUIET + TAG_DESC_CROWDED, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_DECK + RATING_DESC_DECK + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK
                + INVALID_TAG_DESC + VALID_TAG_CROWDED, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + RATING_DESC_DECK + OPERATING_HOURS_DESC_DECK
                        + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_DECK + RATING_DESC_DECK
                        + OPERATING_HOURS_DESC_DECK
                + ADDRESS_DESC_DECK + TAG_DESC_QUIET + TAG_DESC_CROWDED,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
