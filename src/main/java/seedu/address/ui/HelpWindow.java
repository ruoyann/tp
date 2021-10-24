package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.util.CommandList;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL =
            "https://ay2122s1-cs2103t-t09-1.github.io/tp/UserGuide.html";
    public static final String DEVELOPERGUIDE_URL =
            "https://ay2122s1-cs2103t-t09-1.github.io/tp/DeveloperGuide.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    private ObservableList<String> commandList = CommandList.COMMANDS;
    private HashMap<String, String> commandToUsage = CommandList.getCommandToUsageMapping();
    private HelpCommandInfoDisplay commandInfoDisplay;
    private Logic logic;

    @FXML
    private StackPane commandInfoDisplayPlaceholder;

    @FXML
    private ListView<String> commandListView;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        commandListView.setItems(commandList);
        commandListView.setOnMouseClicked(new HandleListView());

        this.commandInfoDisplay = new HelpCommandInfoDisplay();
        commandInfoDisplayPlaceholder.getChildren().add(commandInfoDisplay.getRoot());
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow(Logic logic) {
        this(new Stage());
        this.logic = logic;

        this.getRoot().getScene().getStylesheets().add("/styles/Fonts.css");
        this.getRoot().getScene().getStylesheets().add("/styles/Main.css");
        this.getRoot().getScene().getStylesheets().add(logic.getGuiSettings().getStyleSheetPath());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        int numberOfStyleSheets = getRoot().getScene().getStylesheets().size() - 1;
        logger.fine("Showing help page about the application.");

        getRoot().getScene().getStylesheets().remove(numberOfStyleSheets);
        getRoot().getScene().getStylesheets().add(logic.getGuiSettings().getStyleSheetPath());
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    private void openUserGuide() {
        openWebpage(USERGUIDE_URL);
    }

    @FXML
    private void openDeveloperGuide() {
        openWebpage(DEVELOPERGUIDE_URL);
    }

    /**
     * Opens the URL in a web browser
     */
    private void openWebpage(String urlToOpen) {
        try {
            Desktop.getDesktop().browse(new URL(urlToOpen).toURI());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private class HandleListView implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            String clickedCommand = commandListView.getSelectionModel().getSelectedItem().toLowerCase();
            String commandFormat = commandToUsage.get(clickedCommand);
            commandInfoDisplay.setCommandInfo(commandFormat);
        }
    }
}
