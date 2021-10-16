package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_COMMAND_EXIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_COMMAND_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_LS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_PWD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudySpots.CENTRAL_LIBRARY;
import static seedu.address.testutil.TypicalStudySpots.STARBUCKS;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.alias.Alias;
import seedu.address.model.studyspot.NameContainsKeywordsPredicate;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.StudySpotBuilder;
import seedu.address.testutil.StudyTrackerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new StudyTracker(), new StudyTracker(modelManager.getStudyTracker()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setStudyTrackerFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4, "default"));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setStudyTrackerFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4, "default");
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setStudyTrackerFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setStudyTrackerFilePath(null));
    }

    @Test
    public void setStudyTrackerFilePath_validPath_setsStudyTrackerFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setStudyTrackerFilePath(path);
        assertEquals(path, modelManager.getStudyTrackerFilePath());
    }

    @Test
    public void hasAlias_nullAlias_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAlias(null));
    }

    @Test
    public void addAlias_nullAlias_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addAlias(null));
    }

    @Test
    public void addAlias_newAlias_getsAdded() {
        Alias pwdAlias = new Alias(VALID_ALIAS_PWD, VALID_ALIAS_COMMAND_LIST);
        modelManager.addAlias(pwdAlias);
        assertTrue(modelManager.hasAlias(pwdAlias));
    }

    @Test
    public void addAlias_existingAlias_getsReplaced() {
        Alias lsAlias = new Alias(VALID_ALIAS_LS, VALID_ALIAS_COMMAND_EXIT);
        Alias lsAliasOld = new Alias(VALID_ALIAS_LS, VALID_ALIAS_COMMAND_LIST);
        modelManager.addAlias(lsAlias);
        assertTrue(modelManager.hasAlias(lsAlias));
        assertTrue(modelManager.getUserPrefs().getUserAliases().contains(lsAlias));
        assertFalse(modelManager.getUserPrefs().getUserAliases().contains(lsAliasOld));
    }

    @Test
    public void removeAlias_nullAlias_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.removeAlias(null));
    }

    @Test
    public void removeAlias_notInUserPrefs_aliasesUnchanged() {
        Alias unknownAlias = new Alias("abd", VALID_ALIAS_COMMAND_LIST);
        assertFalse(modelManager.hasAlias(unknownAlias));

        List<Alias> initialAliases = modelManager.getUserPrefs().getUserAliases();
        modelManager.removeAlias(unknownAlias);
        assertEquals(initialAliases, modelManager.getUserPrefs().getUserAliases());
    }

    @Test
    public void removeAlias_aliasInUserPrefs_getsRemoved() {
        Alias toRemove = new Alias(VALID_ALIAS_LS, VALID_ALIAS_COMMAND_LIST);
        assertTrue(modelManager.hasAlias(toRemove));

        List<Alias> initialAliases = modelManager.getUserPrefs().getUserAliases();
        modelManager.removeAlias(toRemove);
        assertFalse(modelManager.hasAlias(toRemove));
    }

    @Test
    public void hasStudySpot_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudySpot(null));
    }

    @Test
    public void hasStudySpot_studySpotNotInStudyTracker_returnsFalse() {
        assertFalse(modelManager.hasStudySpot(STARBUCKS));
    }

    @Test
    public void addStudySpot_studySpotNotAFavourite_returnsFalse() {
        assertFalse(STARBUCKS.isFavourite());
        modelManager.addStudySpot(STARBUCKS);
        assertFalse(modelManager.isFavouriteStudySpot(STARBUCKS));
    }

    @Test
    public void addStudySpot_studySpotIsAFavourite_returnsTrue() {
        StudySpot test = new StudySpotBuilder(STARBUCKS).withFavourite(true).build();
        assertTrue(test.isFavourite());
        modelManager.addStudySpot(test);
        assertTrue(modelManager.isFavouriteStudySpot(test));
    }

    @Test
    public void hasStudySpot_studySpotInStudyTracker_returnsTrue() {
        modelManager.addStudySpot(STARBUCKS);
        assertTrue(modelManager.hasStudySpot(STARBUCKS));
    }

    @Test
    public void isFavouriteStudySpot_nullStudySpot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.isFavouriteStudySpot(null));
    }

    @Test
    public void isFavouriteStudySpot_studySpotNotInFavourites_returnsFalse() {
        modelManager.addStudySpot(STARBUCKS);
        assertFalse(modelManager.isFavouriteStudySpot(STARBUCKS));
    }

    @Test
    public void isFavouriteStudySpot_studySpotInFavourites_returnsTrue() {
        modelManager.addStudySpot(STARBUCKS);
        modelManager.addStudySpotToFavourites(STARBUCKS);
        assertTrue(modelManager.isFavouriteStudySpot(STARBUCKS));
    }

    @Test
    public void getFilteredStudySpotList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudySpotList().remove(0));
    }

    @Test
    public void equals() {
        StudyTracker studyTracker = new StudyTrackerBuilder().withStudySpot(STARBUCKS)
                .withStudySpot(CENTRAL_LIBRARY).build();
        StudyTracker differentStudyTracker = new StudyTracker();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(studyTracker, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(studyTracker, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different studyTracker -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentStudyTracker, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = STARBUCKS.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudySpotList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(studyTracker, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudySpotList(PREDICATE_SHOW_ALL_STUDYSPOTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setStudyTrackerFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(studyTracker, differentUserPrefs)));
    }
}
