package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
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
    public static final String FLAG_FAVOURITES = "-f";
    public static final String FLAG_TAGS = "-t";
    public static final String[] VALID_FLAGS = {"-f", "-t"};

    public static final String MESSAGE_CONSTRAINTS = "Only valid flags are accepted as extra parameters";
    public static final String MESSAGE_SUCCESS = "Listed all study spots";

    private final Predicate<StudySpot> predicate;
    private final boolean isFavFlagPresent;
    private final Set<Tag> tags;

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
        if (!getFilterMessage().isBlank()) {
            sb.append(getFilterMessage());
        }
        return new CommandResult(sb.toString());
    }
    
    /**
     * Returns true if a given string is a valid list flag.
     */
    public static boolean isValidFlag(String test) {
        requireNonNull(test);
        return Arrays.asList(VALID_FLAGS).contains(test);
    }

    /**
     * Lists all flags in a single string.
     */
    public static String listAllFlags(String[] allFlags) {
        if (allFlags.length == 1) {
            return allFlags[0];
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < allFlags.length - 1; i++) {
                sb.append(allFlags[i]);
                sb.append(", ");
            }
            sb.append(allFlags[allFlags.length - 1]);
            return sb.toString();
        }
    }

    public String getFilterMessage() {
        StringBuilder sb = new StringBuilder();
        if (isFavFlagPresent) {
            sb.append(" in Favourites");
        }
        if (tags != null && !tags.isEmpty()) {
            sb.append("\n");
            sb.append("with Tags: ");
            String str = String.join(", ", tags.stream().map(Object::toString).collect(Collectors.toSet()));
            sb.append(str);
        }
        return sb.toString();
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

}
