package seedu.address.logic.parser;

import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudiedHours;

import java.util.NoSuchElementException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

public class LogCommandParser implements Parser<LogCommand> {
    public LogCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_LOG_COMMAND);
        Name studySpot;
        StudiedHours hoursStudied;
        try {
            studySpot = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            hoursStudied = ParserUtil.parseStudiedHours(argMultimap.getValue(PREFIX_LOG_COMMAND).get());
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
        }
        return new LogCommand(studySpot, hoursStudied);
    }
}
