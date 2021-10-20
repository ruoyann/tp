package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.StudyTracker;
import seedu.address.model.studyspot.NameContainsKeywordsPredicate;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.EditStudySpotDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_FRONTIER = "Frontier Canteen";
    public static final String VALID_NAME_DECK = "The Deck Canteen";
    public static final String VALID_RATING_FRONTIER = "3";
    public static final String VALID_RATING_DECK = "2";
    public static final String VALID_OPERATING_HOURS_FRONTIER = "0900-1800, 0900-1800";
    public static final String VALID_OPERATING_HOURS_DECK = "1200-1800, 1200-1800";
    public static final String VALID_ADDRESS_FRONTIER = "NUS Science Faculty";
    public static final String VALID_ADDRESS_DECK = "NUS FASS";
    public static final String VALID_TAG_QUIET = "quiet";
    public static final String VALID_TAG_CROWDED = "crowded";
    public static final String VALID_TAG_COLD = "cold";
    public static final String VALID_TAG_COFFEE = "coffee";
    public static final String VALID_ALIAS_LS = "ls";
    public static final String VALID_ALIAS_PWD = "pwd";
    public static final String VALID_ALIAS_COMMAND_LIST = "list";
    public static final String VALID_ALIAS_COMMAND_EXIT = "exit";
    public static final String VALID_AMENITY_WIFI = "wifi";
    public static final String VALID_AMENITY_AIRCON = "aircon";
    public static final String VALID_AMENITY_CHARGER = "charger";
    public static final String VALID_AMENITY_FOOD = "food";

    public static final String NAME_DESC_FRONTIER = " " + PREFIX_NAME + VALID_NAME_FRONTIER;
    public static final String NAME_DESC_DECK = " " + PREFIX_NAME + VALID_NAME_DECK;
    public static final String RATING_DESC_FRONTIER = " " + PREFIX_RATING + VALID_RATING_FRONTIER;
    public static final String RATING_DESC_DECK = " " + PREFIX_RATING + VALID_RATING_DECK;
    public static final String OPERATING_HOURS_DESC_FRONTIER = " " + PREFIX_OPERATING_HOURS
            + VALID_OPERATING_HOURS_FRONTIER;
    public static final String OPERATING_HOURS_DESC_DECK = " " + PREFIX_OPERATING_HOURS + VALID_OPERATING_HOURS_DECK;
    public static final String ADDRESS_DESC_FRONTIER = " " + PREFIX_ADDRESS + VALID_ADDRESS_FRONTIER;
    public static final String ADDRESS_DESC_DECK = " " + PREFIX_ADDRESS + VALID_ADDRESS_DECK;
    public static final String TAG_DESC_CROWDED = " " + PREFIX_TAG + VALID_TAG_CROWDED;
    public static final String TAG_DESC_REMOVE_CROWDED = " " + PREFIX_REMOVE_TAG + VALID_TAG_CROWDED;
    public static final String TAG_DESC_QUIET = " " + PREFIX_TAG + VALID_TAG_QUIET;
    public static final String TAG_DESC_REMOVE_QUIET = " " + PREFIX_REMOVE_TAG + VALID_TAG_QUIET;
    public static final String AMENITY_DESC_WIFI = " " + PREFIX_AMENITY + VALID_AMENITY_WIFI;
    public static final String AMENITY_RM_DESC_WIFI = " " + PREFIX_REMOVE_AMENITY + VALID_AMENITY_WIFI;
    public static final String AMENITY_DESC_CHARGER = " " + PREFIX_AMENITY + VALID_AMENITY_CHARGER;
    public static final String AMENITY_RM_DESC_CHARGER = " " + PREFIX_REMOVE_AMENITY + VALID_AMENITY_CHARGER;
    public static final String AMENITY_DESC_FOOD = " " + PREFIX_AMENITY + VALID_AMENITY_FOOD;
    public static final String AMENITY_RM_DESC_FOOD = " " + PREFIX_REMOVE_AMENITY + VALID_AMENITY_FOOD;
    public static final String AMENITY_DESC_AIRCON = " " + PREFIX_AMENITY + VALID_AMENITY_AIRCON;
    public static final String AMENITY_RM_DESC_AIRCON = " " + PREFIX_REMOVE_AMENITY + VALID_AMENITY_AIRCON;
    public static final String ALIAS_USER_ALIAS_LS = " " + PREFIX_ALIAS + VALID_ALIAS_LS;
    public static final String ALIAS_USER_ALIAS_PWD = " " + PREFIX_ALIAS + VALID_ALIAS_PWD;
    public static final String ALIAS_USER_COMMAND_LIST = " " + PREFIX_ALIAS_COMMAND + VALID_ALIAS_COMMAND_LIST;
    public static final String ALIAS_USER_COMMAND_EXIT = " " + PREFIX_ALIAS_COMMAND + VALID_ALIAS_COMMAND_EXIT;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "LT27&"; // '&' not allowed in names
    public static final String INVALID_RATING_DESC = " " + PREFIX_RATING + "911a"; // digits not from '1-5' not allowed
    public static final String INVALID_RATING_OUTOFRANGE_DESC = " " + PREFIX_RATING + "9";
    public static final String INVALID_OPERATING_HOURS_DESC = " " + PREFIX_OPERATING_HOURS + "9-10, 9-6";
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "bringJacket*"; // '*' not allowed in tags
    public static final String INVALID_USER_ALIAS = " " + PREFIX_ALIAS + "list"; // alias cannot be a command word
    public static final String INVALID_ALIAS_COMMAND = " " + PREFIX_ALIAS_COMMAND + "plant"; // not a command word

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditStudySpotDescriptor DESC_FRONTIER;
    public static final EditCommand.EditStudySpotDescriptor DESC_DECK;

    static {
        DESC_FRONTIER = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_FRONTIER)
                .withRating(VALID_RATING_FRONTIER)
                .withAddress(VALID_ADDRESS_FRONTIER)
                .withAddedTags(VALID_TAG_CROWDED).build();
        DESC_DECK = new EditStudySpotDescriptorBuilder().withName(VALID_NAME_DECK)
                .withRating(VALID_RATING_DECK).withOperatingHours(VALID_OPERATING_HOURS_DECK)
                .withAddress(VALID_ADDRESS_DECK)
                .withAddedTags(VALID_TAG_QUIET, VALID_TAG_CROWDED).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the study tracker, filtered studyspot list and selected study spot in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        StudyTracker expectedStudyTracker = new StudyTracker(actualModel.getStudyTracker());
        List<StudySpot> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudySpotList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedStudyTracker, actualModel.getStudyTracker());
        assertEquals(expectedFilteredList, actualModel.getFilteredStudySpotList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the study spot at the given {@code targetIndex} in the
     * {@code model}'s study tracker.
     */
    public static void showStudySpotAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudySpotList().size());

        StudySpot spot = model.getFilteredStudySpotList().get(targetIndex.getZeroBased());
        final String[] splitName = spot.getName().fullName.split("\\s+");
        model.updateFilteredStudySpotList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudySpotList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    public static void showNoStudySpot(Model model) {
        model.updateFilteredStudySpotList(p -> false);

        assertTrue(model.getFilteredStudySpotList().isEmpty());
    }
}
