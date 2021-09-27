package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CROWDED;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_QUIET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CROWDED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_QUIET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudySpotDescriptor;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditStudySpotDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_FRONTIER, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " spot/Test", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_FRONTIER, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_FRONTIER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " spot/Test" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " spot/Test" + INVALID_RATING_DESC, Rating.MESSAGE_CONSTRAINTS); // invalid rating
        //  No invalid emails
        //  assertParseFailure(parser, " spot/Test" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, " spot/Test" + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, " spot/Test" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid rating followed by valid email
        assertParseFailure(parser, " spot/Test" + INVALID_RATING_DESC + EMAIL_DESC_FRONTIER,
                Rating.MESSAGE_CONSTRAINTS);

        // valid rating followed by invalid rating. The test case for invalid rating followed by valid rating
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, " spot/Test" + RATING_DESC_DECK + INVALID_RATING_DESC,
                Rating.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code StudySpot} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, " spot/Test" + TAG_DESC_CROWDED
                + TAG_DESC_QUIET + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " spot/Test" + TAG_DESC_CROWDED + TAG_EMPTY
                + TAG_DESC_QUIET, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " spot/Test" + TAG_EMPTY + TAG_DESC_CROWDED
                + TAG_DESC_QUIET, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " spot/Test"
                        + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_FRONTIER + VALID_RATING_FRONTIER,
                Name.MESSAGE_CONSTRAINTS);
    }

    //todo: abstract out " spot/"
    @Test
    public void parse_allFieldsSpecified_success() {
        Name targetName = new Name(VALID_NAME_FRONTIER);
        String userInput = " spot/" + targetName.fullName + RATING_DESC_DECK + TAG_DESC_QUIET
                + EMAIL_DESC_FRONTIER + ADDRESS_DESC_FRONTIER + NAME_DESC_FRONTIER + TAG_DESC_CROWDED;

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_FRONTIER)
                .withRating(VALID_RATING_DECK).withEmail(VALID_EMAIL_FRONTIER).withAddress(VALID_ADDRESS_FRONTIER)
                .withTags(VALID_TAG_QUIET, VALID_TAG_CROWDED).build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Name targetName = new Name(VALID_NAME_FRONTIER);
        String userInput = " spot/" + targetName.fullName + RATING_DESC_DECK + EMAIL_DESC_FRONTIER;

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_DECK)
                .withEmail(VALID_EMAIL_FRONTIER).build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Name targetName = new Name(VALID_NAME_FRONTIER);
        String userInput = " spot/" + targetName.fullName + NAME_DESC_FRONTIER;
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_FRONTIER).build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // rating
        userInput = " spot/" + targetName.fullName + RATING_DESC_FRONTIER;
        descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_FRONTIER).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = " spot/" + targetName.fullName + EMAIL_DESC_FRONTIER;
        descriptor = new EditStudySpotDescriptorBuilder().withEmail(VALID_EMAIL_FRONTIER).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = " spot/" + targetName.fullName + ADDRESS_DESC_FRONTIER;
        descriptor = new EditStudySpotDescriptorBuilder().withAddress(VALID_ADDRESS_FRONTIER).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = " spot/" + targetName.fullName + TAG_DESC_CROWDED;
        descriptor = new EditStudySpotDescriptorBuilder().withTags(VALID_TAG_CROWDED).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Name targetName = new Name(VALID_NAME_FRONTIER);
        String userInput = " spot/" + targetName.fullName + RATING_DESC_FRONTIER + ADDRESS_DESC_FRONTIER + EMAIL_DESC_FRONTIER
                + TAG_DESC_CROWDED + RATING_DESC_FRONTIER + ADDRESS_DESC_FRONTIER + EMAIL_DESC_FRONTIER + TAG_DESC_CROWDED
                + RATING_DESC_DECK + ADDRESS_DESC_DECK + EMAIL_DESC_DECK + TAG_DESC_QUIET;

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_DECK)
                .withEmail(VALID_EMAIL_DECK).withAddress(VALID_ADDRESS_DECK).withTags(VALID_TAG_CROWDED, VALID_TAG_QUIET)
                .build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Name targetName = new Name(VALID_NAME_FRONTIER);
        String userInput = " spot/" + targetName.fullName + INVALID_RATING_DESC + RATING_DESC_DECK;
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_DECK).build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = " spot/" + targetName.fullName + EMAIL_DESC_DECK + INVALID_RATING_DESC + ADDRESS_DESC_DECK
                + RATING_DESC_DECK;
        descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_DECK).withEmail(VALID_EMAIL_DECK)
                .withAddress(VALID_ADDRESS_DECK).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Name targetName = new Name(VALID_NAME_FRONTIER);
        String userInput = " spot/" + targetName.fullName + TAG_EMPTY;

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
