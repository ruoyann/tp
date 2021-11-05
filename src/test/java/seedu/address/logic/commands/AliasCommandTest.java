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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.alias.Alias;

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
        assertEquals(String.format(MESSAGE_SUCCESS_SHOW,
                modelStub.getUserPrefs().getUserAliases().stream()
                        .map(Alias::toString).collect(Collectors.joining("\n"))),
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
        assertEquals(String.format(MESSAGE_SUCCESS_SHOW,
                modelStub.getUserPrefs().getUserAliases().stream()
                        .map(Alias::toString).collect(Collectors.joining("\n"))),
                showTypeAliasCommandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Alias lsAlias = new Alias(VALID_ALIAS_LS, VALID_ALIAS_COMMAND_LIST);
        Alias pwdAlias = new Alias(VALID_ALIAS_PWD, VALID_ALIAS_COMMAND_LIST);
        AliasCommand lsAliasCommand = new AliasCommand(false, lsAlias);
        AliasCommand pwdAliasCommand = new AliasCommand(false, pwdAlias);
        AliasCommand showTypeAliasCommand = new AliasCommand(true);
        AliasCommand anotherShowTypeAliasCommand = new AliasCommand(true, lsAlias);

        // same showtype -> returns true (check if AliasCommand defensively sets alias to null when isShowType)
        assertTrue(showTypeAliasCommand.equals(anotherShowTypeAliasCommand));

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

        // different alias -> returns false
        assertFalse(lsAliasCommand.equals(pwdAliasCommand));
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
