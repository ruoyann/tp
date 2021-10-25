package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.StudyTrackerParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final StudyTrackerParser studyTrackerParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        studyTrackerParser = new StudyTrackerParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = studyTrackerParser.parseCommand(commandText, model.getUserPrefs().getUserAliases());
        commandResult = command.execute(model);

        try {
            storage.saveStudyTracker(model.getStudyTracker());
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyStudyTracker getStudyTracker() {
        return model.getStudyTracker();
    }

    @Override
    public ObservableList<StudySpot> getFilteredStudySpotList() {
        return model.getFilteredStudySpotList();
    }

    @Override
    public ObservableList<StudySpot> getFavouriteStudySpotList() {
        return model.getFavouriteStudySpotList();
    }

    @Override
    public ObservableList<StudySpot> getFullList() {
        return model.getFullList();
    }

    @Override
    public Path getStudyTrackerFilePath() {
        return model.getStudyTrackerFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableList<StudySpot> getTopFiveStudySpotList() {
        return model.getTopFiveStudySpotList();
    }
}
