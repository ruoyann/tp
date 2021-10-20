package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LogCommand;
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;

/**
 * Parses user input.
 */
public class StudyTrackerParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWordOrAlias>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param aliases the list of aliases to check against
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput, List<Alias> aliases) throws ParseException {

        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWordOrAlias = matcher.group("commandWordOrAlias");
        String arguments = matcher.group("arguments");
        String commandWord = commandWordOrAlias;

        // extra steps if we're actually parsing an alias
        if (isInvokingAlias(commandWordOrAlias, aliases)) {
            String expandedAlias = expandAlias(commandWordOrAlias, aliases);
            String expandedCommandWord = expandedAlias.split(" ")[0];
            String expandedArgs = expandedAlias.replace(expandedCommandWord, "");

            commandWord = expandedCommandWord;
            arguments = expandedArgs + arguments;
        }

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case AliasCommand.COMMAND_WORD:
            return new AliasCommandParser().parse(arguments);

        case UnaliasCommand.COMMAND_WORD:
            return new UnaliasCommandParser().parse(arguments);

        case FavouriteCommand.COMMAND_WORD:
            return new FavouriteCommandParser().parse(arguments);

        case UnfavouriteCommand.COMMAND_WORD:
            return new UnfavouriteCommandParser().parse(arguments);

        case LogCommand.COMMAND_WORD:
            return new LogCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }


    /**
     * Checks if command word is an alias. If so, expands the alias.
     *
     * @@author JavaHelper
     * Reused from https://stackoverflow.com/a/50349646
     */
    private boolean isInvokingAlias(String commandWordOrAlias, List<Alias> aliases) {
        // Create a Map of key: userAlias, value: aliasCommandWord for each alias
        Map<String, String> aliasMap = aliases.stream().collect(
                Collectors.toMap(Alias::getUserAlias, Alias::getAliasCommandWord)
        );
        return aliasMap.containsKey(commandWordOrAlias);
    }

    /**
     * Expands the provided alias.
     * Assumption: provided {@code alias} is a valid alias in the list of Aliases
     */
    private String expandAlias(String alias, List<Alias> aliases) {
        // Create a Map of key: userAlias, value: aliasCommandWord for each alias
        Map<String, String> aliasMap = aliases.stream().collect(
                Collectors.toMap(Alias::getUserAlias, Alias::getAliasCommandWord)
        );
        return aliasMap.get(alias);
    }

}
