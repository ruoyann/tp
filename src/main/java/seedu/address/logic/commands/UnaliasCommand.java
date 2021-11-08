package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;

/**
 * Removes aliases for Commands in StudyTracker.
 */
public class UnaliasCommand extends Command {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove aliases.\n"
            + "Parameters: "
            + PREFIX_ALIAS + "ALIAS*\n"
            + "Example: "
            + COMMAND_WORD + " " + PREFIX_ALIAS + "pwd";

    public static final String MESSAGE_SUCCESS_UNALIAS = "Removed alias '%1$s'";
    public static final String MESSAGE_NOT_FOUND = "Alias '%1$s' was not found in list of aliases.";

    private static final Logger logger = LogsCenter.getLogger(UnaliasCommand.class);

    private final Alias alias;

    /**
     * @param alias User-supplied {@code Alias}
     */
    public UnaliasCommand(Alias alias) {
        requireNonNull(alias);

        this.alias = alias;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing Unalias Command...");
        if (!model.hasAlias(alias)) {
            throw new CommandException(String.format(MESSAGE_NOT_FOUND, alias.userAlias));
        }
        model.removeAlias(alias);
        return new CommandResult(String.format(MESSAGE_SUCCESS_UNALIAS, alias.userAlias));
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true; // short circuit if same object
        } else {
            if (other instanceof UnaliasCommand) { // instanceof bypass null
                return alias.equals(((UnaliasCommand) other).alias);
            }
        }
        return false;
    }


}
