package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RATING_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
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
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " spot/Test", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, " spot/Test" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " spot/Test" + INVALID_RATING_DESC, Rating.MESSAGE_CONSTRAINTS); // invalid phone
        //  No invalid emails
        //  assertParseFailure(parser, " spot/Test" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, " spot/Test" + INVALID_ADDRESS_DESC,
                Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, " spot/Test" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, " spot/Test" + INVALID_RATING_DESC + EMAIL_DESC_AMY,
                Rating.MESSAGE_CONSTRAINTS);

        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, " spot/Test" + RATING_DESC_BOB + INVALID_RATING_DESC,
                Rating.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code StudySpot} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, " spot/Test" + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " spot/Test" + TAG_DESC_FRIEND + TAG_EMPTY
                + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " spot/Test" + TAG_EMPTY + TAG_DESC_FRIEND
                + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " spot/Test"
                        + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_RATING_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    //todo: abstract out " spot/"
    @Test
    public void parse_allFieldsSpecified_success() {
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = " spot/" + targetName.fullName + RATING_DESC_BOB + TAG_DESC_HUSBAND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_AMY)
                .withRating(VALID_RATING_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = " spot/" + targetName.fullName + RATING_DESC_BOB + EMAIL_DESC_AMY;

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = " spot/" + targetName.fullName + NAME_DESC_AMY;
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = " spot/" + targetName.fullName + RATING_DESC_AMY;
        descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_AMY).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = " spot/" + targetName.fullName + EMAIL_DESC_AMY;
        descriptor = new EditStudySpotDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = " spot/" + targetName.fullName + ADDRESS_DESC_AMY;
        descriptor = new EditStudySpotDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = " spot/" + targetName.fullName + TAG_DESC_FRIEND;
        descriptor = new EditStudySpotDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = " spot/" + targetName.fullName + RATING_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + RATING_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + RATING_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = " spot/" + targetName.fullName + INVALID_RATING_DESC + RATING_DESC_BOB;
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = " spot/" + targetName.fullName + EMAIL_DESC_BOB + INVALID_RATING_DESC + ADDRESS_DESC_BOB
                + RATING_DESC_BOB;
        descriptor = new EditStudySpotDescriptorBuilder().withRating(VALID_RATING_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).build();
        expectedCommand = new EditCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = " spot/" + targetName.fullName + TAG_EMPTY;

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
