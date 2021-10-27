package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showNoStudySpot;
import static seedu.address.logic.commands.CommandTestUtil.showStudySpotAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;
import static seedu.address.model.Model.PREDICATE_SHOW_FAVOURITES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SPOT;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.StudySpot;
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
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null, null), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null, null), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsOneStudySpot() {
        Tag coffee = new Tag("coffee");
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(coffee));
        Amenity wifi = new Amenity("wifi");
        Set<Amenity> amenitySet = new HashSet<>(Arrays.asList(wifi));
        StudySpot studySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        assertTrue(studySpot.getTags().contains(coffee));
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        showStudySpotAtIndex(expectedModel, INDEX_FIRST_SPOT);
        Predicate<StudySpot> predicate = ListCommand.containsTag(coffee);
        assertCommandSuccess(new ListCommand(predicate, false, tagSet, amenitySet), model,
                ListCommand.MESSAGE_SUCCESS + ListCommand.getFilterMessage(false, tagSet, amenitySet), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsNoStudySpot() {
        Tag tag = new Tag("test123");
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(tag));
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        showNoStudySpot(expectedModel);
        Predicate<StudySpot> predicate = ListCommand.containsTag(tag);
        assertTrue(model.getFullList().stream().filter(predicate).collect(Collectors.toList()).isEmpty());
        assertCommandSuccess(new ListCommand(predicate, false, tagSet, null), model,
                ListCommand.MESSAGE_SUCCESS + ListCommand.getFilterMessage(false, tagSet, null), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsOneFavStudySpot() {
        StudySpot studySpot = model.getFullList().get(INDEX_FIRST_SPOT.getZeroBased());
        model.addStudySpotToFavourites(studySpot);

        // Ensure only one study spot is favourited
        assertTrue(model.getFullList().stream().filter(PREDICATE_SHOW_FAVOURITES).collect(Collectors.toList())
                .size() == 1);

        // Expected model
        expectedModel.addStudySpotToFavourites(studySpot);
        showStudySpotAtIndex(expectedModel, INDEX_FIRST_SPOT);

        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_FAVOURITES, false, null, null), model,
                ListCommand.MESSAGE_SUCCESS + ListCommand.getFilterMessage(false, null, null), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsNoFavStudySpot() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        assertTrue(model.getFullList().stream().filter(PREDICATE_SHOW_FAVOURITES).collect(Collectors.toList())
                .isEmpty());

        showNoStudySpot(expectedModel);

        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_FAVOURITES, false, null, null), model,
                ListCommand.MESSAGE_SUCCESS + ListCommand.getFilterMessage(false, null, null), expectedModel);
    }

    @Test
    public void equals() {
        ListCommand cmd1 = new ListCommand(PREDICATE_SHOW_FAVOURITES, true, null, null);
        ListCommand cmd2 = new ListCommand(PREDICATE_SHOW_FAVOURITES, true, null, null);
        assertTrue(cmd1.equals(cmd2));
    }
}
