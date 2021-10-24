package seedu.address.ui;

import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import seedu.address.model.studyspot.StudySpot;


/**
 * A ui for the info panel that is displayed on the left side of the application.
 */
public class InfoDisplay extends UiPart<Region> {
    private static final String FXML = "InfoDisplay.fxml";

    private Image iconImage = new Image(this.getClass().getResourceAsStream("/images/books.png"));

    @FXML
    private Label statusString;

    @FXML
    private ImageView appIcon;

    @FXML
    private PieChart infoDisplayChart;

    /**
     * Initializes the {@code InfoDisplay}.
     */
    public InfoDisplay(ObservableList<StudySpot> topFiveSpots) {
        super(FXML);
        statusString.setMinHeight(Label.USE_PREF_SIZE);
        statusString.setText("Welcome to StudyTracker!");

        // Setting Pie Chart information
        HashMap<String, Integer> nameHourMap = new HashMap<>();

        for (StudySpot s: topFiveSpots) {
            nameHourMap.put(s.getName().fullName, s.getStudiedHours().getHours());
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        for (String name: nameHourMap.keySet()) {
            System.out.println(nameHourMap.get(name));
            System.out.println(name);
            pieData.add(new PieChart.Data(name, nameHourMap.get(name)));
        }

        infoDisplayChart.setData(pieData);
        infoDisplayChart.autosize();
        infoDisplayChart.setLabelsVisible(false);
    }
}
