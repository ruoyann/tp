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
    private static final String DEFAULT_MESSAGE = "Use the log command to start tracking hours!";
    private ObservableList<StudySpot> topFiveSpots;
    private ObservableList<PieChart.Data> pieChartData;

    @FXML
    private PieChart infoDisplayChart;

    @FXML
    private Label infoChartCaption;

    @FXML
    private Label infoChartHours;

    @FXML
    private Label infoDisplayDefaultMessage;

    /**
     * Initializes the {@code InfoDisplay}.
     */
    public InfoDisplay(ObservableList<StudySpot> topFiveSpots, ObservableList<StudySpot> fullList) {
        super(FXML);

        this.topFiveSpots = topFiveSpots;
        infoChartCaption.setVisible(false);
        infoChartCaption.getStyleClass().add("chart-line-symbol");

        // Setting Pie Chart information
        infoDisplayChart.setLegendVisible(false);
        infoDisplayChart.setLabelsVisible(false);
        infoDisplayChart.setStartAngle(90.0);
        infoDisplayChart.autosize();
        infoDisplayDefaultMessage.setText("");

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();

        if (getTotalStudiedHours(fullList) == 0) {
            infoDisplayDefaultMessage.setText(DEFAULT_MESSAGE);
        }

        initializePieChart(topFiveSpots, pieData);
        infoDisplayChart.setData(pieData);
        infoChartHours.setText(String.valueOf(getTotalStudiedHours(fullList)));
        pieData.forEach(this::addInteractivity);

        pieChartData = pieData;
    }

    /**
     * Add spots to a pie chart
     */
    private void initializePieChart(ObservableList<StudySpot> spots, ObservableList<PieChart.Data> pieData) {
        for (StudySpot s : spots) {
            String name = s.getName().fullName;
            int hours = s.getStudiedHours().getHours();
            pieData.add(new PieChart.Data(name, hours));
        }
    }

    /**
     * Updates the pie chart with new top five spots
     */
    public void updatePieChart(ObservableList<StudySpot> newTopFiveSpots, ObservableList<StudySpot> fullList) {
        int totalStudiedHours = getTotalStudiedHours(fullList);
        infoDisplayDefaultMessage.setText("");
        if (totalStudiedHours == 0) {
            infoDisplayDefaultMessage.setText(DEFAULT_MESSAGE);
        }

        if (newTopFiveSpots.isEmpty() && fullList.isEmpty() && !pieChartData.isEmpty()) {
            pieChartData.clear();
        }

        if (isSameSpotsByName(newTopFiveSpots)) {
            handleSameSpots(newTopFiveSpots);
            checkFavourites(newTopFiveSpots);
        }

        if (!isSameSpotsByName(newTopFiveSpots) && newTopFiveSpots.size() <= topFiveSpots.size()) {
            handleOvertakingSpots(newTopFiveSpots);
        }

        if (newTopFiveSpots.size() > topFiveSpots.size()) {
            handleAddingSpotIntoTopFive(newTopFiveSpots);
        }

        topFiveSpots = newTopFiveSpots;
        pieChartData.forEach(this::addInteractivity);

        //Sorts pie chart based off largest hours to smallest hours
        pieChartData.sort((spot1, spot2) -> (int) (spot2.getPieValue() - spot1.getPieValue()));
        infoChartHours.setText(String.valueOf(totalStudiedHours));
    }

    /**
     * Adds a spot into the TopFiveStudySpot.
     *
     * The updatedStudySpots should have a study spot that was not originally in topFiveSpots,
     * and also the topFiveStudySpots should not already be full.
     */
    public void handleAddingSpotIntoTopFive(ObservableList<StudySpot> updatedStudySpots) {
        assert(updatedStudySpots.size() > topFiveSpots.size()) : "Updated should be bigger than old!";

        for (StudySpot s : updatedStudySpots) {
            if (!topFiveSpots.contains(s)) {
                addNewSpotIntoPieChart(s);
            }
        }
    }

    /**
     * Handles updating the pie chart if the spots are the same
     */
    public void handleSameSpots(ObservableList<StudySpot> updatedStudySpots) {
        assert (isSameSpotsByName(updatedStudySpots)) : "Spots should be the same";
        for (StudySpot s : updatedStudySpots) {
            String name = s.getName().fullName;
            int updatedHours = s.getStudiedHours().getHours();
            for (PieChart.Data d : pieChartData) {
                if (d.getName().equals(name)) {
                    d.setPieValue(updatedHours);
                    break;
                }
            }
        }
    }

    /**
     * Checks if any of the study spots changed due to favourites
     */
    public void checkFavourites(ObservableList<StudySpot> studySpots) {
        assert(isSameSpotsByName(studySpots)) : "This method should only be called if the two lists have the "
                + "same names!";

        // Assume that since both study spots should have the same name, if a spot is not "contained" in the list,
        // that is the spot to be replaced
        boolean isFound = false;
        StudySpot newSpot = null;
        int indexToBeRemoved = 0;
        int indexOfNewSpot = 0;
        for (int i = 0; i < topFiveSpots.size(); i++) {
            StudySpot curr = topFiveSpots.get(i);
            for (int n = 0; n < studySpots.size(); n++) {
                StudySpot compare = studySpots.get(n);
                if (curr.getName().fullName.equals(compare.getName().fullName) && !curr.equals(compare)) {
                    indexToBeRemoved = i;
                    indexOfNewSpot = n;
                    newSpot = compare;
                    isFound = true;
                    break;
                }
            }
            if (isFound) {
                break;
            }
        }
        if (isFound) {
            topFiveSpots.remove(indexToBeRemoved);
            topFiveSpots.add(indexOfNewSpot, newSpot);
        }
    }

    /**
     * Handles updating the pie chart if one spot over took another
     */
    public void handleOvertakingSpots(ObservableList<StudySpot> updatedStudySpots) {
        assert (!isSameSpotsByName(updatedStudySpots)) : "Spots should not be the same";
        StudySpot spotToBeRemoved = null;
        for (StudySpot s : topFiveSpots) {
            if (!updatedStudySpots.contains(s)) {
                spotToBeRemoved = s;
                break;
            }
        }

        assert (spotToBeRemoved != null) : "At least one spot should have been removed";
        for (PieChart.Data d : pieChartData) {
            if (d.getName().equals(spotToBeRemoved.getName().fullName)) {
                pieChartData.remove(d);
                break;
            }
        }

        for (StudySpot s : updatedStudySpots) {
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
                addNewSpotIntoPieChart(s);
            }
        }
    }

    /**
     * Adds a new study spot into the pie chart
     */
    public void addNewSpotIntoPieChart(StudySpot newSpot) {
        // Method should only be called if pie chart has less than 5 elements
        assert (pieChartData.size() < 5) : "This method should only be called if pie chart is less than 5";
        int pieChartInitialSize = pieChartData.size();
        String name = newSpot.getName().fullName;
        int hours = newSpot.getStudiedHours().getHours();
        PieChart.Data data = new PieChart.Data(name, hours);

        int index = 0;

        // Ensures that the data is inserted in the right index
        for (PieChart.Data d : pieChartData) {
            if (d.getPieValue() < hours) {
                pieChartData.add(index, data);
                break;
            }
            index++;
        }

        // Was not added in during above for loop because it was smaller than all other hours
        if (pieChartInitialSize == pieChartData.size()) {
            pieChartData.add(data);
        }
    }

    /**
     * Checks if the given list is the same (same name) as the current top five spots
     */
    public boolean isSameSpotsByName(ObservableList<StudySpot> comparedList) {
        boolean result = true;

        //If the two lists are different size, it means that they dont have the same names already
        if (pieChartData.size() != comparedList.size()) {
            return false;
        }
        for (PieChart.Data d : pieChartData) {
            String name = d.getName();

            //Turns the comparedList into a stream containing the names of the StudySpots, then checks if none of the
            //names matches the name of the current data being checked
            if (comparedList.stream()
                    .map((spot) -> spot.getName().fullName)
                    .noneMatch(curr -> curr.equals(name))) {
                result = false;
                break;
            }
        }

        return result;
    }

    /**
     * Turns each of the data points in the pie chart to show data on hover.
     * There is a single {@code infoChartCaption} Label, and it appears on mousehover.
     * The data shown on this {@code infoChartCaption} is dynamically populated based on what is being hovered.
     *
     * @@author jewlsea, Zombkey
     * Reused from https://stackoverflow.com/questions/30662190/javafx-pichart-my-hover-values-blink
     */
    private void addInteractivity(PieChart.Data data) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            infoChartCaption.setMouseTransparent(true);
            infoChartCaption.setTranslateX(e.getX() + 20);
            infoChartCaption.setTranslateY(e.getY() - 20);
            infoChartCaption.setText(String.valueOf(data.getName() + "\n"
                    + data.getPieValue() + " hours"));
            infoChartCaption.setVisible(true);
        });

        data.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            infoChartCaption.setTranslateX(e.getX() + 20);
            infoChartCaption.setTranslateY(e.getY() - 20);
        });

        data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            infoChartCaption.setVisible(false);
        });
    }

    /**
     * Given a list of study spots, add up total studied hours.
     * Returns MAX int value if there is integer overflow.
     */
    private int getTotalStudiedHours(ObservableList<StudySpot> fullList) {
        long totalHours = 0;
        for (StudySpot s : fullList) {
            totalHours += s.getStudiedHours().getHours();
        }
        return totalHours > (long) Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) totalHours;
    }
}
