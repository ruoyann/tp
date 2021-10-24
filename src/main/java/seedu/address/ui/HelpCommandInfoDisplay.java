package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class HelpCommandInfoDisplay extends UiPart<Region> {
    private static final String FXML = "HelpCommandInfoDisplay.fxml";
    private static final String DEFAULT_MESSAGE = "Click on a command to view info on the command.";

    @FXML
    private TextFlow commandInfo;

    @FXML
    private VBox commandInfoContainer;

    /**
     * Creates a HelpCommandInfoDisplay with the DEFAULT_MESSAGE as the label
     */
    public HelpCommandInfoDisplay() {
        super(FXML);
        commandInfo.getStyleClass().add("command-info");
        commandInfo.getChildren().add(new Text(DEFAULT_MESSAGE));
    }

    /**
     * Sets the display to have the info of the selected command
     */
    public void setCommandInfo(String info) {
        requireNonNull(info);
        try {
            String cmdName = info.split(": ")[0];
            String cmdInfo = info.split(": ")[1].split("\nParameters")[0];
            String cmdParameters = info.split("Parameters: ")[1].split("\n")[0];
            String cmdExample = info.split("Example: ")[1];

            Text textCmdName = new Text(cmdName + "\n");
            textCmdName.getStyleClass().add("command-info__command-name");
            Text textCmdInfo = new Text(cmdInfo);
            textCmdInfo.getStyleClass().add("command-info__command-info");
            Text textCmdParameters = new Text(cmdParameters);
            textCmdParameters.getStyleClass().add("command-info__command-info");
            Text textCmdExample = new Text(cmdExample);
            textCmdExample.getStyleClass().add("command-info__command-info");
            Text textParameterHeader = new Text("\n\nParameters:\n");
            textParameterHeader.getStyleClass().add("command-info__command-header");
            Text textExampleHeader = new Text("\n\nExample:\n");
            textExampleHeader.getStyleClass().add("command-info__command-header");

            commandInfo.getChildren().clear();
            commandInfo.getChildren().addAll(textCmdName, textCmdInfo,
                    textParameterHeader, textCmdParameters,
                    textExampleHeader, textCmdExample);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AssertionError("Parameters missing from message usage!");
        }
    }
}
