package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;

import seedu.address.model.Model;

/**
 * Lists all study spots in the study tracker to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all study spots. \n" +
            "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all study spots";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudySpotList(PREDICATE_SHOW_ALL_STUDYSPOTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public String toString() {
        return "List";
    }

    @Override
    public String getCommandUsage() {
        return MESSAGE_USAGE;
    }
}
