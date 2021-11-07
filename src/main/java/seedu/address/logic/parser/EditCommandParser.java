package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT_SPOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudySpotDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Name;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EDIT_SPOT, PREFIX_NAME, PREFIX_RATING,
                        PREFIX_OPERATING_HOURS, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMOVE_TAG,
                        PREFIX_AMENITY, PREFIX_REMOVE_AMENITY);

        Name toBeChangedSpot;

        try {
            toBeChangedSpot = ParserUtil.parseName(argMultimap.getValue(PREFIX_EDIT_SPOT).get());
        } catch (NoSuchElementException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditStudySpotDescriptor editStudySpotDescriptor = new EditStudySpotDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editStudySpotDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            editStudySpotDescriptor.setRating(ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING).get()));
        }
        if (argMultimap.getValue(PREFIX_OPERATING_HOURS).isPresent()) {
            editStudySpotDescriptor.setOperatingHours(ParserUtil
                    .parseOperatingHours(argMultimap.getValue(PREFIX_OPERATING_HOURS).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editStudySpotDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG))
                .ifPresent(editStudySpotDescriptor::setAddedTags);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_REMOVE_TAG))
                .ifPresent(editStudySpotDescriptor::setRemovedTags);

        parseAmenitiesForEdit(argMultimap.getAllValues(PREFIX_AMENITY))
                .ifPresent(editStudySpotDescriptor::setAddedAmenities);
        parseAmenitiesForEdit(argMultimap.getAllValues(PREFIX_REMOVE_AMENITY))
                .ifPresent(editStudySpotDescriptor::setRemovedAmenities);

        if (!editStudySpotDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(toBeChangedSpot, editStudySpotDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> amenities} into a {@code Set<Amenity>} if {@code amenities} is non-empty.
     * If {@code amenities} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Amenity>} containing zero tags.
     */
    private Optional<Set<Amenity>> parseAmenitiesForEdit(Collection<String> amenities) throws ParseException {
        if (amenities.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> amenitySet = amenities.size() == 1 && amenities.contains("")
                ? Collections.emptySet() : amenities;
        return Optional.of(ParserUtil.parseAmenities(amenitySet));
    }

}
