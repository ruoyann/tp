package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;

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
        boolean isFavFlagPresent = false;
        String trimmedArgs = args.trim();
        Predicate<StudySpot> predicate = PREDICATE_SHOW_ALL_STUDYSPOTS;
        // List
        if (trimmedArgs.isEmpty()) {
            return new ListCommand(predicate, false, null);
        }

        // List favourites
        String[] flags = trimmedArgs.split("\\s+");
        if (isFlagPresent(flags, ListCommand.FLAG_FAVOURITES)) {
            isFavFlagPresent = true;
            predicate = predicate.and(StudySpot::isFavourite);
        }
        if (!isFlagPresent(flags, ListCommand.FLAG_TAGS)) {
            return new ListCommand(predicate, isFavFlagPresent, null);
        }

        // List tags
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Predicate<StudySpot> containsTags = ListCommand.containsTags(tagList);
        predicate = isFavFlagPresent ? predicate.or(containsTags) : containsTags;

        return new ListCommand(predicate, isFavFlagPresent, tagList);
    }

    public boolean isFlagPresent(String[] args, String flag) {
        for (String arg: args) {
            if (arg.equals(flag)) {
                return true;
            }
        }
        return false;
    }

}
