package seedu.address.logic.parser;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import seedu.address.logic.commands.UnaliasCommand;
import seedu.address.logic.commands.UnfavouriteCommand;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_RATING = new Prefix("r/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMOVE_TAG = new Prefix("rt/");
    public static final Prefix PREFIX_AMENITY = new Prefix("m/");
    public static final Prefix PREFIX_REMOVE_AMENITY = new Prefix("rm/");
    public static final Prefix PREFIX_EDIT_SPOT = new Prefix("spot/");
    public static final Prefix PREFIX_FLAG = new Prefix("-");
    public static final Prefix PREFIX_DELETE_SPOT = new Prefix("n/");
    public static final Prefix PREFIX_ALIAS = new Prefix("al/");
    public static final Prefix PREFIX_ALIAS_COMMAND = new Prefix("cmd/");

    /* All command objects */
    public static final HashSet<String> COMMAND_WORDS_LIST = Stream.of(
        AddCommand.COMMAND_WORD,
        AliasCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD,
        FavouriteCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        UnaliasCommand.COMMAND_WORD,
        UnfavouriteCommand.COMMAND_WORD
    ).collect(Collectors.toCollection(HashSet::new));
}
