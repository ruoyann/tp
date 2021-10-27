package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * Lists all study spots in the study tracker to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all study spots.\n"
            + "The -f flag lists all favourite study spots\n"
            + "The -t flag lists study spots that match given tags\n"
            + "The -m flag lists study spots that match given amenities\n"
            + "The -r flag lists study spots that match given rating\n"
            + "Parameters: [-f] [-t t/TAG...] [-m m/AMENITY...] [-r r/RATING]\n"
            + "Example: " + COMMAND_WORD;
    public static final String FLAG_FAVOURITES = "f";
    public static final String FLAG_TAGS = "t";
    public static final String FLAG_AMENITIES = "m";
    public static final String FLAG_RATING = "r";
    public static final List<String> FLAG_LIST = new ArrayList<>(Arrays.asList(FLAG_FAVOURITES , FLAG_TAGS,
            FLAG_AMENITIES, FLAG_RATING));

    public static final String MESSAGE_CONSTRAINTS = "Only valid flags are accepted as extra parameters";
    public static final String MESSAGE_SUCCESS = "Listed all study spots";
    public static final String MESSAGE_MISSING_TAGS = "Please enter a tag. e.g. t/cold";
    public static final String MESSAGE_MISSING_AMENITIES = "Please enter a tag. e.g. m/wifi";
    public static final String MESSAGE_MISSING_RATING = "Please enter a tag. e.g. r/5";
    public static final String MESSAGE_UNKNOWN_FLAGS = "Unknown flags given";

    private final Predicate<StudySpot> predicate;
    private final boolean isFavFlagPresent;
    private final Set<Tag> tags;
    private final Set<Amenity> amenities;

    /**
     * Creates a ListCommand.
     * @param predicate Predicate that filters the study spots.
     * @param isFavFlagPresent Tracks if ListCommand lists favourites.
     * @param tags List of tags that study spots are being filtered by.
     */
    public ListCommand(Predicate<StudySpot> predicate, boolean isFavFlagPresent, Set<Tag> tags,
                       Set<Amenity> amenities) {
        this.predicate = predicate;
        this.isFavFlagPresent = isFavFlagPresent;
        this.tags = tags;
        this.amenities = amenities;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudySpotList(predicate);
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS);
        String msg = getFilterMessage(isFavFlagPresent, tags, amenities);
        if (!msg.isBlank()) {
            sb.append(msg);
        }
        return new CommandResult(sb.toString());
    }

    /**
     * Returns the string to be printed containing the filter currently set.
     * @param isFavFlag
     * @param tagSet
     */
    public static String getFilterMessage(boolean isFavFlag, Set<Tag> tagSet, Set<Amenity> amenitySet) {
        StringBuilder sb = new StringBuilder();
        if (isFavFlag) {
            sb.append(" in Favourites");
        }
        if (tagSet != null && !tagSet.isEmpty()) {
            sb.append(" with Tags: ");
            String str = String.join(", ", tagSet.stream().map(Object::toString).collect(Collectors.toSet()));
            sb.append(str);
        }
        if (amenitySet != null && !amenitySet.isEmpty()) {
            sb.append(" with Amenities: ");
            String str = String.join(", ", amenitySet.stream().map(Object::toString).collect(Collectors.toSet()));
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    /**
     * Returns an unmodifiable amenity set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code amenities} is null.
     */
    public Optional<Set<Amenity>> getAmenities() {
        return (amenities != null) ? Optional.of(Collections.unmodifiableSet(amenities)) : Optional.empty();
    }

    /**
     * Returns a Predicate that checks if a studySpot contains the tag queried.
     * @param queryTag
     */
    public static Predicate<StudySpot> containsTag(Tag queryTag) {
        Predicate<StudySpot> studySpotContainTag = new Predicate<StudySpot>() {
            @Override
            public boolean test(StudySpot studySpot) {
                return studySpot.getTags().contains(queryTag);
            }
        };
        return studySpotContainTag;
    }

    /**
     * Returns a Predicate that checks if a studySpot contains the amenity queried.
     * @param queryAmenity
     */
    public static Predicate<StudySpot> containsAmenity(Amenity queryAmenity) {
        Predicate<StudySpot> studySpotContainAmenity = new Predicate<StudySpot>() {
            @Override
            public boolean test(StudySpot studySpot) {
                return studySpot.getAmenities().contains(queryAmenity);
            }
        };
        return studySpotContainAmenity;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        // state check
        // Predicate is not a condition for equals as different predicates may have same results
        ListCommand c = (ListCommand) other;
        return (isFavFlagPresent == c.isFavFlagPresent)
                && getTags().equals(c.getTags())
                && getAmenities().equals(c.getAmenities());
    }
}
