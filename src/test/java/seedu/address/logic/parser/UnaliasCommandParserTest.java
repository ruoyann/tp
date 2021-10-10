package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ALIAS_ARGUMENTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_USER_ALIAS_LS;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_USER_ALIAS_PWD;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_USER_COMMAND_LIST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_USER_ALIAS;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CROWDED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_COMMAND_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_LS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.model.alias.Alias;

class UnaliasCommandParserTest {
    private UnaliasCommandParser parser = new UnaliasCommandParser();

    @Test
    void parse_allFieldsPresent_success() {
        Alias expectedAlias = new Alias(VALID_ALIAS_LS, VALID_ALIAS_COMMAND_LIST);
        UnaliasCommand expectedCommand = new UnaliasCommand(expectedAlias);

        // empty preamble, correct arguments
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ALIAS_USER_ALIAS_LS,
                expectedCommand);

        // multiple userAliases - last one accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ALIAS_USER_ALIAS_LS + ALIAS_USER_ALIAS_PWD
                + ALIAS_USER_ALIAS_LS , expectedCommand);
    }

    /**
     * Compulsory fields to be tested for are userAlias
     */
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE);

        // missing userAlias prefix
        assertParseFailure(parser, VALID_ALIAS_LS, expectedMessage);
    }

    @Test
    void parse_invalidValues_failure() {
        // extraneous arguments before alias prefixes - error
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                        + ADDRESS_DESC_DECK + TAG_DESC_CROWDED
                        + ALIAS_USER_ALIAS_LS,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));

        // using a command word in userAlias argument
        assertParseFailure(parser, PREAMBLE_WHITESPACE
                        + INVALID_USER_ALIAS + ALIAS_USER_COMMAND_LIST,
                String.format(MESSAGE_INVALID_ALIAS_ARGUMENTS, Alias.MESSAGE_ALIAS_CONSTRAINTS));
    }

}
