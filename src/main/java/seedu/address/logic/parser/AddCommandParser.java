package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_RATING, PREFIX_OPERATING_HOURS, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_AMENITY, PREFIX_HOURS);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_RATING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // name and rating do not need isPresent() checks because they are expected to have values.
        // operating hours and address default to "-" if the value is not added in.
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Rating rating = ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING).get());
        OperatingHours operatingHours = ParserUtil.parseOperatingHours(argMultimap.getValue(PREFIX_OPERATING_HOURS)
                .orElse(OperatingHours.DEFAULT_OPERATING_HOURS));
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(Address.DEFAULT_VALUE));
        StudiedHours studiedHours = ParserUtil.parseStudiedHours(argMultimap.getValue(PREFIX_HOURS)
                .orElse(StudiedHours.DEFAULT_VALUE));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Amenity> amenityList = ParserUtil.parseAmenities(argMultimap.getAllValues(PREFIX_AMENITY));

        StudySpot spot = new StudySpot(name, rating, operatingHours, address, studiedHours, tagList, amenityList);

        return new AddCommand(spot);
    }
}
