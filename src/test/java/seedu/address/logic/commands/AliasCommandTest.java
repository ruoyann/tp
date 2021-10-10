package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AliasCommand.MESSAGE_SUCCESS_SET;
import static seedu.address.logic.commands.AliasCommand.MESSAGE_SUCCESS_SHOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_COMMAND_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_LS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_PWD;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;
import seedu.address.model.studyspot.StudySpot;

class AliasCommandTest {

    @Test
    public void constructor_nullAlias_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AliasCommand(true, null));
        assertThrows(NullPointerException.class, () -> new AliasCommand(false, null));
    }

    @Test
    public void execute_validAliasAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        Alias validAlias = new Alias(VALID_ALIAS_LS, VALID_ALIAS_COMMAND_LIST);

        CommandResult addTypeAliasCommandResult = new AliasCommand(false, validAlias).execute(modelStub);

        assertEquals(String.format(MESSAGE_SUCCESS_SET, validAlias), addTypeAliasCommandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAlias), modelStub.aliasesAdded);
    }

    @Test
    public void execute_validShowTypeAlias_runs() throws Exception {
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();

        CommandResult showTypeAliasCommandResult = new AliasCommand(true).execute(modelStub);

        // shows the default aliases
        assertEquals(String.format(MESSAGE_SUCCESS_SHOW, modelStub.getUserPrefs().getUserAliases()),
                showTypeAliasCommandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validAliasAddedToModel_runs() throws Exception {
        Alias listAlias = new Alias(VALID_ALIAS_PWD, VALID_ALIAS_COMMAND_LIST);
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();

        CommandResult addTypeAliasCommandResult = new AliasCommand(false, listAlias).execute(modelStub);
        assertEquals(String.format(MESSAGE_SUCCESS_SET, listAlias), addTypeAliasCommandResult.getFeedbackToUser());

        // running showType Alias Command now shows the newly added Alias
        CommandResult showTypeAliasCommandResult = new AliasCommand(true).execute(modelStub);
        assertTrue(modelStub.hasAlias(listAlias));
        assertEquals(String.format(MESSAGE_SUCCESS_SHOW, modelStub.getUserPrefs().getUserAliases()),
                showTypeAliasCommandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Alias lsAlias = new Alias(VALID_ALIAS_LS, VALID_ALIAS_COMMAND_LIST);
        Alias pwdAlias = new Alias(VALID_ALIAS_PWD, VALID_ALIAS_COMMAND_LIST);
        AliasCommand lsAliasCommand = new AliasCommand(false, lsAlias);
        AliasCommand pwdAliasCommand = new AliasCommand(false, pwdAlias);
        AliasCommand showTypeAliasCommand = new AliasCommand(true);

        // same object -> returns true
        assertTrue(lsAliasCommand.equals(lsAliasCommand));

        // same values -> returns true
        AliasCommand lsAliasCommandCopy = new AliasCommand(false, lsAlias);
        assertTrue(lsAliasCommand.equals(lsAliasCommandCopy));

        // same values -> returns true
        AliasCommand showTypeAliasCommandCopy = new AliasCommand(true);
        assertTrue(showTypeAliasCommand.equals(showTypeAliasCommandCopy));

        // different showType -> returns false
        assertFalse(lsAliasCommand.equals(showTypeAliasCommand));

        // different Object type -> returns false
        assertFalse(lsAliasCommand.equals(1));

        // null -> returns false
        assertFalse(lsAliasCommand.equals(null));

        // different study spot -> returns false
        assertFalse(lsAliasCommand.equals(pwdAliasCommand));
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
        public void addAlias(Alias aliasToAdd) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAlias(Alias alias) {
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
        public void addStudySpot(StudySpot studySpot) {
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
        public boolean hasStudySpot(StudySpot studySpot) {
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
        public boolean isFavouriteStudySpot(StudySpot studySpot) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StudySpot addStudySpotToFavourites(StudySpot studySpot) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public StudySpot removeStudySpotFromFavourites(StudySpot studySpot) {
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

        @Override
        public ObservableList<StudySpot> getFullList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single Alias.
     */
    private class ModelStubWithAlias extends ModelStub {
        private final Alias alias;

        ModelStubWithAlias(Alias alias) {
            requireNonNull(alias);
            this.alias = alias;
        }

        @Override
        public boolean hasAlias(Alias alias) {
            requireNonNull(alias);
            return this.alias.isSameAlias(alias);
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return new UserPrefs();
        }
    }

    /**
     * A Model stub that always accept the Alias being added.
     */
    private class ModelStubAcceptingAliasAdded extends ModelStub {
        final ArrayList<Alias> aliasesAdded = new ArrayList<>();

        @Override
        public boolean hasAlias(Alias alias) {
            requireNonNull(alias);
            return aliasesAdded.stream().anyMatch(alias::isSameAlias);
        }

        @Override
        public void addAlias(Alias alias) {
            requireNonNull(alias);
            aliasesAdded.add(alias);
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return new UserPrefs();
        }
    }
}
