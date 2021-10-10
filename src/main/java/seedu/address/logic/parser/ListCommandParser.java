package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FLAG, PREFIX_TAG);
        Predicate<StudySpot> predicate = ParserUtil.parseFlags(argMultimap);
        boolean isFavFlagPresent = ParserUtil.isFlagPresent(
                argMultimap.getAllValues(PREFIX_FLAG), ListCommand.FLAG_FAVOURITES);
        boolean isTagFlagPresent = ParserUtil.isFlagPresent(
                argMultimap.getAllValues(PREFIX_TAG), ListCommand.FLAG_TAGS);

        Set<Tag> tagList = isTagFlagPresent ? ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)) : null;
        return new ListCommand(predicate, isFavFlagPresent, tagList);
    }
}
