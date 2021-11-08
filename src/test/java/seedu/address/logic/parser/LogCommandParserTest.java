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

        // Starbucks hr/5 -> missing n/ prefix
        String userInput = " " + name.fullName + " " + PREFIX_HOURS + "5";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));

        // -o n/Starbucks -> missing hr/
        String invalidOverrideHourMissing = " " + PREFIX_NAME + name.fullName;
        assertParseFailure(parser, invalidOverrideHourMissing, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LogCommand.MESSAGE_USAGE));

        // -o hr/5 -> missing n/ prefix
        String invalidOverrideMissingName = " " + PREFIX_HOURS + "5";
        assertParseFailure(parser, invalidOverrideMissingName, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LogCommand.MESSAGE_USAGE));

        // -o -> missing name, not reset all
        String invalidNoNameAndNotResetAll = "-o";
        assertParseFailure(parser, invalidNoNameAndNotResetAll, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LogCommand.MESSAGE_USAGE));

        // test preamble n/Starbucks -o -> missing hr/ when not reset all
        String invalidNamePresentPreamblePresentNotResetAll = " test preamble " + PREFIX_NAME + name.fullName + " -o";
        assertParseFailure(parser, invalidNamePresentPreamblePresentNotResetAll, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE
        ));
    }

    @Test
    public void parse_moreThanOneFlag_failure() {
        Name name = STARBUCKS.getName();
        String userInput = " -r " + "-o " + PREFIX_NAME + name.fullName;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                            LogCommand.MESSAGE_ONE_FLAG));
    }

    @Test
    public void parse_validFlags_success() {
        Name name = STARBUCKS.getName();

        // -r n/Starbucks
        String validResetInput = " -r " + PREFIX_NAME + name.fullName;
        assertParseSuccess(parser, validResetInput, new LogCommand(STARBUCKS.getName(),
                null, true, false, false));

        // -r n/Starbucks hr/3, should disregard hr/3
        String validResetInputWithHour = " -r " + PREFIX_NAME + name.fullName + " " + PREFIX_HOURS + "3";
        assertParseSuccess(parser, validResetInputWithHour, new LogCommand(name,
                null, true, false, false));

        // -o n/Starbucks hr/3
        String validOverrideInput = " -o " + PREFIX_NAME + name.fullName + " " + PREFIX_HOURS + "3";
        assertParseSuccess(parser, validOverrideInput, new LogCommand(name, new StudiedHours("3"),
                false, true, false));

        // -ra
        String validResetAllInput = " -ra ";
        assertParseSuccess(parser, validResetAllInput, new LogCommand(null,
                null, false, false, true));

        // -ra n/Starbucks
        String validResetAllInputWithName = " -ra " + PREFIX_NAME + name.fullName;
        assertParseSuccess(parser, validResetAllInputWithName, new LogCommand(name,
                null, false, false, true));
    }
}
