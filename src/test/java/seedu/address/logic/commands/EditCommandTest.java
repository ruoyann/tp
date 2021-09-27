package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showStudySpotAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalStudySpots.getTypicalAddressBook;

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

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
        Name lastStudySpotInTypicalStudySpots = new Name("Fiona Kunz");
        StudySpot lastStudySpot = model.getFilteredStudySpotList().get(indexLastStudySpot.getZeroBased());

        StudySpotBuilder personInList = new StudySpotBuilder(lastStudySpot);
        StudySpot editedStudySpot = personInList.withName(VALID_NAME_BOB).withRating(VALID_RATING_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_BOB)
                .withRating(VALID_RATING_BOB).withTags(VALID_TAG_HUSBAND).build();
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
        StudySpot editedStudySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYSPOT_SUCCESS, editedStudySpot);

        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showStudySpotAtIndex(model, INDEX_FIRST_PERSON);

        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        StudySpot personInFilteredList = model.getFilteredStudySpotList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudySpot editedStudySpot = new StudySpotBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(firstStudySpotInTypicalStudySpots,
                new EditStudySpotDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_STUDYSPOT_SUCCESS, editedStudySpot);

        Model expectedModel = new ModelManager(new StudyTracker(model.getStudyTracker()), new UserPrefs());
        expectedModel.setStudySpot(model.getFilteredStudySpotList().get(0), editedStudySpot);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateStudySpotUnfilteredList_failure() {
        Name secondStudySpotInTypicalStudySpots = new Name("Central library");
        StudySpot firstStudySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder(firstStudySpot).build();
        EditCommand editCommand = new EditCommand(secondStudySpotInTypicalStudySpots, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicateStudySpotFilteredList_failure() {
        showStudySpotAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in study tracker
        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        StudySpot personInList = model.getStudyTracker().getStudySpotList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(firstStudySpotInTypicalStudySpots,
                new EditStudySpotDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidStudySpotIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredStudySpotList().size() + 1);
        Name notInTypicalStudySpots = new Name("Test not in typical study spots");
        EditStudySpotDescriptor descriptor = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(notInTypicalStudySpots, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EDIT_NAME);
    }

    //    Removed this test because I think its not applicable anymore
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of study tracker
     */
    //    @Test
    //    public void execute_invalidStudySpotIndexFilteredList_failure() {
    //        showStudySpotAtIndex(model, INDEX_FIRST_PERSON);
    //        Index outOfBoundIndex = INDEX_SECOND_PERSON;
    //        // ensures that outOfBoundIndex is still in bounds of study tracker list
    //        assertTrue(outOfBoundIndex.getZeroBased() < model.getStudyTracker().getStudySpotList().size());
    //
    //        EditCommand editCommand = new EditCommand(outOfBoundIndex,
    //                new EditStudySpotDescriptorBuilder().withName(VALID_NAME_BOB).build());
    //
    //        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_STUDYSPOT_DISPLAYED_INDEX);
    //    }

    @Test
    public void equals() {
        Name firstStudySpotInTypicalStudySpots = new Name("Starbucks");
        final EditCommand standardCommand = new EditCommand(firstStudySpotInTypicalStudySpots, DESC_AMY);

        // same values -> returns true
        Name secondStudySpotInTypicalStudySpots = new Name("Central library");
        EditStudySpotDescriptor copyDescriptor = new EditStudySpotDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(firstStudySpotInTypicalStudySpots, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(secondStudySpotInTypicalStudySpots, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(firstStudySpotInTypicalStudySpots, DESC_BOB)));
    }

}
