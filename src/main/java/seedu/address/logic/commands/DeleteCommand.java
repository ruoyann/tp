package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyspot.StudySpot;

/**
 * Deletes a study spot identified using it's displayed index from the study tracker.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the study spot identified by the name used in the displayed study spot list.\n"
            + "Parameters: NAME (must be a valid name in the StudyTracker)\n"
            + "Example: " + COMMAND_WORD + " n/ValidName";

    public static final String MESSAGE_DELETE_STUDYSPOT_SUCCESS = "Deleted study spot: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Creates a default DeleteCommand constructor for {@code CommandList}
     */
    public DeleteCommand() {
        this.targetIndex = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<StudySpot> lastShownList = model.getFilteredStudySpotList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDYSPOT_DISPLAYED_INDEX);
        }

        StudySpot studySpotToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteStudySpot(studySpotToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_STUDYSPOT_SUCCESS, studySpotToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }

    @Override
    public String toString() {
        return "Delete";
    }

    @Override
    public String getCommandUsage() {
        return MESSAGE_USAGE;
    }
}
