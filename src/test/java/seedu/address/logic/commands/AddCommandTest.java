package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.StudySpotBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_validStudySpotAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudySpotAdded modelStub = new ModelStubAcceptingStudySpotAdded();
        StudySpot validStudySpot = new StudySpotBuilder().build();

        CommandResult commandResult = new AddCommand(validStudySpot).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validStudySpot), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudySpot), modelStub.studySpotsAdded);
    }

    @Test
    public void execute_duplicateStudySpot_throwsCommandException() {
        StudySpot validStudySpot = new StudySpotBuilder().build();
        AddCommand addCommand = new AddCommand(validStudySpot);
        ModelStub modelStub = new ModelStubWithStudySpot(validStudySpot);

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_DUPLICATE_STUDYSPOT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        StudySpot frontier = new StudySpotBuilder().withName("Frontier").build();
        StudySpot deck = new StudySpotBuilder().withName("Deck").build();
        AddCommand addFrontierCommand = new AddCommand(frontier);
        AddCommand addDeckCommand = new AddCommand(deck);

        // same object -> returns true
        assertTrue(addFrontierCommand.equals(addFrontierCommand));

        // same values -> returns true
        AddCommand addFrontierCommandCopy = new AddCommand(frontier);
        assertTrue(addFrontierCommand.equals(addFrontierCommandCopy));

        // different types -> returns false
        assertFalse(addFrontierCommand.equals(1));

        // null -> returns false
        assertFalse(addFrontierCommand.equals(null));

        // different study spot -> returns false
        assertFalse(addFrontierCommand.equals(addDeckCommand));
    }

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

    /**
     * A Model stub that always accept the study spot being added.
     */
    private class ModelStubAcceptingStudySpotAdded extends ModelStub {
        final ArrayList<StudySpot> studySpotsAdded = new ArrayList<>();

        @Override
        public boolean hasStudySpot(StudySpot studySpot) {
            requireNonNull(studySpot);
            return studySpotsAdded.stream().anyMatch(studySpot::isSameStudySpot);
        }

        @Override
        public void addStudySpot(StudySpot studySpot) {
            requireNonNull(studySpot);
            studySpotsAdded.add(studySpot);
        }

        @Override
        public ReadOnlyStudyTracker getStudyTracker() {
            return new StudyTracker();
        }
    }

}
