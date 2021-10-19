package seedu.address.logic.parser;

import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudiedHours;

import java.util.NoSuchElementException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

public class LogCommandParser implements Parser<LogCommand> {
    public LogCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FLAG, PREFIX_NAME, PREFIX_HOURS);
        Name studySpot;
        StudiedHours hoursStudied;

        try {
            studySpot = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

            String flag = null;
            if (argMultimap.getValue(PREFIX_FLAG).isPresent()) {
                // Only one flag should be present
                if (argMultimap.getAllValues(PREFIX_FLAG).size() != 1) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_ONE_FLAG));
                }
                flag = argMultimap.getValue(PREFIX_FLAG).get();
                if (flag.equals("r")) {
                    return new LogCommand(studySpot, null, flag);
                }
            }
            hoursStudied = ParserUtil.parseStudiedHours(argMultimap.getValue(PREFIX_HOURS).get());
            return new LogCommand(studySpot, hoursStudied, flag);
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
        }

    }
}
