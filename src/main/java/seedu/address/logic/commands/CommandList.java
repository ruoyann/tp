package seedu.address.logic.commands;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Contains a list of all the available commands
 */
public class CommandList {
    public static final Command ADD_COMMAND = new AddCommand();
    public static final Command ALIAS_COMMAND = new AliasCommand();
    public static final Command CLEAR_COMMAND = new ClearCommand();
    public static final Command DELETE_COMMAND = new DeleteCommand();
    public static final Command EDIT_COMMAND = new EditCommand();
    public static final Command EXIT_COMMAND = new ExitCommand();
    public static final Command FAVOURITE_COMMAND = new FavouriteCommand();
    public static final Command HELP_COMMAND = new HelpCommand();
    public static final Command LIST_COMMAND = new ListCommand();
    public static final Command UNALIAS_COMMAND = new UnaliasCommand();
    public static final Command UNFAVOURITE_COMMAND = new UnfavouriteCommand();

    /**
     * Returns the list of Commands available to users
     */
    public static final ObservableList<Command> COMMANDS = FXCollections.observableArrayList(ADD_COMMAND, ALIAS_COMMAND,
            CLEAR_COMMAND, DELETE_COMMAND, EDIT_COMMAND, EXIT_COMMAND, FAVOURITE_COMMAND, HELP_COMMAND,
            LIST_COMMAND, UNALIAS_COMMAND,UNFAVOURITE_COMMAND);
}
