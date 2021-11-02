package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.NoSuchElementException;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FLAG, PREFIX_NAME, PREFIX_HOURS);

        // If command has no n/ and it's not a reset all command
        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) && !args.contains(" -ra")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
        }

        Name studySpot;
        StudiedHours hoursStudied;
        boolean isOverride = false;

        try {
            boolean isNamePresent = argMultimap.getValue(PREFIX_NAME).isPresent();
            studySpot = isNamePresent ? ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()) : null;

            if (argMultimap.getValue(PREFIX_FLAG).isPresent()) {
                // Only one flag should be present
                if (argMultimap.getAllValues(PREFIX_FLAG).size() != 1) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            LogCommand.MESSAGE_ONE_FLAG));
                }
                String flag = argMultimap.getValue(PREFIX_FLAG).get();
                if (!flag.equals(LogCommand.FLAG_RESET) && !flag.equals(LogCommand.FLAG_OVERRIDE)
                         && !flag.equals(LogCommand.FLAG_RESET_ALL)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            LogCommand.MESSAGE_INVALID_FLAG));
                }

                // Reset only depends on if a name is given,
                // It should ignore any hour provided, and should not throw an error even if hour is null.
                if (flag.equals("r") && isNamePresent) {
                    return new LogCommand(studySpot, null, true, false, false);
                } else if (flag.equals("ra")) {
                    return new LogCommand(studySpot, null, false, false, true);
                } else {
                    isOverride = true;
                }
            }
            hoursStudied = ParserUtil.parseStudiedHours(argMultimap.getValue(PREFIX_HOURS).get());
            return new LogCommand(studySpot, hoursStudied, false, isOverride, false);
        } catch (NoSuchElementException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCommand.MESSAGE_USAGE));
        }
    }
}
