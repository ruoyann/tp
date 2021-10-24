package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.model.studyspot.StudySpot;


/**
 * A ui for the info panel that is displayed on the left side of the application.
 */
public class InfoDisplay extends UiPart<Region> {
    private static final String FXML = "InfoDisplay.fxml";
    private static final String TOTAL_STUDIED_HOURS = "Total Studied Hours: %1$S";

    @FXML
    private Label statusString;

    @FXML
    private ImageView appIcon;

    @FXML
    private PieChart infoDisplayChart;

    /**
     * Initializes the {@code InfoDisplay}.
     */
    public InfoDisplay(ObservableList<StudySpot> topFiveSpots, ObservableList<StudySpot> fullList) {
        super(FXML);
        statusString.setMinHeight(Label.USE_PREF_SIZE);
        statusString.setText("Welcome to StudyTracker!");

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (StudySpot s : topFiveSpots) {
            String name = s.getName().fullName;
            int hours = s.getStudiedHours().getHours();
            pieData.add(new PieChart.Data(name, hours));
        }

        infoDisplayChart.setData(pieData);
        infoDisplayChart.autosize();
        infoDisplayChart.setLabelsVisible(false);
        infoDisplayChart.setTitle(String.format(TOTAL_STUDIED_HOURS, getTotalStudiedHours(fullList)));
    }

    private int getTotalStudiedHours(ObservableList<StudySpot> fullList) {
        int totalHours = 0;
        for (StudySpot s : fullList) {
            totalHours += s.getStudiedHours().getHours();
        }
        return totalHours;
    }

    /**
     * Updates the pie chart with new top five spots
     */
    public void updatePieChart(ObservableList<StudySpot> newTopFiveSpots, ObservableList<StudySpot> fullList) {
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (StudySpot s : newTopFiveSpots) {
            String name = s.getName().fullName;
            int hours = s.getStudiedHours().getHours();
            pieData.add(new PieChart.Data(name, hours));
        }

        infoDisplayChart.setData(pieData);
        infoDisplayChart.setTitle(String.format(TOTAL_STUDIED_HOURS, getTotalStudiedHours(fullList)));
    }
}
