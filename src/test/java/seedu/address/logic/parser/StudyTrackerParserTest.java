package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudySpotDescriptor;
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
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.NameContainsKeywordsPredicate;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.EditStudySpotDescriptorBuilder;
import seedu.address.testutil.StudySpotBuilder;
import seedu.address.testutil.StudySpotUtil;

public class StudyTrackerParserTest {

    private static final List<Alias> ALIAS_LIST = Collections.singletonList(new Alias("ls", "list"));
    private final StudyTrackerParser parser = new StudyTrackerParser();

    @Test
    public void parseCommand_add() throws Exception {
        StudySpot studySpot = new StudySpotBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(StudySpotUtil.getAddCommand(studySpot), ALIAS_LIST);
        assertEquals(new AddCommand(studySpot), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, ALIAS_LIST) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3", ALIAS_LIST) instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(DeleteCommand.COMMAND_WORD
                + " " + CliSyntax.PREFIX_DELETE_SPOT + "Test", ALIAS_LIST);
        assertEquals(new DeleteCommand(new Name("Test")), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        StudySpot studySpot = new StudySpotBuilder().withName("Test").build();
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder(studySpot).build();
        EditCommand commandFromParse = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + CliSyntax.PREFIX_EDIT_SPOT + "Test" + " "
                + StudySpotUtil.getEditStudySpotDescriptorDetails(descriptor), ALIAS_LIST);
        assertEquals(new EditCommand(new Name("Test"), descriptor), commandFromParse);
    }

    @Test
    public void parseCommand_alias() throws Exception {
        Alias listAlias = new Alias("ls", "list");
        AliasCommand command = (AliasCommand) parser.parseCommand(
                AliasCommand.COMMAND_WORD + " " + PREFIX_ALIAS + "ls "
                        + PREFIX_ALIAS_COMMAND + "list ", ALIAS_LIST);
        assertEquals(new AliasCommand(false, listAlias), command);
    }

    @Test
    public void parseCommand_unalias() throws Exception {
        Alias listAlias = new Alias("ls", "list");
        UnaliasCommand command = (UnaliasCommand) parser.parseCommand(
                UnaliasCommand.COMMAND_WORD + " " + PREFIX_ALIAS + "ls ", ALIAS_LIST);
        assertEquals(new UnaliasCommand(listAlias), command);
    }

    @Test
    public void parseCommand_favourite() throws Exception {
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(
                FavouriteCommand.COMMAND_WORD + " " + PREFIX_NAME + STARBUCKS.getName(), ALIAS_LIST);
        assertEquals(new FavouriteCommand(STARBUCKS.getName()), command);
    }

    @Test
    public void parseCommand_unfavourite() throws Exception {
        UnfavouriteCommand command = (UnfavouriteCommand) parser.parseCommand(
                UnfavouriteCommand.COMMAND_WORD + " " + PREFIX_NAME + STARBUCKS.getName(), ALIAS_LIST);
        assertEquals(new UnfavouriteCommand(STARBUCKS.getName()), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, ALIAS_LIST) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3", ALIAS_LIST) instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")),
                ALIAS_LIST);
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, ALIAS_LIST) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", ALIAS_LIST) instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, ALIAS_LIST) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3", ALIAS_LIST) instanceof ListCommand);
    }

    @Test
    public void parseCommand_log() throws Exception {
        LogCommand commandFromParse = (LogCommand) parser.parseCommand(
                LogCommand.COMMAND_WORD + " " + PREFIX_NAME + STARBUCKS.getName() + " "
                        + PREFIX_HOURS + "5", ALIAS_LIST);
        LogCommand expectedLogCommand = new LogCommand(STARBUCKS.getName(), new StudiedHours("5"),
                false, false, false);
        assertEquals(expectedLogCommand, commandFromParse);
    }

    @Test
    public void parseCommand_aliasInList_parsesToCorrectCommand() throws Exception {
        assertTrue(parser.parseCommand("ls", ALIAS_LIST) instanceof ListCommand);
    }

    @Test
    public void parseCommand_unknownAlias_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                "unknown alias", ALIAS_LIST));
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand("", ALIAS_LIST));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                "unknownCommand", ALIAS_LIST));
    }
}
