package seedu.address.ui;

import static seedu.address.ui.StudySpotCard.SVGPATH_AIRCON_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_CHARGER_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_FOOD_CONTENT;
import static seedu.address.ui.StudySpotCard.SVGPATH_WIFI_CONTENT;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;
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
    private Label studiedHours;
    @FXML
    private Label hoursText;
    @FXML
    private FlowPane tags;
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
                .forEach(tag -> tags.getChildren().add(getStyledTagLabel(tag.tagName)));

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

    /**
     * Generate a Label that has an accent colour based on its contents.
     */
    private Label getStyledTagLabel(String tagName) {
        Label newLabel = new Label(tagName);
        newLabel.getStyleClass().add(getAccentFromTag(tagName));
        return newLabel;
    }

    private void setUpdatedAmenitiesDisplay(HBox iconContainer, StudySpot studySpot) {
        HBox amenitiesDisplay = new HBox();
        amenitiesDisplay.getStyleClass().add("icon_container");

        studySpot.getAmenities().stream()
                .sorted(Comparator.comparing(amenity -> amenity.amenityType))
                .forEach(amenity -> amenitiesDisplay.getChildren().add(getAmenityIconLabel(amenity.amenityType)));
        iconContainer.getChildren().add(amenitiesDisplay);
    }

    private Label getAmenityIconLabel(String amenityType) {
        Label result = new Label();
        result.getStyleClass().add("icon_label");

        switch (amenityType) {
        case "wifi":
            result.setGraphic(getIcon(SVGPATH_WIFI_CONTENT, 0.03));
            return result;
        case "charger":
            result.setGraphic(getIcon(SVGPATH_CHARGER_CONTENT, 0.03));
            return result;
        case "food":
            result.setGraphic(getIcon(SVGPATH_FOOD_CONTENT, 0.03));
            return result;
        case "aircon":
            result.setGraphic(getIcon(SVGPATH_AIRCON_CONTENT, 0.03));
            return result;
        default:
            throw new AssertionError("Amenity [\" + amenityType + \"] not found in StudySpotCard!");
        }
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
