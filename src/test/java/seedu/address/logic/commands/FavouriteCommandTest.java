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
 * {@code FavouriteCommand}.
 */
public class FavouriteCommandTest {

    private Model model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() {
        StudySpot studySpotToFavourite = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        FavouriteCommand favouriteCommand = new FavouriteCommand(studySpotToFavourite.getName());

        String expectedMessage =
                String.format(FavouriteCommand.MESSAGE_FAVOURITE_STUDYSPOT_SUCCESS, studySpotToFavourite.getName());

        ModelManager expectedModel = new ModelManager(model.getStudyTracker(), new UserPrefs());
        expectedModel.addStudySpotToFavourites(studySpotToFavourite);

        assertCommandSuccess(favouriteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        StudySpot invalidStudySpot = new StudySpotBuilder().build();
        assertFalse(model.hasStudySpot(invalidStudySpot));
        FavouriteCommand favouriteCommand = new FavouriteCommand(invalidStudySpot.getName());

        assertCommandFailure(favouriteCommand, model, Messages.MESSAGE_INVALID_NAME);
    }

    @Test
    public void execute_studySpotAlreadyAFavourite_throwsCommandException() {
        StudySpot invalidStudySpot = new StudySpotBuilder().withFavourite(true).build();
        assertTrue(invalidStudySpot.isFavourite());
        model.addStudySpot(invalidStudySpot);
        FavouriteCommand favouriteCommand = new FavouriteCommand(invalidStudySpot.getName());

        String expectedMessage =
                String.format(FavouriteCommand.MESSAGE_FAVOURITE_REPEATSTUDYSPOT_FAIL, invalidStudySpot.getName());

        assertCommandFailure(favouriteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        StudySpot firstStudySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        StudySpot secondStudySpot = model.getFilteredStudySpotList().get(INDEX_SECOND_SPOT.getZeroBased());
        FavouriteCommand favouriteFirstCommand = new FavouriteCommand(firstStudySpot.getName());
        FavouriteCommand favouriteSecondCommand = new FavouriteCommand(secondStudySpot.getName());

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        FavouriteCommand favouriteFirstCommandCopy = new FavouriteCommand(firstStudySpot.getName());
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different study spot -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }

}
