package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_QUIET;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudySpotAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SPOT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SPOT;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditStudySpotDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.StudyTracker;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.EditStudySpotDescriptorBuilder;
import seedu.address.testutil.StudySpotBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        StudySpot editedStudySpot = new StudySpotBuilder().build();
        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder(editedStudySpot).build();
        EditCommand editCommand = new EditCommand(firstStudySpotInTypicalStudySpots, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYSPOT_SUCCESS, editedStudySpot);

        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());
        expectedModel.setStudySpot(model.getFilteredStudySpotList().get(0), editedStudySpot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastStudySpot = Index.fromOneBased(model.getFilteredStudySpotList().size());
        Name lastStudySpotInTypicalStudySpots = new Name("LT17");
        StudySpot lastStudySpot = model.getFilteredStudySpotList().get(indexLastStudySpot.getZeroBased());

        StudySpotBuilder spotInList = new StudySpotBuilder(lastStudySpot);
        StudySpot editedStudySpot = spotInList.withName(VALID_NAME_DECK).withRating(VALID_RATING_DECK)
                .withTags(VALID_TAG_QUIET).build();

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_DECK)
                .withRating(VALID_RATING_DECK).withTags(VALID_TAG_QUIET).build();
        EditCommand editCommand = new EditCommand(lastStudySpotInTypicalStudySpots, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYSPOT_SUCCESS, editedStudySpot);

        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());
        expectedModel.setStudySpot(lastStudySpot, editedStudySpot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        EditCommand editCommand = new EditCommand(firstStudySpotInTypicalStudySpots, new EditStudySpotDescriptor());
        StudySpot editedStudySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYSPOT_SUCCESS, editedStudySpot);

        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);

        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        StudySpot studySpotInFilteredList = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        StudySpot editedStudySpot = new StudySpotBuilder(studySpotInFilteredList).withName(VALID_NAME_DECK).build();
        EditCommand editCommand = new EditCommand(firstStudySpotInTypicalStudySpots,
                new EditStudySpotDescriptorBuilder().withName(VALID_NAME_DECK).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYSPOT_SUCCESS, editedStudySpot);

        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());
        expectedModel.setStudySpot(model.getFilteredStudySpotList().get(0), editedStudySpot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudySpotUnfilteredList_failure() {
        Name secondStudySpotInTypicalStudySpots = new Name("Central library");
        StudySpot firstStudySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder(firstStudySpot).build();
        EditCommand editCommand = new EditCommand(secondStudySpotInTypicalStudySpots, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_STUDYSPOT);
    }

    @Test
    public void execute_duplicateStudySpotFilteredList_failure() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);

        // edit study spot in filtered list into a duplicate in study tracker
        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        StudySpot studySpotInList = model.getStudyTracker().getStudySpotList().get(INDEX_SECOND_SPOT.getZeroBased());
        EditCommand editCommand = new EditCommand(firstStudySpotInTypicalStudySpots,
                new EditStudySpotDescriptorBuilder(studySpotInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_STUDYSPOT);
    }

    @Test
    public void execute_invalidStudySpotIndexUnfilteredList_failure() {
        Name notInTypicalStudySpots = new Name("Test not in typical study spots");
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_DECK).build();
        EditCommand editCommand = new EditCommand(notInTypicalStudySpots, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EDIT_NAME);
    }

    @Test
    public void equals() {
        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        final EditCommand standardCommand = new EditCommand(firstStudySpotInTypicalStudySpots, DESC_FRONTIER);

        // same values -> returns true
        Name secondStudySpotInTypicalStudySpots = new Name("Central library");
        EditStudySpotDescriptor copyDescriptor = new EditStudySpotDescriptor(DESC_FRONTIER);
        EditCommand commandWithSameValues = new EditCommand(firstStudySpotInTypicalStudySpots, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(secondStudySpotInTypicalStudySpots, DESC_FRONTIER)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(firstStudySpotInTypicalStudySpots, DESC_DECK)));
    }

}
