package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudySpotAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SPOT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SPOT;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudySpot;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    public static final Name INVALID_NAME = new Name("thisIsAnInvalidName");
    public static final Name VALID_NAME_ONE = new Name("validOne");
    public static final Name VALID_NAME_TWO = new Name("validTwo");
    private Model model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        StudySpot studySpotToDelete = model.getFilteredStudySpotList().get(0);
        DeleteCommand deleteCommand = new DeleteCommand(studySpotToDelete.getName());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDYSPOT_SUCCESS, studySpotToDelete);

        ModelManager expectedModel = new ModelManager(model.getStudyTracker(), new UserPrefs());
        expectedModel.deleteStudySpot(studySpotToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(INVALID_NAME);

        boolean isPresent = false;
        List<StudySpot> lastShownList = model.getFullList();
        for (StudySpot current: lastShownList) {
            if (current.isSameName(INVALID_NAME)) {
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EDIT_NAME);
        }
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);

        StudySpot studySpotToDelete = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(studySpotToDelete.getName());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_STUDYSPOT_SUCCESS, studySpotToDelete);

        Model expectedModel = new ModelManager(model.getStudyTracker(), new UserPrefs());
        expectedModel.deleteStudySpot(studySpotToDelete);
        showNoStudySpot(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);

        Index outOfBoundIndex = INDEX_SECOND_SPOT;
        // ensures that outOfBoundIndex is still in bounds of study tracker list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getStudyTracker().getStudySpotList().size());

        DeleteCommand deleteCommand = new DeleteCommand(INVALID_NAME);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EDIT_NAME);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(VALID_NAME_ONE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(VALID_NAME_TWO);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(VALID_NAME_ONE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different study spot -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoStudySpot(Model model) {
        model.updateFilteredStudySpotList(p -> false);

        assertTrue(model.getFilteredStudySpotList().isEmpty());
    }
}
