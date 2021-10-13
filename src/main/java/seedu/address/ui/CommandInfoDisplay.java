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
        try {
            String[] splitParameters = info.split("Parameters:");
            String[] splitExample = splitParameters[1].split("Example:");
            String shownInfo = splitParameters[0] + "\n";
            shownInfo += "Parameters:" + splitExample[0] + "\n";
            shownInfo += "Example:" + splitExample[1];
            commandInfo.setText(shownInfo);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AssertionError("Parameters missing from message usage!");
        }
    }
}
