package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        CommandResult commandResultInitial = new LogCommand(name, studiedHours, false, false).execute(expectedModel);
        CommandResult commandResultReset = new LogCommand(name, studiedHours, true, false).execute(expectedModel);
        CommandResult commandResultOverride = new LogCommand(name, studiedHours, false, true).execute(expectedModel);

        String expectedCommandInitial = String.format(LogCommand.MESSAGE_SUCCESS_DEFAULT, studiedHours.toString(),
                name.toString());
        String expectedCommandReset = String.format(LogCommand.MESSAGE_SUCCESS_RESET, name.toString());
        String expectedCommandOverride = String.format(LogCommand.MESSAGE_SUCCESS_OVERRIDE, studiedHours,
                name);

        assertEquals(String.format(expectedCommandInitial, studySpotToLog),
                commandResultInitial.getFeedbackToUser());
        assertEquals(String.format(expectedCommandReset, studySpotToLog),
                commandResultReset.getFeedbackToUser());
        assertEquals(String.format(expectedCommandOverride, studySpotToLog),
                commandResultOverride.getFeedbackToUser());
    }

    @Test
    public void execute_invalidStudySpotName_failure() {
        Name notInTypicalStudySpots = new Name("Invalid Name");
        StudiedHours studiedHours = new StudiedHours("4");
        LogCommand logCommand = new LogCommand(notInTypicalStudySpots, studiedHours, false, false);

        assertCommandFailure(logCommand, model, Messages.MESSAGE_INVALID_NAME);
    }

    @Test
    public void execute_invalidStudiedHoursIntegerOverflow_failure() throws CommandException {
        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());

        Name name = new Name("Starbucks");
        StudiedHours studiedHours = new StudiedHours("2147483647");
        LogCommand logCommand = new LogCommand(name, studiedHours, false, false);

        assertCommandFailure(logCommand, expectedModel, StudiedHours.MESSAGE_HOURS_IS_FULL);
    }

    @Test
    public void equals() {
        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        StudiedHours studiedHours = new StudiedHours("4");
        final LogCommand standardCommand = new LogCommand(firstStudySpotInTypicalStudySpots, studiedHours, false,
                false);

        // same values -> returns true
        Name secondStudySpotInTypicalStudySpots = new Name("Central library");
        StudiedHours copyStudiedHours = new StudiedHours("4");
        StudiedHours differentStudiedHours = new StudiedHours("7");
        LogCommand commandWithSameValues = new LogCommand(firstStudySpotInTypicalStudySpots, copyStudiedHours,
                false, false);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new LogCommand(secondStudySpotInTypicalStudySpots, copyStudiedHours,
                false, false)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new LogCommand(secondStudySpotInTypicalStudySpots, differentStudiedHours,
                false, false)));
    }
}
