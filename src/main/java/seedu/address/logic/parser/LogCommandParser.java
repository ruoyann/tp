package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudiedHours;


/**
 * Parses input arguments and creates a new LogCommand object
 */
public class LogCommandParser implements Parser<LogCommand> {
    /**
     * Parses the given {@code String} of arguments in context of the LogCommand
     * and returns a LogCommand object for execution
     * @throws ParseException if the user input does not conform to the expected format
     */
    public LogCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FLAG, PREFIX_NAME, PREFIX_HOURS);

        boolean isNamePresent = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean isHoursPresent = argMultimap.getValue(PREFIX_HOURS).isPresent();
        StudiedHours hoursStudied = isHoursPresent
                ? ParserUtil.parseStudiedHours(argMultimap.getValue(PREFIX_HOURS).get()) : null;
        Name studySpot = isNamePresent ? ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()) : null;

        if (argMultimap.getValue(PREFIX_FLAG).isPresent()) {
            String flag = argMultimap.getValue(PREFIX_FLAG).get();

            if (flag.equals(LogCommand.FLAG_RESET) && isNamePresent) {
                return new LogCommand(studySpot, null, true, false, false);
            } else if (flag.equals(LogCommand.FLAG_OVERRIDE) && isNamePresent && isHoursPresent) {
                return new LogCommand(studySpot, hoursStudied, false, true, false);
            } else if (flag.equals(LogCommand.FLAG_RESET_ALL)) {
                return new LogCommand(studySpot, null, false, false, true);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
            }
        } else if (isNamePresent && isHoursPresent) {
            return new LogCommand(studySpot, hoursStudied, false, false, false);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
        }
    }
}
