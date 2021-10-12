package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * Lists all study spots in the study tracker to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String FLAG_FAVOURITES = "f";
    public static final String FLAG_TAGS = "t";

    public static final String MESSAGE_CONSTRAINTS = "Only valid flags are accepted as extra parameters";
    public static final String MESSAGE_SUCCESS = "Listed all study spots";

    private final Predicate<StudySpot> predicate;
    private final boolean isFavFlagPresent;
    private final Set<Tag> tags;

    /**
     * Creates a ListCommand.
     * @param predicate Predicate that filters the study spots.
     * @param isFavFlagPresent Tracks if ListCommand lists favourites.
     * @param tags List of tags that study spots are being filtered by.
     */
    public ListCommand(Predicate<StudySpot> predicate, boolean isFavFlagPresent, Set<Tag> tags) {
        this.predicate = predicate;
        this.isFavFlagPresent = isFavFlagPresent;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudySpotList(predicate);
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_SUCCESS);
        String msg = getFilterMessage(isFavFlagPresent, tags);
        if (!msg.isBlank()) {
            sb.append(msg);
        }
        return new CommandResult(sb.toString());
    }

    public static String getFilterMessage(boolean isFavFlag, Set<Tag> tagSet) {
        StringBuilder sb = new StringBuilder();
        if (isFavFlag) {
            sb.append(" in Favourites");
        }
        if (tagSet != null && !tagSet.isEmpty()) {
            sb.append(" with Tags: ");
            String str = String.join(", ", tagSet.stream().map(Object::toString).collect(Collectors.toSet()));
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
     * Returns a Predicate that checks if a studySpot contains the set of tags queried.
     * @param queryTags
     */
    public static Predicate<StudySpot> containsTags(Set<Tag> queryTags) {
        Predicate<StudySpot> studySpotContainTag = new Predicate<StudySpot>() {
            @Override
            public boolean test(StudySpot studySpot) {
                Set<Tag> studySpotTags = studySpot.getTags();
                Set<Tag> queryTagsCopy = new HashSet<>(queryTags);
                queryTagsCopy.retainAll(studySpotTags);
                return !queryTagsCopy.isEmpty();
            }
        };
        return studySpotContainTag;
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
                && getTags().equals(c.getTags());
    }
}

