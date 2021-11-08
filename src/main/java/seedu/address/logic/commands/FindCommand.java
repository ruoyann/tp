package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.studyspot.NameContainsKeywordsPredicate;

/**
 * Finds and lists all study spots in study tracker whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all study spots whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them.\n"
            + "Parameters: KEYWORD MORE_KEYWORDS...\n"
            + "Example: " + COMMAND_WORD + " com2 library biz ";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing Find Command...");
        requireNonNull(model);
        model.updateFilteredStudySpotList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDYSPOT_LISTED_OVERVIEW, model.getFilteredStudySpotList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }


}
