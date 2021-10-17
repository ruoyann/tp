package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DECK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OPERATING_HOURS_FRONTIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_DECK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_QUIET;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditStudySpotDescriptor;
import seedu.address.testutil.EditStudySpotDescriptorBuilder;

public class EditStudySpotDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditStudySpotDescriptor descriptorWithSameValues = new EditStudySpotDescriptor(DESC_FRONTIER);
        boolean a = DESC_FRONTIER.equals(descriptorWithSameValues);
        assertTrue(DESC_FRONTIER.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_FRONTIER.equals(DESC_FRONTIER));

        // null -> returns false
        assertFalse(DESC_FRONTIER.equals(null));

        // different types -> returns false
        assertFalse(DESC_FRONTIER.equals(5));

        // different values -> returns false
        assertFalse(DESC_FRONTIER.equals(DESC_DECK));

        // different name -> returns false
        EditStudySpotDescriptor editedFrontier = new EditStudySpotDescriptorBuilder(DESC_FRONTIER)
                .withName(VALID_NAME_DECK).build();
        assertFalse(DESC_FRONTIER.equals(editedFrontier));

        // different rating -> returns false
        editedFrontier = new EditStudySpotDescriptorBuilder(DESC_FRONTIER).withRating(VALID_RATING_DECK).build();
        assertFalse(DESC_FRONTIER.equals(editedFrontier));

        // different email -> returns false
        editedFrontier = new EditStudySpotDescriptorBuilder(DESC_FRONTIER)
                .withOperatingHours(VALID_OPERATING_HOURS_FRONTIER).build();
        assertFalse(DESC_FRONTIER.equals(editedFrontier));

        // different address -> returns false
        editedFrontier = new EditStudySpotDescriptorBuilder(DESC_FRONTIER).withAddress(VALID_ADDRESS_DECK).build();
        assertFalse(DESC_FRONTIER.equals(editedFrontier));

        // different tags -> returns false
        editedFrontier = new EditStudySpotDescriptorBuilder(DESC_FRONTIER).withAddedTags(VALID_TAG_QUIET).build();
        assertFalse(DESC_FRONTIER.equals(editedFrontier));
    }
}
