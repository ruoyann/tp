package seedu.address.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;

/**
 * An UI component that displays information of a {@code StudySpot}.
 */
public class StudySpotCard extends UiPart<Region> {

    private static final String FXML = "StudySpotListCard.fxml";

    private static final String SVGPATH_WIFI_CONTENT =
            "M634.91 154.88C457.74-8.99 182.19-8.93 5.09 154.88c-6.66 6.16-6.79 16.59-.35 22.98l34.24 33.97c6.14 "
                    + "6.1 16.02 6.23 22.4.38 145.92-133.68 371.3-133.71 517.25 0 6.38 5.85 16.26 5.71 "
                    + "22.4-.38l34.24-33.97c6.43-6.39 6.3-16.82-.36-22.98zM320 352c-35.35 0-64 28.65-64 64s28.65 64 64 "
                    + "64 64-28.65 64-64-28.65-64-64-64zm202.67-83.59c-115.26-101.93-290.21-101.82-405.34 0-6.9 "
                    + "6.1-7.12 16.69-.57 23.15l34.44 33.99c6 5.92 15.66 6.32 22.05.8 83.95-72.57 209.74-72.41 293.49 "
                    + "0 6.39 5.52 16.05 5.13 22.05-.8l34.44-33.99c6.56-6.46 6.33-17.06-.56-23.15z";
    private static final String SVGPATH_CHARGER_CONTENT = "M320,32a32,32,0,0,0-64,0v96h64Zm48,128H16A16,16,0,0,0,0,"
            + "176v32a16,16,0,0,0,16,16H32v32A160.07,160.07,0,0,0,160,412.8V512h64V412.8A160.07,160.07,0,0,0,352,"
            + "256V224h16a16,16,0,0,0,16-16V176A16,16,0,0,0,368,160ZM128,32a32,32,0,0,0-64,0v96h64Z";
    private static final String SVGPATH_FOOD_CONTENT = "M480.1 31.9c-55-55.1-164.9-34.5-227.8 28.5-49.3 49.3-5"
            + "5.1 110-28.8 160.4L9 413.2c-11.6 10.5-12.1 28.5-1 39.5L59.3 504c11 11 29.1 10.5 39.5-1.1l192.4-2"
            + "14.4c50.4 26.3 111.1 20.5 160.4-28.8 63-62.9 83.6-172.8 28.5-227.8z";

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
    private Label favourite;
    @FXML
    private Label rating;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private HBox amenities;

    /**
     * Creates a {@code StudySpotCode} with the given {@code StudySpot} and index to display.
     */
    public StudySpotCard(StudySpot studySpot, int displayedIndex) {
        super(FXML);
        this.studySpot = studySpot;
        id.setText(displayedIndex + ". ");
        name.setText(studySpot.getName().fullName);
        rating.setText(setRatingDisplay(studySpot.getRating()));
        favourite.setVisible(studySpot.isFavourite());
        rating.setText(setRatingDisplay(studySpot.getRating()));
        address.setText(studySpot.getAddress().value);
        email.setText(studySpot.getEmail().value);
        studySpot.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        setAmenitiesDisplay(amenities, studySpot);
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

    private void setAmenitiesDisplay(HBox amenitiesDisplay, StudySpot studySpot) {
        if (studySpot.getAmenities().isEmpty()) {
            setDefaultAmenitiesDisplay(amenitiesDisplay);
        } else {
            setUpdatedAmenitiesDisplay(amenitiesDisplay, studySpot);
        }
    }

    private void setDefaultAmenitiesDisplay(HBox amenitiesDisplay) {
        Arrays.stream(Amenity.VALID_TYPES)
                .sorted()
                .forEach(amenityType -> amenitiesDisplay.getChildren()
                        .add(getAmenityIconLabel(amenityType, false)));
    }

    private void setUpdatedAmenitiesDisplay(HBox amenitiesDisplay, StudySpot studySpot) {
        Set<String> amenitiesPresent = studySpot.getAmenities().stream()
                        .map(amenity -> amenity.amenityType).sorted().collect(Collectors.toSet());
        Arrays.stream(Amenity.VALID_TYPES)
                .sorted()
                .forEach(amenityType -> {
                    if (amenitiesPresent.contains(amenityType)) {
                        amenitiesDisplay.getChildren().add(getAmenityIconLabel(amenityType, true));
                    } else {
                        amenitiesDisplay.getChildren().add(getAmenityIconLabel(amenityType, false));
                    }
                });
    }

    private Label getAmenityIconLabel(String amenityType, boolean isActive) {
        Label result = new Label();

        SVGPath icon = new SVGPath();
        icon.setScaleX(0.03);
        icon.setScaleY(0.03);
        icon.getStyleClass().add("svg_icon");
        if (!isActive) {
            icon.getStyleClass().add("cell_muted_label");
        }

        switch (amenityType) {
        case "wifi":
            icon.setContent(SVGPATH_WIFI_CONTENT);
            result.setGraphic(icon);
            return result;
        case "charger":
            icon.setContent(SVGPATH_CHARGER_CONTENT);
            result.setGraphic(icon);
            return result;
        case "food":
            icon.setContent(SVGPATH_FOOD_CONTENT);
            result.setGraphic(icon);
            return result;
        default:
            throw new AssertionError("error occured");
        }
    }

    private String setRatingDisplay(Rating providedRating) {
        int rating = Integer.parseInt(providedRating.value);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            result.append(rating > 0 ? "★" : "☆");
            rating -= 1;
        }
        return result.toString();
    }
}
