package seedu.address.ui;

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

/**
 * An UI component that displays information of a {@code StudySpot}.
 */
public class StudySpotCard extends UiPart<Region> {

    public static final String SVGPATH_AIRCON_CONTENT = "M440.3 345.2l-33.8-19.5 26-7c8.2-2.2 13.1-10.7 10.9-18.9l-4-"
            + "14.9c-2.2-8.2-10.7-13.1-18.9-10.9l-70.8 19-63.9-37 63.8-36.9 70.8 19c8.2 2.2 16.7-2.7 18.9-10.9l4-14.9c"
            + "2.2-8.2-2.7-16.7-10.9-18.9l-26-7 33.8-19.5c7.4-4.3 9.9-13.7 5.7-21.1L430.4 119c-4.3-7.4-13.7-9.9-21.1-"
            + "5.7l-33.8 19.5 7-26c2.2-8.2-2.7-16.7-10.9-18.9l-14.9-4c-8.2-2.2-16.7 2.7-18.9 10.9l-19 70.8-62.8 36.2v-7"
            + "7.5l53.7-53.7c6.2-6.2 6.2-16.4 0-22.6l-11.3-11.3c-6.2-6.2-16.4-6.2-22.6 0L256 56.4V16c0-8.8-7.2-16-16-16"
            + "h-32c-8.8 0-16 7.2-16 16v40.4l-19.7-19.7c-6.2-6.2-16.4-6.2-22.6 0L138.3 48c-6.3 6.2-6.3 16.4 0 22.6l53."
            + "7 53.7v77.5l-62.8-36.2-19-70.8c-2.2-8.2-10.7-13.1-18.9-10.9l-14.9 4c-8.2 2.2-13.1 10.7-10.9 18.9l7 26-3"
            + "3.8-19.5c-7.4-4.3-16.8-1.7-21.1 5.7L2.1 145.7c-4.3 7.4-1.7 16.8 5.7 21.1l33.8 19.5-26 7c-8.3 2.2-13.2 1"
            + "0.7-11 19l4 14.9c2.2 8.2 10.7 13.1 18.9 10.9l70.8-19 63.8 36.9-63.8 36.9-70.8-19c-8.2-2.2-16.7 2.7-18."
            + "9 10.9l-4 14.9c-2.2 8.2 2.7 16.7 10.9 18.9l26 7-33.8 19.6c-7.4 4.3-9.9 13.7-5.7 21.1l15.5 26.8c4.3 7.4 1"
            + "3.7 9.9 21.1 5.7l33.8-19.5-7 26c-2.2 8.2 2.7 16.7 10.9 18.9l14.9 4c8.2 2.2 16.7-2.7 18.9-10.9l19-70.8 "
            + "62.8-36.2v77.5l-53.7 53.7c-6.3 6.2-6.3 16.4 0 22.6l11.3 11.3c6.2 6.2 16.4 6.2 22.6 0l19.7-19.7V496c0 8."
            + "8 7.2 16 16 16h32c8.8 0 16-7.2 16-16v-40.4l19.7 19.7c6.2 6.2 16.4 6.2 22.6 0l11.3-11.3c6.2-6.2 6.2-16."
            + "4 0-22.6L256 387.7v-77.5l62.8 36.2 19 70.8c2.2 8.2 10.7 13.1 18.9 10.9l14.9-4c8.2-2.2 13.1-10.7 10.9-1"
            + "8.9l-7-26 33.8 19.5c7.4 4.3 16.8 1.7 21.1-5.7l15.5-26.8c4.3-7.3 1.8-16.8-5.6-21z";
    public static final String SVGPATH_CHARGER_CONTENT = "M320,32a32,32,0,0,0-64,0v96h64Zm48,128H16A16,16,0,0,0,0,"
            + "176v32a16,16,0,0,0,16,16H32v32A160.07,160.07,0,0,0,160,412.8V512h64V412.8A160.07,160.07,0,0,0,352,"
            + "256V224h16a16,16,0,0,0,16-16V176A16,16,0,0,0,368,160ZM128,32a32,32,0,0,0-64,0v96h64Z";
    public static final String SVGPATH_FOOD_CONTENT = "M207.9 15.2c.8 4.7 16.1 94.5 16.1 128.8 0 52.3-27.8 8"
            + "9.6-68.9 104.6L168 486.7c.7 13.7-10.2 25.3-24 25.3H80c-13.7 0-24.7-11.5-24-25.3l12.9-238.1C2"
            + "7.7 233.6 0 196.2 0 144 0 109.6 15.3 19.9 16.1 15.2 19.3-5.1 61.4-5.4 64 16.3v141.2c1.3 3.4 1"
            + "5.1 3.2 16 0 1.4-25.3 7.9-139.2 8-141.8 3.3-20.8 44.7-20.8 47.9 0 .2 2.7 6.6 116.5 8 141.8.9 3.2 1"
            + "4.8 3.4 16 0V16.3c2.6-21.6 44.8-21.4 48-1.1zm119.2 285.7l-15 185.1c-1.2 14 9.9 26 23.9 26h56c1"
            + "3.3 0 24-10.7 24-24V24c0-13.2-10.7-24-24-24-82.5 0-221.4 178.5-64.9 300.9z";
    public static final String SVGPATH_HEART_CONTENT = "M462.3 62.6C407.5 15.9 326 24.3 275.7 76.2L256 "
            + "96.5l-19.7-20.3C186.1 24.3 104.5 15.9 49.7 62.6c-62.8 53.6-66.1 149.8-9.9 207.9l193.5 199.8c12.5 12.9 "
            + "32.8 12.9 45.3 0l193.5-199.8c56.3-58.1 53-154.3-9.8-207.9z";
    public static final String SVGPATH_STAR_FILLED_CONTENT = "M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 "
            + "36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 "
            + "46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 "
            + "17.8c-11.7-23.6-45.6-23.9-57.4 0z";
    public static final String SVGPATH_STAR_UNFILLED_CONTENT = "M528.1 171.5L382 150.2 316.7 "
            + "17.8c-11.7-23.6-45.6-23.9-57.4 0L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 "
            + "103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 "
            + "105.7-103c19-18.5 8.5-50.8-17.7-54.6zM388.6 312.3l23.7 138.4L288 385.4l-124.3 65.3 23.7-138.4-100.6-98 "
            + "139-20.2 62.2-126 62.2 126 139 20.2-100.6 98z";
    public static final String SVGPATH_WIFI_CONTENT =
            "M634.91 154.88C457.74-8.99 182.19-8.93 5.09 154.88c-6.66 6.16-6.79 16.59-.35 22.98l34.24 33.97c6.14 "
                    + "6.1 16.02 6.23 22.4.38 145.92-133.68 371.3-133.71 517.25 0 6.38 5.85 16.26 5.71 "
                    + "22.4-.38l34.24-33.97c6.43-6.39 6.3-16.82-.36-22.98zM320 352c-35.35 0-64 28.65-64 64s28.65 64 64 "
                    + "64 64-28.65 64-64-28.65-64-64-64zm202.67-83.59c-115.26-101.93-290.21-101.82-405.34 0-6.9 "
                    + "6.1-7.12 16.69-.57 23.15l34.44 33.99c6 5.92 15.66 6.32 22.05.8 83.95-72.57 209.74-72.41 293.49 "
                    + "0 6.39 5.52 16.05 5.13 22.05-.8l34.44-33.99c6.56-6.46 6.33-17.06-.56-23.15z";

    private static final String FXML = "StudySpotListCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on StudyTracker level 4</a>
     */

    public final StudySpot studySpot;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private HBox rating;
    @FXML
    private Label address;
    @FXML
    private Label operatingHours;
    @FXML
    private Label studiedHours;
    @FXML
    private Label hoursText;
    @FXML
    private FlowPane tags;
    @FXML
    private HBox icons;


    /**
     * Creates a {@code StudySpotCard} with the given {@code StudySpot} and index to display.
     */
    public StudySpotCard(StudySpot studySpot, int displayedIndex) {
        super(FXML);
        this.studySpot = studySpot;
        id.setText(displayedIndex + ". ");
        name.setText(studySpot.getName().fullName);
        address.setText(studySpot.getAddress().value);

        StudiedHours hours = studySpot.getStudiedHours();
        if (Integer.parseInt(hours.value) != 0) {
            studiedHours.setText(hours.value);
        } else {
            hoursText.setText("");
        }

        operatingHours.setText(studySpot.getOperatingHours().toString());
        studySpot.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(getStyledTagLabel(tag.tagName)));
        setRatingDisplay(rating, studySpot.getRating());
        setFavouriteDisplay(icons, studySpot.isFavourite());
        setAmenitiesDisplay(icons, studySpot);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudySpotCard)) {
            return false;
        }

        // state check
        StudySpotCard card = (StudySpotCard) other;
        return id.getText().equals(card.id.getText())
                && studySpot.equals(card.studySpot);
    }

    /**
     * Sets the display of amenities in a HBox with the given {@code iconContainer} and {@code studySpot}.
     */
    private void setAmenitiesDisplay(HBox iconContainer, StudySpot studySpot) {
        if (!studySpot.getAmenities().isEmpty()) {
            HBox amenitiesDisplay = new HBox();
            amenitiesDisplay.getStyleClass().add("icon_container");
            setUpdatedAmenitiesDisplay(amenitiesDisplay, studySpot);
            iconContainer.getChildren().add(amenitiesDisplay);
        }
    }

    private void setFavouriteDisplay(HBox iconContainer, boolean isFavourite) {
        if (isFavourite) {
            HBox favouriteDisplay = new HBox();
            favouriteDisplay.getStyleClass().add("icon_container");
            favouriteDisplay.getChildren().add(getFavouriteIconLabel());
            iconContainer.getChildren().add(favouriteDisplay);
        }
    }

    private void setRatingDisplay(HBox iconContainer, Rating providedRating) {
        int rating = Integer.parseInt(providedRating.value);
        HBox ratingDisplay = new HBox();
        for (int i = 0; i < 5; i++) {
            if (rating > 0) {
                ratingDisplay.getChildren().add(getStarIconLabel(true));
            } else {
                ratingDisplay.getChildren().add(getStarIconLabel(false));
            }
            rating -= 1;
        }
        ratingDisplay.getStyleClass().add("icon_container");
        iconContainer.getChildren().add(ratingDisplay);
    }

    private void setUpdatedAmenitiesDisplay(HBox amenitiesDisplay, StudySpot studySpot) {
        studySpot.getAmenities().stream()
                .sorted(Comparator.comparing(amenity -> amenity.amenityType))
                .forEach(amenity -> amenitiesDisplay.getChildren().add(getAmenityIconLabel(amenity.amenityType)));
    }

    private Label getFavouriteIconLabel() {
        Label result = new Label();
        result.getStyleClass().add("icon_label");
        result.setGraphic(getIcon(SVGPATH_HEART_CONTENT, 0.025));
        return result;
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

    private Label getStarIconLabel(boolean isFilled) {
        Label result = new Label();
        result.getStyleClass().add("icon_label");
        if (isFilled) {
            result.setGraphic(getIcon(SVGPATH_STAR_FILLED_CONTENT, 0.025));
        } else {
            result.setGraphic(getIcon(SVGPATH_STAR_UNFILLED_CONTENT, 0.025));
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
     * Generate a Label that has an accent colour based on its contents.
     */
    private Label getStyledTagLabel(String tagName) {
        Label newLabel = new Label(tagName);
        newLabel.getStyleClass().add(getAccentFromTag(tagName));
        return newLabel;
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
