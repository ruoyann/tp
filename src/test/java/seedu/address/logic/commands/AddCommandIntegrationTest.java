package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalStudySpots.getTypicalStudyTracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.testutil.StudySpotBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalStudyTracker(), new UserPrefs());
    }

    @Test
    public void execute_newStudySpot_success() {
        StudySpot validStudySpot = new StudySpotBuilder().build();

        Model expectedModel = new ModelManager(model.getStudyTracker(), new UserPrefs());
        expectedModel.addStudySpot(validStudySpot);

        assertCommandSuccess(new AddCommand(validStudySpot), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validStudySpot), expectedModel);
    }

    @Test
    public void execute_duplicateStudySpot_throwsCommandException() {
        StudySpot spotInList = model.getStudyTracker().getStudySpotList().get(0);
        assertCommandFailure(new AddCommand(spotInList), model, AddCommand.MESSAGE_DUPLICATE_STUDYSPOT);
    }

}
