package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_SPOT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.studyspot.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // White space only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_DECK,
                new DeleteCommand(new Name(VALID_NAME_DECK)));

        // Correct format
        assertParseSuccess(parser, " " + PREFIX_DELETE_SPOT + VALID_NAME_DECK,
                new DeleteCommand(new Name(VALID_NAME_DECK)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

        assertParseFailure(parser, " abc", expectedMessage);

        String userInput = PREAMBLE_WHITESPACE + " abc";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        String userInput = " " + PREFIX_DELETE_SPOT;
        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);
    }

    /**
     * Compulsory fields to be tested for are name
     */
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

        // Missing name parameter and name field
        assertParseFailure(parser, "", expectedMessage);

        // Missing name parameter
        assertParseFailure(parser, VALID_NAME_DECK, expectedMessage);
    }

    @Test
    public void parser_extraPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PREFIX_DELETE_SPOT + VALID_NAME_DECK, expectedMessage);
    }

    @Test
    public void parser_extraPreambleMissingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_NAME_DECK, expectedMessage);
    }
}
