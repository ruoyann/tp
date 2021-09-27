package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.StudyTracker;
import seedu.address.model.studyspot.StudySpot;

/**
 * A utility class containing a list of {@code StudySpot} objects to be used in tests.
 */
public class TypicalStudySpots {

    public static final StudySpot STARBUCKS = new StudySpotBuilder().withName("Starbucks")
            .withAddress("UTown").withEmail("-")
            .withRating("4")
            .withTags("coffee").build();
    public static final StudySpot CENTRAL_LIBRARY = new StudySpotBuilder().withName("Central library")
            .withAddress("NUS, Central Library")
            .withEmail("-").withRating("3")
            .withTags("cold", "quiet").build();
    public static final StudySpot COM1 = new StudySpotBuilder().withName("COM1").withRating("2")
            .withEmail("-").withAddress("NUS").build();
    public static final StudySpot DANIEL = new StudySpotBuilder().withName("Daniel Meier").withRating("3")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final StudySpot ELLE = new StudySpotBuilder().withName("Elle Meyer").withRating("4")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final StudySpot FIONA = new StudySpotBuilder().withName("Fiona Kunz").withRating("5")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final StudySpot GEORGE = new StudySpotBuilder().withName("George Best").withRating("3")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final StudySpot HOON = new StudySpotBuilder().withName("Hoon Meier").withRating("1")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final StudySpot IDA = new StudySpotBuilder().withName("Ida Mueller").withRating("1")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - StudySpot's details found in {@code CommandTestUtil}
    public static final StudySpot AMY = new StudySpotBuilder().withName(VALID_NAME_AMY).withRating(VALID_RATING_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final StudySpot BOB = new StudySpotBuilder().withName(VALID_NAME_BOB).withRating(VALID_RATING_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();


    private TypicalStudySpots() {} // prevents instantiation

    /**
     * Returns an {@code StudyTracker} with all the typical study spots.
     */
    public static StudyTracker getTypicalAddressBook() {
        StudyTracker ab = new StudyTracker();
        for (StudySpot spot : getTypicalStudySpots()) {
            ab.addStudySpot(spot);
        }
        return ab;
    }

    public static List<StudySpot> getTypicalStudySpots() {
        return new ArrayList<>(Arrays.asList(STARBUCKS, CENTRAL_LIBRARY, COM1, AMY, DANIEL, ELLE, FIONA));
    }
}
