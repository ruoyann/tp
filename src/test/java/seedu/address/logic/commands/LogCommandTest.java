package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StudyTracker;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.StudySpotBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LogCommand.
 */
public class LogCommandTest {

    private Model model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());

    @Test
    public void execute_validLogCommand_logSuccessful() throws CommandException {
        StudySpot studySpotToLog = new StudySpotBuilder().withName("Starbucks").build();
        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());

        Name name = new Name("Starbucks");
        StudiedHours studiedHours = new StudiedHours("4");
        CommandResult commandResultInitial =
                new LogCommand(name, studiedHours, false, false, false).execute(expectedModel);
        CommandResult commandResultResetStudySpot =
                new LogCommand(name, studiedHours, true, false, false).execute(expectedModel);
        CommandResult commandResultResetAll =
                new LogCommand(name, studiedHours, false, false, true).execute(expectedModel);
        CommandResult commandResultOverride =
                new LogCommand(name, studiedHours, false, true, false).execute(expectedModel);

        String expectedCommandInitial = String.format(LogCommand.MESSAGE_SUCCESS_DEFAULT, studiedHours.toString(),
                name.toString());
        String expectedCommandResetStudySpot = String.format(LogCommand.MESSAGE_SUCCESS_RESET, name.toString());
        String expectedCommandResetAll = String.format(LogCommand.MESSAGE_SUCCESS_RESET_ALL, name.toString());
        String expectedCommandOverride = String.format(LogCommand.MESSAGE_SUCCESS_OVERRIDE, studiedHours,
                name);

        assertEquals(String.format(expectedCommandInitial, studySpotToLog),
                commandResultInitial.getFeedbackToUser());
        assertEquals(String.format(expectedCommandResetStudySpot, studySpotToLog),
                commandResultResetStudySpot.getFeedbackToUser());
        assertEquals(String.format(expectedCommandResetAll, studySpotToLog),
                commandResultResetAll.getFeedbackToUser());
        assertEquals(String.format(expectedCommandOverride, studySpotToLog),
                commandResultOverride.getFeedbackToUser());
    }

    @Test
    public void execute_invalidStudySpotName_failure() {
        Name notInTypicalStudySpots = new Name("Invalid Name");
        StudiedHours studiedHours = new StudiedHours("4");
        LogCommand logCommand = new LogCommand(notInTypicalStudySpots, studiedHours, false, false, false);

        assertCommandFailure(logCommand, model, Messages.MESSAGE_INVALID_NAME);
    }

    @Test
    public void execute_invalidStudiedHoursIntegerOverflow_failure() throws CommandException {
        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());

        Name name = new Name("Starbucks");
        StudiedHours studiedHours = new StudiedHours("2147483647");
        LogCommand logCommand = new LogCommand(name, studiedHours, false, false, false);

        assertCommandFailure(logCommand, expectedModel, StudiedHours.MESSAGE_HOURS_IS_FULL);
    }

    @Test
    public void execute_invalidNullStudiedHours_failure() {
        Name name = new Name("Starbucks");

        assertThrows(NullPointerException.class, () -> new LogCommand(name, null, false,
                false, false).execute(model));
    }

    @Test
    public void execute_invalidNullName_failure() {
        Name name = null;
        StudiedHours studiedHours = new StudiedHours("3");
        assertThrows(NullPointerException.class, () -> new LogCommand(name, studiedHours, false,
                false, false).execute(model));
    }

    @Test
    public void equals() {
        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        StudiedHours studiedHours = new StudiedHours("4");
        final LogCommand standardCommand = new LogCommand(firstStudySpotInTypicalStudySpots, studiedHours, false,
                false, false);

        // same values -> returns true
        Name secondStudySpotInTypicalStudySpots = new Name("Central library");
        StudiedHours copyStudiedHours = new StudiedHours("4");
        StudiedHours differentStudiedHours = new StudiedHours("7");
        LogCommand commandWithSameValues = new LogCommand(firstStudySpotInTypicalStudySpots, copyStudiedHours,
                false, false, false);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(new LogCommand(secondStudySpotInTypicalStudySpots, copyStudiedHours,
                false, false, false)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new LogCommand(secondStudySpotInTypicalStudySpots, differentStudiedHours,
                false, false, false)));

        // same name but different studied hours -> returns false
        assertFalse(standardCommand.equals(new LogCommand(firstStudySpotInTypicalStudySpots, differentStudiedHours,
                false, false, false)));

        // same name same hours, different reset -> returns false
        assertFalse(standardCommand.equals(new LogCommand(firstStudySpotInTypicalStudySpots, studiedHours,
                true, false, false)));

        // same name same hours, different override -> returns false
        assertFalse(standardCommand.equals(new LogCommand(firstStudySpotInTypicalStudySpots, studiedHours,
                false, true, false)));

        // same name same hours, different reset all -> returns false
        assertFalse(standardCommand.equals(new LogCommand(firstStudySpotInTypicalStudySpots, studiedHours,
                false, false, true)));
    }
}
