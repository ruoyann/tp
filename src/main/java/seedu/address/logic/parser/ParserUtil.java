package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Email;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String rating} into a {@code Rating}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rating} is invalid.
     */
    public static Rating parseRating(String rating) throws ParseException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        if (!Rating.isValidRating(trimmedRating)) {
            throw new ParseException(Rating.MESSAGE_CONSTRAINTS);
        }
        return new Rating(trimmedRating);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (email.equals("-")) {
            return Email.emptyEmail();
        }
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String amenity} into an {@code Amenity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amenity} is invalid.
     */
    public static Predicate<StudySpot> parseFlags(ArgumentMultimap argMultiMap) throws ParseException {
        requireNonNull(argMultiMap);
        List<String> flags = argMultiMap.getAllValues(PREFIX_FLAG);
        List<Predicate<StudySpot>> predicateList = new ArrayList<>();
        predicateList.add(PREDICATE_SHOW_ALL_STUDYSPOTS);
        if (isFlagPresent(flags, ListCommand.FLAG_FAVOURITES)) {
            predicateList.add(StudySpot::isFavourite);
        }

        if (isFlagPresent(flags, ListCommand.FLAG_TAGS)) {
            Set<Tag> tags = parseTags(argMultiMap.getAllValues(PREFIX_TAG));
            List<Predicate<StudySpot>> tagsPredicate =
                    tags.stream().map(tag -> ListCommand.containsTag(tag)).collect(Collectors.toList());
            predicateList.addAll(tagsPredicate);
        }
        return predicateList.stream().reduce(Predicate::and).get();
    }

    /**
     * Checks if a particular flag is present in the arguments.
     * @param args Arguments from MultiMap.
     * @param flag Query flag.
     * @return true if query flag is present.
     */
    public static boolean isFlagPresent(List<String> args, String flag) {
        for (String arg: args) {
            if (arg.equals(flag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String amenity} into an {@code Amenity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amenity} is invalid.
     */
    public static Amenity parseAmenity(String amenity) throws ParseException {
        requireNonNull(amenity);
        String trimmedAmenity = amenity.trim();
        if (!Amenity.isValidAmenityType(amenity)) {
            throw new ParseException(String.format(Amenity.MESSAGE_CONSTRAINTS,
                    Amenity.listAllAmenityTypes(Amenity.VALID_TYPES)));
        }
        return new Amenity(trimmedAmenity);
    }

    /**
     * Parses {@code Collection<String> amenities} into a {@code Set<Amenity>}.
     */
    public static Set<Amenity> parseAmenities(Collection<String> amenities) throws ParseException {
        requireNonNull(amenities);
        final Set<Amenity> amenitySet = new HashSet<>();
        for (String amenityType : amenities) {
            amenitySet.add(parseAmenity(amenityType));
        }
        return amenitySet;
    }
}
