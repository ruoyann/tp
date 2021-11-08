package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_COMMAND;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.alias.Alias;

/**
 * Sets aliases for Commands in StudyTracker.
 */
public class AliasCommand extends Command {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Define or display aliases.\n"
            + "The -s flag will show all aliases\n"
            + "Parameters: "
            + PREFIX_ALIAS + "ALIAS* "
            + PREFIX_ALIAS_COMMAND + "COMMAND* \n"
            + "Example: "
            + COMMAND_WORD + " " + PREFIX_ALIAS + "e " + PREFIX_ALIAS_COMMAND + "edit\n"
            + COMMAND_WORD + " " + PREFIX_ALIAS + "editStarbucks " + PREFIX_ALIAS_COMMAND + "edit spot/Starbucks\n";

    public static final String FLAG_SHOW = "s";

    public static final String MESSAGE_SUCCESS_SET = "Added alias %1$s";
    public static final String MESSAGE_SUCCESS_SHOW = "Here are your aliases:\n%1$s.";

    private static final Logger logger = LogsCenter.getLogger(AliasCommand.class);

    private final boolean isShowType;
    private final Alias alias;

    /**
     * @param isShowType Whether this AliasCommand is a "show" type
     */
    public AliasCommand(boolean isShowType) {
        this.isShowType = isShowType;
        this.alias = null;
    }

    /**
     * @param isShowType Whether this AliasCommand is a "show" type
     * @param alias User-supplied {@code Alias}
     */
    public AliasCommand(boolean isShowType, Alias alias) {
        requireNonNull(alias);

        this.isShowType = isShowType;

        // defensive code to ensure null alias when isShowType
        if (isShowType) {
            this.alias = null;
        } else {
            this.alias = alias;
        }
    }

    @Override
    public CommandResult execute(Model model) {
        logger.info("Executing Alias Command...");
        if (this.isShowType) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_SHOW,
                    model.getUserPrefs().getUserAliases().stream()
                    .map(Alias::toString)
                    .collect(Collectors.joining("\n")))
            );
        } else {
            model.addAlias(alias);
            return new CommandResult(String.format(MESSAGE_SUCCESS_SET, alias));
        }
    }

    @Override
    public boolean equals(Object other) {

        if (other == this) {
            return true; // short circuit if same object
        } else {
            if (other instanceof AliasCommand) { // instanceof bypass null
                if (isShowType) { // showType AliasCommands have null aliases
                    return ((AliasCommand) other).isShowType && ((AliasCommand) other).alias == null;
                } else { // compare the Alias in non-showType
                    return alias.equals(((AliasCommand) other).alias);
                }
            }
        }
        return false;
    }

}
