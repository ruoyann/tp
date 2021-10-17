package seedu.address.model.alias;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.util.CommandList;

/**
 * Represents an Alias in the study tracker program.
 * Guarantees: immutable; userAlias and aliasCommandWord are valid as declared in {@link #isValidUserAlias(String)}
 *             and {@link #isValidCommandWord(String)}.
 */
public class Alias {

    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String MESSAGE_CONSTRAINTS = "Alias must not contain existing command words,"
                    + " and provided command must match an existing command.";
    public static final String MESSAGE_ALIAS_CONSTRAINTS = "Provided alias cannot contain spaces or "
                    + "existing command words.";
    public static final String MESSAGE_COMMAND_CONSTRAINTS = "Provided command must match an existing command.";
    public static final String MESSAGE_COMMAND_WARN = "Please do not create aliases for alias and unalias >:( "
            + "We don't want any recursion shenanigans.";

    public final String userAlias;
    public final String aliasCommandWord;

    /**
     * Constructs as {@code Alias}.
     *
     * @param userAlias A valid user-defined alias word.
     * @param aliasCommandWord A valid command word.
     */
    public Alias(String userAlias, String aliasCommandWord) {
        requireNonNull(userAlias);
        requireNonNull(aliasCommandWord);
        checkArgument(isValidUserAlias(userAlias), MESSAGE_ALIAS_CONSTRAINTS);
        checkArgument(!isDangerousCommandWord(aliasCommandWord), MESSAGE_COMMAND_WARN);
        checkArgument(isValidCommandWord(aliasCommandWord), MESSAGE_COMMAND_CONSTRAINTS);
        this.userAlias = userAlias;
        this.aliasCommandWord = aliasCommandWord;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getAliasCommandWord() {
        return aliasCommandWord;
    }

    /**
     * Checks if provided userAlias String matches the validity rules
     * @param userAlias provided in constructor.
     */
    public static boolean isValidUserAlias(String userAlias) {
        return userAlias != null && !userAlias.isBlank()
                && userAlias.matches(VALIDATION_REGEX) && !CommandList.COMMAND_WORDS_LIST.contains(userAlias);
    }

    /**
     * Checks if provided commandWord belongs to the list of command Words
     * @param commandWord provided in constructor.
     */
    public static boolean isValidCommandWord(String commandWord) {
        if (commandWord != null) {
            String firstWord = commandWord.split(" ")[0];
            return CommandList.COMMAND_WORDS_LIST.contains(firstWord);
        }

        return false;
    }

    /**
     * Checks if provided commandWord is 'alias' or 'unalias'
     */
    public static boolean isDangerousCommandWord(String commandWord) {
        return commandWord.equals(AliasCommand.COMMAND_WORD)
                || commandWord.equals(UnaliasCommand.COMMAND_WORD);
    }

    /**
     * Returns true if both aliases have the same name.
     * This defines a weaker notion of equality between two aliases.
     */
    public boolean isSameAlias(Alias otherAlias) {
        if (otherAlias == this) {
            return true;
        }

        return otherAlias != null
                && otherAlias.userAlias.equals(userAlias);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.alias.Alias // instanceof handles nulls
                && userAlias.equals(((seedu.address.model.alias.Alias) other).userAlias)) // state check
                && aliasCommandWord.equals(((seedu.address.model.alias.Alias) other).aliasCommandWord); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(userAlias, aliasCommandWord);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return userAlias + ": [" + aliasCommandWord + "]";
    }

}
