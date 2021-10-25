package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.model.studyspot.StudySpot;


/**
 * A ui for the info panel that is displayed on the left side of the application.
 */
public class InfoDisplay extends UiPart<Region> {
    private static final String FXML = "InfoDisplay.fxml";
    private ObservableList<StudySpot> topFiveSpots;
    private ObservableList<PieChart.Data> pieChartData;

    @FXML
    private PieChart infoDisplayChart;

    @FXML
    private Label caption;

    @FXML
    private Label infoChartHours;

    /**
     * Initializes the {@code InfoDisplay}.
     */
    public InfoDisplay(ObservableList<StudySpot> topFiveSpots, ObservableList<StudySpot> fullList) {
        super(FXML);
        this.topFiveSpots = topFiveSpots;
        caption.setVisible(false);
        caption.getStyleClass().add("chart-line-symbol");

        // Setting Pie Chart information
        infoDisplayChart.setLegendVisible(false);
        infoDisplayChart.setLabelsVisible(false);
        infoDisplayChart.setStartAngle(90.0);
        infoDisplayChart.autosize();

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        for (StudySpot s : topFiveSpots) {
            String name = s.getName().fullName;
            int hours = s.getStudiedHours().getHours();
            pieData.add(new PieChart.Data(name, hours));
        }

        infoDisplayChart.setData(pieData);
        infoChartHours.setText(String.valueOf(getTotalStudiedHours(fullList)));
        pieData.forEach(this::addInteractivity);

        pieChartData = pieData;
    }

    /**
     * Updates the pie chart with new top five spots
     */
    public void updatePieChart(ObservableList<StudySpot> newTopFiveSpots, ObservableList<StudySpot> fullList) {
        for (StudySpot s : newTopFiveSpots) {
            String name = s.getName().fullName;
            int hours = s.getStudiedHours().getHours();

            if (topFiveSpots.contains(s)) {
                for (PieChart.Data d : pieChartData) {
                    if (d.getName().equals(name)) {
                        d.setPieValue(hours);
                        break;
                    }
                }
            } else {
                handleNewSpot(s);
            }
        }
        pieChartData.forEach(this::addInteractivity);
        infoChartHours.setText(String.valueOf(getTotalStudiedHours(fullList)));
    }

    public void handleNewSpot(StudySpot newSpot) {
        String name = newSpot.getName().fullName;
        int hours = newSpot.getStudiedHours().getHours();
        PieChart.Data data = new PieChart.Data(name, hours);
        if (pieChartData.size() < 5) {
            pieChartData.add(data);
            return;
        }

        //Last value of pieChartData should be less than new spots hours
        assert pieChartData.get(4).getPieValue() < hours;

        pieChartData.remove(4);
        int index = 0;
        for (PieChart.Data d : pieChartData) {
            if (d.getPieValue() < hours) {
                pieChartData.add(index, data);
                break;
            }
            index++;
        }

        // If still not 5, means that it was not added in the above for loop
        if (pieChartData.size() != 5) {
            pieChartData.add(data);
        }
    }

    /**
     * Turns each of the data points in the pie chart to show data on hover
     *
     * @@author jewlsea, Zombkey
     * Reused from https://stackoverflow.com/questions/30662190/javafx-pichart-my-hover-values-blink
     */
    private void addInteractivity(PieChart.Data data) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            caption.setMouseTransparent(true);
            caption.setTranslateX(e.getX() + 20);
            caption.setTranslateY(e.getY() - 20);
            caption.setText(String.valueOf(data.getName() + "\n"
                    + data.getPieValue() + " hours"));
            caption.setVisible(true);
        });

        data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            caption.setTranslateX(e.getX() + 20);
            caption.setTranslateY(e.getY() - 20);
        });

        data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            caption.setVisible(false);
        });
    }

    /**
     * Given a list of study spots, add up total studied hours
     */
    private int getTotalStudiedHours(ObservableList<StudySpot> fullList) {
        int totalHours = 0;
        for (StudySpot s : fullList) {
            totalHours += s.getStudiedHours().getHours();
        }
        return totalHours;
    }
}
