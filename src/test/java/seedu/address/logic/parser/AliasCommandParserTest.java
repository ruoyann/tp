package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ALIAS_ARGUMENTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_USER_ALIAS_LS;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_USER_ALIAS_PWD;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_USER_COMMAND_EXIT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_USER_COMMAND_LIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALIAS_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USER_ALIAS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_ALIAS_SHOW;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CROWDED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_COMMAND_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_LS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.model.alias.Alias;

class AliasCommandParserTest {
    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        Alias expectedAlias = new Alias(VALID_ALIAS_LS, VALID_ALIAS_COMMAND_LIST);
        AliasCommand expectedNoShowCommand = new AliasCommand(false, expectedAlias);
        AliasCommand expectedShowCommand = new AliasCommand(true);

        // show preamble, no arguments
        assertParseSuccess(parser, PREAMBLE_ALIAS_SHOW,
                expectedShowCommand);

        // show preamble, arguments correctly ignored
        assertParseSuccess(parser, PREAMBLE_ALIAS_SHOW + ALIAS_USER_ALIAS_LS + ALIAS_USER_COMMAND_LIST,
                expectedShowCommand);

        // show preamble, incorrect arguments also correctly ignored
        assertParseSuccess(parser, PREAMBLE_ALIAS_SHOW + ALIAS_USER_ALIAS_LS + ALIAS_USER_COMMAND_LIST
                + ADDRESS_DESC_DECK + TAG_DESC_CROWDED,
                expectedShowCommand);

        // empty preamble, correct arguments
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ALIAS_USER_ALIAS_LS + ALIAS_USER_COMMAND_LIST,
                expectedNoShowCommand);

        // multiple userAliases - last one accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ALIAS_USER_ALIAS_LS + ALIAS_USER_ALIAS_PWD
                + ALIAS_USER_COMMAND_LIST + ALIAS_USER_ALIAS_LS , expectedNoShowCommand);

        // multiple commandWords - last one accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ALIAS_USER_ALIAS_LS + ALIAS_USER_COMMAND_LIST
                + ALIAS_USER_COMMAND_EXIT + ALIAS_USER_COMMAND_LIST , expectedNoShowCommand);
    }

    /**
     * Compulsory fields to be tested for are userAlias and commandWord
     */
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE);

        // missing userAlias prefix
        assertParseFailure(parser, VALID_ALIAS_LS + ALIAS_USER_COMMAND_LIST,
                expectedMessage);

        // missing commandWord prefix
        assertParseFailure(parser, ALIAS_USER_ALIAS_LS + VALID_ALIAS_COMMAND_LIST,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ALIAS_LS + VALID_ALIAS_COMMAND_LIST,
                expectedMessage);
    }

    @Test
    void parse_invalidValues_failure() {

        // extraneous arguments before alias prefixes - error
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                        + ADDRESS_DESC_DECK + TAG_DESC_CROWDED
                        + ALIAS_USER_ALIAS_LS + ALIAS_USER_COMMAND_LIST,
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));

        // using a command word in userAlias argument
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                + INVALID_USER_ALIAS + ALIAS_USER_COMMAND_LIST,
                String.format(MESSAGE_INVALID_ALIAS_ARGUMENTS, Alias.MESSAGE_ALIAS_CONSTRAINTS));

        // using a non-command word as commandWord argument
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                        + ALIAS_USER_ALIAS_LS + INVALID_ALIAS_COMMAND,
                String.format(MESSAGE_INVALID_ALIAS_ARGUMENTS, Alias.MESSAGE_COMMAND_CONSTRAINTS));

    }
}
