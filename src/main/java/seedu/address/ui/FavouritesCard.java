package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;

public class FavouritesCard extends UiPart<Region> {
    private static final String FXML = "FavouritesListCard.fxml";

    public final StudySpot studySpot;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label rating;
    @FXML
    private Label studiedHours;
    @FXML
    private Label hoursText;
    @FXML
    private FlowPane tags;
    @FXML
    private HBox icons;

    /**
     * Creates a {@code FavouritesCard} with the given {@code StudySpot}.
     */
    public FavouritesCard(StudySpot studySpot) {
        super(FXML);
        this.studySpot = studySpot;
        name.setText(studySpot.getName().fullName);
        rating.setText(StudySpotCard.setRatingDisplay(studySpot.getRating()));

        StudiedHours hours = studySpot.getStudiedHours();
        if (Integer.parseInt(hours.value) != 0) {
            studiedHours.setText(hours.value);
        } else {
            hoursText.setText("");
        }

        studySpot.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(StudySpotCard.getStyledTagLabel(tag.tagName)));
        StudySpotCard.setAmenitiesDisplay(icons, studySpot);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FavouritesCard)) {
            return false;
        }

        // state check
        FavouritesCard card = (FavouritesCard) other;
        return studySpot.equals(card.studySpot);
    }
}
