package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.StudyTracker;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.StudySpotBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
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

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        StudySpot alice = new StudySpotBuilder().withName("Alice").build();
        StudySpot bob = new StudySpotBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getStudyTrackerFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudyTrackerFilePath(Path studyTrackerFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudySpot(StudySpot person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudyTracker(ReadOnlyStudyTracker newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyStudyTracker getStudyTracker() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudySpot(StudySpot person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudySpot(StudySpot target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudySpot(StudySpot target, StudySpot editedStudySpot) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<StudySpot> getFilteredStudySpotList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudySpotList(Predicate<StudySpot> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithStudySpot extends ModelStub {
        private final StudySpot person;

        ModelStubWithStudySpot(StudySpot person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasStudySpot(StudySpot person) {
            requireNonNull(person);
            return this.person.isSameStudySpot(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingStudySpotAdded extends ModelStub {
        final ArrayList<StudySpot> studySpotsAdded = new ArrayList<>();

        @Override
        public boolean hasStudySpot(StudySpot person) {
            requireNonNull(person);
            return studySpotsAdded.stream().anyMatch(person::isSameStudySpot);
        }

        @Override
        public void addStudySpot(StudySpot person) {
            requireNonNull(person);
            studySpotsAdded.add(person);
        }

        @Override
        public ReadOnlyStudyTracker getStudyTracker() {
            return new StudyTracker();
        }
    }

}
