package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.StudyTracker;

/**
 * Clears the study tracker.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes ALL study spots from the StudyTracker.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "StudyTracker has been cleared!";

    private static final Logger logger = LogsCenter.getLogger(ClearCommand.class);

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing Clear Command...");
        requireNonNull(model);
        model.setStudyTracker(new StudyTracker());
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
