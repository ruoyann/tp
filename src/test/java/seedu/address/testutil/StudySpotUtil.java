package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditStudySpotDescriptor;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * A utility class for StudySpot.
 */
public class StudySpotUtil {

    /**
     * Returns an add command string for adding the {@code studySpot}.
     */
    public static String getAddCommand(StudySpot studySpot) {
        return AddCommand.COMMAND_WORD + " " + getStudySpotDetails(studySpot);
    }

    /**
     * Returns the part of command string for the given {@code studySpot}'s details.
     */
    public static String getStudySpotDetails(StudySpot studySpot) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + studySpot.getName().fullName + " ");
        sb.append(PREFIX_RATING + studySpot.getRating().value + " ");
        sb.append(PREFIX_EMAIL + studySpot.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + studySpot.getAddress().value + " ");
        studySpot.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditStudySpotDescriptor}'s details.
     */
    public static String getEditStudySpotDescriptorDetails(EditStudySpotDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getRating().ifPresent(rating -> sb.append(PREFIX_RATING).append(rating.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
