package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import javafx.scene.control.TextArea;

public class CommandInfoDisplay extends UiPart<Region> {
    private static final String FXML = "CommandInfoDisplay.fxml";

    @FXML
    private TextArea commandInfo;

    public CommandInfoDisplay() {
        super(FXML);
        String DEFAULT_MESSAGE = "Click on a command to view info on the command.";
        commandInfo.setText(DEFAULT_MESSAGE);
    }

    public void setCommandInfo(String info) {
        requireNonNull(info);
        commandInfo.setText(info);
    }
}
