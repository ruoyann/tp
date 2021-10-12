package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudySpotAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;
import static seedu.address.model.Model.PREDICATE_SHOW_FAVOURITES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SPOT;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());
        expectedModel = new ModelManager(model.getStudyTracker(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsAFiltered_showsEverything() {
        Tag coffee = new Tag("coffee");
        assert(STARBUCKS.getTags().contains(new Tag("coffee")));
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand cmd1 = new ListCommand(PREDICATE_SHOW_FAVOURITES, true, null);
        ListCommand cmd2 = new ListCommand(PREDICATE_SHOW_FAVOURITES, true, null);
        assertTrue(cmd1.equals(cmd2));
    }
}
