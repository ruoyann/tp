package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.studyspot.StudySpot;

/**
 * A ui for the info panel that is displayed on the left side of the application.
 */
public class InfoDisplay extends UiPart<Region> {
    private static final String FXML = "InfoDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(InfoDisplay.class);

    private Image iconImage = new Image(this.getClass().getResourceAsStream("/images/books.png"));

    private FavouritesListPanel favouritesListPanel;

    @javafx.fxml.FXML
    private Label statusString;

    @javafx.fxml.FXML
    private ImageView appIcon;

    @javafx.fxml.FXML
    private StackPane favouritesListPanelPlaceholder;

    /**
     * Initializes the {@code InfoDisplay} with the given {@code favouriteStudySpots}.
     */
    public InfoDisplay(ObservableList<StudySpot> favouriteStudySpots) {
        super(FXML);
        statusString.setMinHeight(Label.USE_PREF_SIZE);
        statusString.setText("Welcome to StudyTracker!");

        appIcon.setImage(iconImage);

        favouritesListPanel = new FavouritesListPanel(favouriteStudySpots, favouriteStudySpots.size());
        favouritesListPanelPlaceholder.getChildren().add(favouritesListPanel.getRoot());
    }
}
