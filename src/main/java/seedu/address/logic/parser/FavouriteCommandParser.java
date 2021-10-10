package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.NoSuchElementException;

import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.studyspot.Name;

/**
 * Parses input arguments and creates a new FavouriteCommand object
 */
public class FavouriteCommandParser implements Parser<FavouriteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FavouriteCommand
     * and returns a FavouriteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
        Name name;
        try {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } catch (NoSuchElementException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE), pe);
        }
        return new FavouriteCommand(name);
    }

}
