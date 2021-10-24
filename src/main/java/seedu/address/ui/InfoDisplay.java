package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.studyspot.StudySpot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A ui for the info panel that is displayed on the left side of the application.
 */
public class InfoDisplay extends UiPart<Region> {
    private static final String FXML = "InfoDisplay.fxml";

    @FXML
    private PieChart infoDisplayChart;

    @FXML
    private Label caption;

    /**
     * Initializes the {@code InfoDisplay}.
     */
    public InfoDisplay() {
        super(FXML);

        caption.setVisible(false);
        caption.getStyleClass().add("chart-line-symbol");

        // Setting Pie Chart information
        infoDisplayChart.setLegendVisible(false);
        infoDisplayChart.setLabelsVisible(false);
        infoDisplayChart.setStartAngle(90.0);
        updatePieChartInfo(topFiveSpots);
    }

    public void updatePieChartInfo(ObservableList<StudySpot> topFiveSpots) {
        HashMap<String, Integer> nameHourMap = new HashMap<>();
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for (StudySpot s : topFiveSpots) {
            nameHourMap.put(s.getName().fullName, s.getStudiedHours().getHours());
        }

        for (String name : nameHourMap.keySet()) {
            PieChart.Data datapoint = new PieChart.Data(name, nameHourMap.get(name));
            pieData.add(datapoint);
        }

        infoDisplayChart.setData(pieData);

        infoDisplayChart.getData().stream()
                .forEach(this::addInteractivity);
    }


    /**
     * reuse https://stackoverflow.com/questions/30662190/javafx-pichart-my-hover-values-blink
     */
    private void addInteractivity(PieChart.Data data) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            caption.setMouseTransparent(true);
            caption.setTranslateX(e.getX() + 20);
            caption.setTranslateY(e.getY() - 20);
            caption.setText(String.valueOf(data.getName() + "\n"
                    + data.getPieValue() + " hours"));
            if (caption.getStyleClass().size() == 4) {
                caption.getStyleClass().remove(3);
            }
            caption.getStyleClass().add(data.getNode().getStyleClass().get(2));
            caption.setVisible(true);
        });

        data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, e-> {
            caption.setTranslateX(e.getX() + 20);
            caption.setTranslateY(e.getY() - 20);
        });

        data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            caption.setVisible(false);
        });
    }
}
