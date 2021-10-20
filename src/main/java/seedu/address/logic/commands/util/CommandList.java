package seedu.address.logic.commands.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
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

/**
 * Contains a list of all the available commands
 */
public class CommandList {
    private static final String ADD_COMMAND = AddCommand.COMMAND_WORD;
    private static final String ALIAS_COMMAND = AliasCommand.COMMAND_WORD;
    private static final String CLEAR_COMMAND = ClearCommand.COMMAND_WORD;
    private static final String DELETE_COMMAND = DeleteCommand.COMMAND_WORD;
    private static final String EDIT_COMMAND = EditCommand.COMMAND_WORD;
    private static final String EXIT_COMMAND = ExitCommand.COMMAND_WORD;
    private static final String FIND_COMMAND = FindCommand.COMMAND_WORD;
    private static final String FAVOURITE_COMMAND = FavouriteCommand.COMMAND_WORD;
    private static final String HELP_COMMAND = HelpCommand.COMMAND_WORD;
    private static final String LIST_COMMAND = ListCommand.COMMAND_WORD;
    private static final String LOG_COMMAND = LogCommand.COMMAND_WORD;
    private static final String UNALIAS_COMMAND = UnaliasCommand.COMMAND_WORD;
    private static final String UNFAVOURITE_COMMAND = UnfavouriteCommand.COMMAND_WORD;


    private static final List<String> commandWords = Arrays.asList(
        ADD_COMMAND, ALIAS_COMMAND,
        CLEAR_COMMAND, DELETE_COMMAND, EDIT_COMMAND, EXIT_COMMAND,
        FIND_COMMAND, FAVOURITE_COMMAND, HELP_COMMAND,
        LIST_COMMAND, LOG_COMMAND, UNALIAS_COMMAND, UNFAVOURITE_COMMAND
    );

    /**
     * Returns the list of {@code COMMAND_WORD}s as Strings
     *
     * The default COMMAND_WORD is in lowercase format. We do some cleaning here to capitalize the COMMAND_WORD.
     */
    public static final ObservableList<String> COMMANDS = FXCollections.observableArrayList(
            commandWords.stream().map(cmd ->
                    cmd.substring(0, 1).toUpperCase() + cmd.substring(1)
            ).collect(Collectors.toList())
    );

    /**
     *  Returns a HashSet of all {@code COMMAND_WORD}s
     */
    public static final HashSet<String> COMMAND_WORDS_LIST = new HashSet<>(commandWords);

    /**
     * Returns a HashMap mapping COMMAND_WORD to the respective MESSAGE_USAGE
     */
    public static HashMap<String, String> getCommandToUsageMapping() {
        HashMap<String, String> commandToUsage = new HashMap<>();
        commandToUsage.put(ADD_COMMAND, AddCommand.MESSAGE_USAGE);
        commandToUsage.put(ALIAS_COMMAND, AliasCommand.MESSAGE_USAGE);
        commandToUsage.put(CLEAR_COMMAND, ClearCommand.MESSAGE_USAGE);
        commandToUsage.put(DELETE_COMMAND, DeleteCommand.MESSAGE_USAGE);
        commandToUsage.put(EDIT_COMMAND, EditCommand.MESSAGE_USAGE);
        commandToUsage.put(EXIT_COMMAND, ExitCommand.MESSAGE_USAGE);
        commandToUsage.put(FAVOURITE_COMMAND, FavouriteCommand.MESSAGE_USAGE);
        commandToUsage.put(FIND_COMMAND, FindCommand.MESSAGE_USAGE);
        commandToUsage.put(HELP_COMMAND, HelpCommand.MESSAGE_USAGE);
        commandToUsage.put(LIST_COMMAND, ListCommand.MESSAGE_USAGE);
        commandToUsage.put(LOG_COMMAND, LogCommand.MESSAGE_USAGE);
        commandToUsage.put(UNALIAS_COMMAND, UnaliasCommand.MESSAGE_USAGE);
        commandToUsage.put(UNFAVOURITE_COMMAND, UnfavouriteCommand.MESSAGE_USAGE);
        return commandToUsage;
    }
}
