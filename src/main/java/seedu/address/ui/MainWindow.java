package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String CSS_PATH = "/styles/";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private StudySpotListPanel studySpotListPanel;
    private FavouritesListPanel favouritesListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private InfoDisplay infoDisplay;
    private SettingsWindow settingsWindow;

    @FXML
    private HBox commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane studySpotListPanelPlaceholder;

    @FXML
    private StackPane favouritesListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox infoDisplayPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setStylesheet("Fonts.css");
        setStylesheet("Main.css");
        setWindowDefaultSize(logic.getGuiSettings());
        setThemeFromSettings(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow(logic);
        settingsWindow = new SettingsWindow(logic);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        studySpotListPanel = new StudySpotListPanel(logic.getFilteredStudySpotList(),
                logic.getFullList().size());
        studySpotListPanelPlaceholder.getChildren().add(studySpotListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        infoDisplay = new InfoDisplay(logic.getTopFiveStudySpotList(), logic.getFullList());
        infoDisplayPlaceholder.getChildren().add(infoDisplay.getRoot());

        favouritesListPanel = new FavouritesListPanel(logic.getFavouriteStudySpotList());
        favouritesListPanelPlaceholder.getChildren().add(favouritesListPanel.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getStudyTrackerFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Sets the theme based on {@code guiSettings}.
     */
    private void setThemeFromSettings(GuiSettings guiSettings) {
        primaryStage.getScene().getStylesheets().add(guiSettings.getStyleSheetPath());
    }

    /**
     * Sets the stylesheet based on {@code file name}.
     */
    private void setStylesheet(String fileName) {
        primaryStage.getScene().getStylesheets().add(CSS_PATH + fileName);
    }

    /**
     * Updates theme to be the latest theme
     */
    private void updateTheme() {
        int numberOfStyleSheets = primaryStage.getScene().getStylesheets().size() - 1;
        primaryStage.getScene().getStylesheets().remove(numberOfStyleSheets);
        primaryStage.getScene().getStylesheets().add(logic.getGuiSettings().getStyleSheetPath());
    }

    /**
     * Opens the settings window or focuses on it if it's already opened.
     * Will always update the theme.
     */
    @FXML
    public void handleSettings() {
        if (!settingsWindow.isShowing()) {
            settingsWindow.showAndWait();
        } else {
            settingsWindow.focus();
        }
        updateTheme();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), logic.getGuiSettings().getTheme());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public StudySpotListPanel getStudySpotListPanel() {
        return studySpotListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            studySpotListPanel.updateStudySpotCountDisplay(logic.getFilteredStudySpotList().size(),
                    logic.getFullList().size());
            favouritesListPanel.updateFavouritesCountDisplay(logic.getFavouriteStudySpotList().size());
            infoDisplay.updatePieChart(logic.getTopFiveStudySpotList(), logic.getFullList());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
