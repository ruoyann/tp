package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudiedHours;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LogCommand.
 */
public class LogCommandTest {

    private Model model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());

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
