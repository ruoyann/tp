package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SPOT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SPOT;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.StudySpotBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnfavouriteCommand}.
 */
public class UnfavouriteCommandTest {

    private Model model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        StudySpot studySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        model.addStudySpotToFavourites(studySpot);
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(studySpot.getName());

        String expectedMessage =
                String.format(UnfavouriteCommand.MESSAGE_UNFAVOURITE_STUDYSPOT_SUCCESS, studySpot.getName());

        ModelManager expectedModel = new ModelManager(getTypicalStudyTracker(), new UserPrefs());

        assertCommandSuccess(unfavouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        StudySpot invalidStudySpot = new StudySpotBuilder().build();
        assertFalse(model.hasStudySpot(invalidStudySpot));
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(invalidStudySpot.getName());

        assertCommandFailure(unfavouriteCommand, model, Messages.MESSAGE_INVALID_NAME);
    }

    @Test
    public void execute_studySpotIsNotAFavourite_throwsCommandException() {
        StudySpot invalidStudySpot = new StudySpotBuilder().withFavourite(false).build();
        assertFalse(invalidStudySpot.isFavourite());
        model.addStudySpot(invalidStudySpot);
        UnfavouriteCommand unfavouriteCommand = new UnfavouriteCommand(invalidStudySpot.getName());

        String expectedMessage =
                String.format(UnfavouriteCommand.MESSAGE_UNFAVOURITE_REPEATSTUDYSPOT_FAIL, invalidStudySpot.getName());

        assertCommandFailure(unfavouriteCommand, model, expectedMessage);
    }
    @Test
    public void equals() {
        StudySpot firstStudySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        StudySpot secondStudySpot = model.getFilteredStudySpotList().get(INDEX_SECOND_SPOT.getZeroBased());
        UnfavouriteCommand unfavouriteFirstCommand = new UnfavouriteCommand(firstStudySpot.getName());
        UnfavouriteCommand unfavouriteSecondCommand = new UnfavouriteCommand(secondStudySpot.getName());

        // same object -> returns true
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommand));

        // same values -> returns true
        UnfavouriteCommand favouriteFirstCommandCopy = new UnfavouriteCommand(firstStudySpot.getName());
        assertTrue(unfavouriteFirstCommand.equals(favouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(unfavouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unfavouriteFirstCommand.equals(null));

        // different study spot -> returns false
        assertFalse(unfavouriteFirstCommand.equals(unfavouriteSecondCommand));
    }

}
