package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.amenity.Amenity;
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
        requireNonNull(args);
//        ArgumentMultimap argMultimapTag = ArgumentTokenizer.tokenize(args, PREFIX_FLAG, PREFIX_TAG);
//        ArgumentMultimap argMultimapAmenity = ArgumentTokenizer.tokenize(args, PREFIX_FLAG, PREFIX_AMENITY);
//        Predicate<StudySpot> predicateTag = ParserUtil.parseFlags(argMultimapTag);
//        Predicate<StudySpot> predicateAmenity = ParserUtil.parseFlags(argMultimapAmenity);
//        List<String> flagsListTag = argMultimapTag.getAllValues(PREFIX_FLAG);
//        List<String> flagsListAmenity = argMultimapAmenity.getAllValues(PREFIX_FLAG);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FLAG, PREFIX_TAG, PREFIX_AMENITY);
        Predicate<StudySpot> predicate = ParserUtil.parseFlags(argMultimap);
        List<String> flagsList = argMultimap.getAllValues(PREFIX_FLAG);
        boolean isFavFlagPresent = ParserUtil.isFlagPresent(flagsList, ListCommand.FLAG_FAVOURITES);
        boolean isTagFlagPresent = ParserUtil.isFlagPresent(flagsList, ListCommand.FLAG_TAGS);
        boolean isAmenityFlagPresent = ParserUtil.isFlagPresent(flagsList, ListCommand.FLAG_AMENITIES);

        Set<Tag> tagList = isTagFlagPresent ? ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)) : null;
        Set<Amenity> amenityList = isAmenityFlagPresent ?
                ParserUtil.parseAmenities(argMultimap.getAllValues(PREFIX_AMENITY)) :
                null;

        if (isTagFlagPresent && tagList.isEmpty()) {
            throw new ParseException(ListCommand.MESSAGE_MISSING_TAGS);
        }

        if (isAmenityFlagPresent && amenityList.isEmpty()) {
            throw new ParseException(ListCommand.MESSAGE_MISSING_AMENITIES);
        }
        for (String flag: flagsList) {
            if (!ListCommand.FLAG_LIST.contains(flag)) {
                throw new ParseException(ListCommand.MESSAGE_UNKNOWN_FLAGS);
            }
        }
        return new ListCommand(predicate, isFavFlagPresent, tagList, amenityList);
    }
}
