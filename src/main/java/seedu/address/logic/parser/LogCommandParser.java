package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

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
                assert(flag.equals(LogCommand.FLAG_RESET) || flag.equals(LogCommand.FLAG_OVERRIDE) || flag.equals(LogCommand.FLAG_RESET_ALL));

                // Reset only depends on if a name is given,
                // It should ignore any hour provided, and should not throw an error even if hour is null.
                if (flag.equals("r")) {
                    return new LogCommand(studySpot, null, isNamePresent,
                            false, !isNamePresent);
                }
                else if (flag.equals("ra")) {
                    return new LogCommand(studySpot, null, false,
                            false, true);
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

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
