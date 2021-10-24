package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

/**
 * Controller for a settings page
 */
public class SettingsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(SettingsWindow.class);
    private static final String FXML = "SettingsWindow.fxml";

    private Logic logic;

    @FXML
    private ChoiceBox<String> themeChoiceBox;

    /**
     * Creates a new SettingsWindow.
     *
     * @param root Stage to use as the root of the SettingsWindow.
     */
    public SettingsWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new SettingsWindow with a Logic instance.
     */
    public SettingsWindow(Logic logic) {
        this(new Stage());
        this.logic = logic;

        this.getRoot().getScene().getStylesheets().add("/styles/Fonts.css");
        this.getRoot().getScene().getStylesheets().add("/styles/Main.css");
        this.getRoot().getScene().getStylesheets().add(logic.getGuiSettings().getStyleSheetPath());

        themeChoiceBox.getItems().addAll("Ab3", "Default", "Dotsdark", "Dotslight", "Milkshake");
        themeChoiceBox.setValue(this.getCurrentTheme());
    }

    /**
     * Shows the settings window.
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
    public void showAndWait() {
        int numberOfStyleSheets = getRoot().getScene().getStylesheets().size() - 1;

        logger.fine("Showing settings page of StudyTracker.");
        getRoot().getScene().getStylesheets().remove(numberOfStyleSheets);
        getRoot().getScene().getStylesheets().add(logic.getGuiSettings().getStyleSheetPath());
        getRoot().showAndWait();
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


    /**
     * Save the Settings.
     */
    @FXML
    private void handleSettingsSave() {
        String userChoice = themeChoiceBox.getValue().toLowerCase();
        GuiSettings current = logic.getGuiSettings();
        logic.setGuiSettings(new GuiSettings(
                current.getWindowWidth(),
                current.getWindowHeight(),
                current.getWindowCoordinates().x,
                current.getWindowCoordinates().y,
                userChoice
        ));
        this.hide();
    }

    /**
     * Close the Settings Window.
     */
    @FXML
    private void handleSettingsCancel() {
        this.hide();
    }

    private String getCurrentTheme() {
        return logic.getGuiSettings().getTheme().substring(0, 1).toUpperCase()
                + logic.getGuiSettings().getTheme().substring(1);
    }
}
