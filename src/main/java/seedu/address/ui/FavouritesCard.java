package seedu.address.ui;

import static seedu.address.ui.StudySpotCard.SVGPATH_AIRCON_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_CHARGER_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_FOOD_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_HEART_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_STAR_FILLED_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_STAR_UNFILLED_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_WIFI_CONTENT;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;
import seedu.address.model.studyspot.Rating;
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
    private HBox rating;
    @FXML
    private Label studiedHours;
    @FXML
    private Label hoursText;
    @FXML
    private FlowPane tagsForFavourites;
    @FXML
    private HBox amenitiesDisplay;

    /**
     * Creates a {@code FavouritesCard} with the given {@code StudySpot}.
     */
    public FavouritesCard(StudySpot studySpot) {
        super(FXML);
        this.studySpot = studySpot;
        name.setText(studySpot.getName().fullName);

        StudiedHours hours = studySpot.getStudiedHours();
        if (Integer.parseInt(hours.value) != 0) {
            studiedHours.setText(hours.value);
        } else {
            hoursText.setText("");
        }

        studySpot.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tagsForFavourites.getChildren().add(getStyledTagLabel(tag.tagName)));
        setRatingDisplay(rating, studySpot.getRating());
        setAmenitiesDisplay(amenitiesDisplay, studySpot);
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

    /**
     * Sets the display of amenities in a HBox with the given {@code iconsDisplay} and {@code studySpot}.
     */
    private void setAmenitiesDisplay(HBox amenitiesDisplay, StudySpot studySpot) {
        if (!studySpot.getAmenities().isEmpty()) {
            setUpdatedAmenitiesDisplay(amenitiesDisplay, studySpot);
        }
    }

    private void setRatingDisplay(HBox ratingDisplay, Rating providedRating) {
        int rating = Integer.parseInt(providedRating.value);
        for (int i = 0; i < 5; i++) {
            if (rating > 0) {
                ratingDisplay.getChildren().add(getStarIconLabel(true));
            } else {
                ratingDisplay.getChildren().add(getStarIconLabel(false));
            }
            rating -= 1;
        }
    }

    /**
     * Generate a Label that has an accent colour based on its contents.
     */
    private Label getStyledTagLabel(String tagName) {
        Label newLabel = new Label(tagName);
        newLabel.getStyleClass().add(getAccentFromTag(tagName));
        return newLabel;
    }

    private void setUpdatedAmenitiesDisplay(HBox amenitiesDisplay, StudySpot studySpot) {
        studySpot.getAmenities().stream()
                .sorted(Comparator.comparing(amenity -> amenity.amenityType))
                .forEach(amenity -> amenitiesDisplay.getChildren().add(getAmenityIconLabel(amenity.amenityType)));
    }

    private Label getAmenityIconLabel(String amenityType) {
        Label result = new Label();
        result.getStyleClass().add("icon_small_label");

        switch (amenityType) {
        case "wifi":
            result.setGraphic(getIcon(SVGPATH_WIFI_CONTENT, 0.02));
            return result;
        case "charger":
            result.setGraphic(getIcon(SVGPATH_CHARGER_CONTENT, 0.02));
            return result;
        case "food":
            result.setGraphic(getIcon(SVGPATH_FOOD_CONTENT, 0.02));
            return result;
        case "aircon":
            result.setGraphic(getIcon(SVGPATH_AIRCON_CONTENT, 0.02));
            return result;
        default:
            throw new AssertionError("Amenity [\" + amenityType + \"] not found in StudySpotCard!");
        }
    }

    private Label getStarIconLabel(boolean isFilled) {
        Label result = new Label();
        result.getStyleClass().add("icon_small_label");
        if (isFilled) {
            result.setGraphic(getIcon(SVGPATH_STAR_FILLED_CONTENT, 0.015));
        } else {
            result.setGraphic(getIcon(SVGPATH_STAR_UNFILLED_CONTENT, 0.015));
        }
        return result;
    }

    private SVGPath getIcon(String svgPathContent, double scale) {
        SVGPath icon = new SVGPath();
        icon.setScaleX(scale);
        icon.setScaleY(scale);
        icon.getStyleClass().add("svg_icon");
        icon.setContent(svgPathContent);
        return icon;
    }

    /**
     * Given a String tagName, hash it to return an accent colour from 0-5.
     * Used in some themes.
     */
    private String getAccentFromTag(String tagName) {
        int accent = Math.abs(tagName.hashCode() % 5);
        return "tag-accent-" + accent;
    }
}
