package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;


public class HelpCommandInfoDisplay extends UiPart<Region> {
    private static final String FXML = "HelpCommandInfoDisplay.fxml";
    private static final String DEFAULT_MESSAGE = "Click on a command to view info on the command.";

    @FXML
    private TextArea commandInfo;

    /**
     * Creates a CommandInfoDisplay with the DEFAULT_MESSAGE as the label
     */
    public HelpCommandInfoDisplay() {
        super(FXML);
        commandInfo.setText(DEFAULT_MESSAGE);
    }

    /**
     * Sets the display to have the info of the selected command
     */
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
