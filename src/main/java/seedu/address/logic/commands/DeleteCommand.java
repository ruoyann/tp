package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_SPOT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudySpot;

/**
 * Deletes a study spot identified using it's displayed index from the study tracker.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the study spot identified by the study spot name used in the displayed study spot list.\n"
            + "Parameters: Study Spot Name \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_DELETE_SPOT + "starbucks";

    public static final String MESSAGE_DELETE_STUDYSPOT_SUCCESS = "Deleted study spot: %1$s";

    private Name name;

    public DeleteCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<StudySpot> lastShownList = model.getFilteredStudySpotList();

        boolean isPresent = false;
        StudySpot studySpotToDelete = null;
        for (StudySpot current: lastShownList) {
            if (current.isSameName(name)) {
                studySpotToDelete = current;
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME);
        }

        model.deleteStudySpot(studySpotToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDYSPOT_SUCCESS, studySpotToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && name.equals(((DeleteCommand) other).name)); // state check
    }
}
