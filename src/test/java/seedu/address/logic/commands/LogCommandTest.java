package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMENITY_AIRCON;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.EditStudySpotDescriptorBuilder;
import seedu.address.testutil.StudySpotBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LogCommand.
 */
public class LogCommandTest {

    private Model model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());


    /**
     * A Model stub that contains a single study spot.
     */
    private class ModelStubWithStudySpot extends ModelStub {
        private final StudySpot studySpot;

        ModelStubWithStudySpot(StudySpot studySpot) {
            requireNonNull(studySpot);
            this.studySpot = studySpot;
        }

        @Override
        public boolean hasStudySpot(StudySpot studySpot) {
            requireNonNull(studySpot);
            return this.studySpot.isSameStudySpot(studySpot);
        }
    }

    @Test
    public void execute_validInitialLog_logSuccessful() throws CommandException {
        StudySpot studySpotToLog = new StudySpotBuilder().withName("Starbucks").build();
        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());

        Name name = new Name("Starbucks");
        StudiedHours studiedHours = new StudiedHours("4");
        CommandResult commandResult = new LogCommand(name, studiedHours, false, false).execute(expectedModel);

        String expectedCommand = String.format(LogCommand.MESSAGE_SUCCESS_DEFAULT, studiedHours.toString(),
                name.toString());

        assertEquals(String.format(expectedCommand, studySpotToLog),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validHandleResetLog_logSuccessful() throws CommandException {
        StudySpot studySpotToLog = new StudySpotBuilder().withName("Starbucks").build();
        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());

        Name name = new Name("Starbucks");
        StudiedHours studiedHours = new StudiedHours("4");
        CommandResult commandResult = new LogCommand(name, studiedHours, true, false).execute(expectedModel);

        String expectedCommand = String.format(LogCommand.MESSAGE_SUCCESS_RESET, name.toString());

        assertEquals(String.format(expectedCommand, studySpotToLog),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validHandleOverrideLog_logSuccessful() throws CommandException {
        StudySpot studySpotToLog = new StudySpotBuilder().withName("Starbucks").build();
        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());

        Name name = new Name("Starbucks");
        StudiedHours studiedHours = new StudiedHours("4");
        CommandResult commandResult = new LogCommand(name, studiedHours, false, true).execute(expectedModel);

        String expectedCommand = String.format(LogCommand.MESSAGE_SUCCESS_OVERRIDE, studiedHours.toString(),
                name.toString());

        assertEquals(String.format(expectedCommand, studySpotToLog),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidStudySpotIndex_failure() {
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

        assertCommandFailure(logCommand, model, StudiedHours.MESSAGE_HOURS_IS_FULL);
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
