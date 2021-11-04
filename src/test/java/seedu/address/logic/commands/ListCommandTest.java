package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.StudySpotBuilder;

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
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null, null, null), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, false, null, null, null), model,
                ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsOneStudySpot() {
        Tag coffee = new Tag("coffee");
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(coffee));
        Amenity wifi = new Amenity("wifi");
        Set<Amenity> amenitySet = new HashSet<>(Arrays.asList(wifi));
        Rating rating = new Rating("5");
        StudySpot studySpot = model.getFilteredStudySpotList().get(INDEX_FIRST_SPOT.getZeroBased());
        assertTrue(studySpot.getTags().contains(coffee));
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        showStudySpotAtIndex(expectedModel, INDEX_FIRST_SPOT);
        Predicate<StudySpot> predicate = ListCommand.containsTag(coffee);
        assertCommandSuccess(new ListCommand(predicate, true, tagSet, amenitySet, rating), model,
                ListCommand.MESSAGE_SUCCESS + ListCommand.getFilterMessage(true, tagSet, amenitySet, rating),
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsNoStudySpot() {
        Tag tag = new Tag("test123");
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(tag));
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        showNoStudySpot(expectedModel);
        Predicate<StudySpot> predicate = ListCommand.containsTag(tag);
        assertTrue(model.getFullList().stream().filter(predicate).collect(Collectors.toList()).isEmpty());
        assertCommandSuccess(new ListCommand(predicate, false, tagSet, null, null), model,
                ListCommand.MESSAGE_SUCCESS + ListCommand.getFilterMessage(false, tagSet, null, null), expectedModel);
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

        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_FAVOURITES, false, null, null, null), model,
                ListCommand.MESSAGE_SUCCESS + ListCommand.getFilterMessage(false, null, null, null), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsNoFavStudySpot() {
        showStudySpotAtIndex(model, INDEX_FIRST_SPOT);
        assertTrue(model.getFullList().stream().filter(PREDICATE_SHOW_FAVOURITES).collect(Collectors.toList())
                .isEmpty());

        showNoStudySpot(expectedModel);

        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_FAVOURITES, false, null, null, null), model,
                ListCommand.MESSAGE_SUCCESS + ListCommand.getFilterMessage(false, null, null, null), expectedModel);
    }

    @Test
    public void equals() {
        ListCommand cmd1 = new ListCommand(PREDICATE_SHOW_FAVOURITES, true, null, null, null);
        ListCommand cmd2 = new ListCommand(PREDICATE_SHOW_ALL_STUDYSPOTS, true, null, null, null);
        assertTrue(cmd1.equals(cmd1));
        assertTrue(cmd1.equals(cmd2));

        Tag coffee = new Tag("coffee");
        Tag tag = new Tag("test123");
        Set<Tag> tagSet = new HashSet<>(Arrays.asList(coffee, tag));
        Amenity wifi = new Amenity("wifi");
        Amenity charger = new Amenity("charger");
        Set<Amenity> amenitySet = new HashSet<>(Arrays.asList(wifi, charger));
        Predicate<StudySpot> wifiTest = ListCommand.containsAmenity(wifi);
        Predicate<StudySpot> chargerTest = ListCommand.containsAmenity(charger);
        Rating ratingZero = new Rating("0");
        Rating ratingFive = new Rating("5");
        StudySpot studySpotZero = new StudySpotBuilder().withRating("0").build();
        StudySpot studySpotFive = new StudySpotBuilder().withRating("5").build();
        Predicate<StudySpot> zeroTest = ListCommand.containsRating(ratingZero);
        Predicate<StudySpot> fiveTest = ListCommand.containsRating(ratingFive);

    }

    @Test
    public void containsAmenity() {
        Amenity wifi = new Amenity("wifi");
        Amenity charger = new Amenity("charger");
        StudySpot studySpot = new StudySpotBuilder().build();
        StudySpot studySpotWifi = new StudySpotBuilder().withAmenities("wifi").build();
        StudySpot studySpotCharger = new StudySpotBuilder().withAmenities("charger").build();
        Predicate<StudySpot> wifiTest = ListCommand.containsAmenity(wifi);
        Predicate<StudySpot> chargerTest = ListCommand.containsAmenity(charger);

        assertTrue(wifiTest.test(studySpotWifi));
        assertTrue(chargerTest.test(studySpotCharger));

        assertFalse(chargerTest.test(studySpotWifi));
        assertFalse(wifiTest.test(studySpotCharger));

        assertFalse(chargerTest.test(studySpot));
        assertFalse(wifiTest.test(studySpot));
    }

    @Test
    public void containsRating() {
        Rating ratingZero = new Rating("0");
        Rating ratingFive = new Rating("5");
        StudySpot studySpotZero = new StudySpotBuilder().withRating("0").build();
        StudySpot studySpotFive = new StudySpotBuilder().withRating("5").build();
        Predicate<StudySpot> zeroTest = ListCommand.containsRating(ratingZero);
        Predicate<StudySpot> fiveTest = ListCommand.containsRating(ratingFive);

        assertTrue(zeroTest.test(studySpotZero));
        assertTrue(fiveTest.test(studySpotFive));

        assertFalse(zeroTest.test(studySpotFive));
        assertFalse(fiveTest.test(studySpotZero));
    }
}
