package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LogCommand;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudiedHours;

public class LogCommandParserTest {
    private LogCommandParser parser = new LogCommandParser();

    @Test
    public void parse_validArgs_returnsLogCommand() {
        Name name = STARBUCKS.getName();
        StudiedHours studiedHours = new StudiedHours("5");
        String userInput = " " + PREFIX_NAME + name.fullName + " " + PREFIX_HOURS + "5";
        assertParseSuccess(parser, userInput, new LogCommand(STARBUCKS.getName(), studiedHours, false,
                false, false));
    }

    @Test
    public void parse_invalidFormat_failure() {
        Name name = STARBUCKS.getName();
        String userInput = " " + PREFIX_NAME + name.fullName + " " + "5";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_moreThanOneFlag_failure() {
        Name name = STARBUCKS.getName();
        String userInput = " -r " + "-o " + PREFIX_NAME + name.fullName;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                            LogCommand.MESSAGE_INVALID_FLAG));
    }
}
