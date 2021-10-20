package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private List<String> commandHistory = new ArrayList<>();
    private ListIterator<String> scroller;
    private int lastCommandIndex = -1;

    private final CommandExecutor commandExecutor;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        handleKeyboardShortcuts();
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            scroller = null;
            commandHistory.add(commandText);
            commandExecutor.execute(commandText);
            commandTextField.setText("");
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Handles keyboard shortcuts in the Input box.
     * Shortcuts: Up arrow toggles between last-entered commands.
     *
     * @@author qreoct-reused
     * Reused from https://github.com/qreoct/ip/blob/master/src/main/java/duke/controllers/AppWindow.java
     */
    private void handleKeyboardShortcuts() {
        commandTextField.setOnKeyReleased(event -> {
            String key = event.getCode().toString();
            cycleThroughCommandHistory(key);
        });
    }

    /**
     * Cycles through the history of commands.
     *
     * @param key To check if the key pressed is UP or DOWN
     * Adapted from https://stackoverflow.com/questions/41604430/implement-command-history-within-java-program
     */
    private void cycleThroughCommandHistory(String key) {
        boolean isUpArrow = key.equals("UP");
        boolean isDownArrow = key.equals("DOWN");
        if (scroller == null || commandTextField.getText().equals("")) {
            lastCommandIndex = commandHistory.size();
            scroller = commandHistory.listIterator(lastCommandIndex);
        }
        if (scroller.hasPrevious() && isUpArrow) {
            scrollUp();
        }
        if (scroller.hasNext() && isDownArrow) {
            scrollDown();
        }
    }

    private void scrollUp() {
        if (scroller.hasPrevious()) {
            int prevIndex = scroller.previousIndex();
            String prevCommand = scroller.previous();
            if (scroller.hasPrevious() && prevIndex == lastCommandIndex) {
                prevIndex = scroller.previousIndex();
                prevCommand = scroller.previous();
            }
            lastCommandIndex = prevIndex;
            commandTextField.setText(prevCommand);
            commandTextField.end();
            if (!scroller.hasPrevious()) {
                scroller.next();
            }
        }
    }

    private void scrollDown() {
        if (scroller.hasNext()) {
            int nextIndex = scroller.nextIndex();
            String nextCommand = scroller.next();
            if (scroller.hasNext() && nextIndex == lastCommandIndex) {
                nextIndex = scroller.nextIndex();
                nextCommand = scroller.next();
            }
            lastCommandIndex = nextIndex;
            commandTextField.setText(nextCommand);
            commandTextField.end();
            if (!scroller.hasNext()) {
                scroller.previous();
            }
        }
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
