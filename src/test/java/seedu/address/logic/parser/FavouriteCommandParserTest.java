package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.model.studyspot.Name;

public class FavouriteCommandParserTest {

    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        String userInput = " " + PREFIX_NAME + STARBUCKS.getName().fullName;
        assertParseSuccess(parser, userInput, new FavouriteCommand(STARBUCKS.getName()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = " " + PREFIX_NAME;
        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }
}
