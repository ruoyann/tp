package seedu.address.ui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;

/**
 * A ui for the info panel that is displayed on the left side of the application.
 */
public class InfoDisplay extends UiPart<Region> {
    private static final String FXML = "InfoDisplay.fxml";

    private Image iconImage = new Image(this.getClass().getResourceAsStream("/images/books.png"));

    @javafx.fxml.FXML
    private Label statusString;

    @javafx.fxml.FXML
    private ImageView appIcon;

    /**
     * Initializes the {@code InfoDisplay}.
     */
    public InfoDisplay() {
        super(FXML);
        statusString.setMinHeight(Label.USE_PREF_SIZE);
        statusString.setText("Welcome to StudyTracker!");

        appIcon.setImage(iconImage);
    }
}
