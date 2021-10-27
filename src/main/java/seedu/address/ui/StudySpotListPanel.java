package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.studyspot.StudySpot;

/**
 * Panel containing the list of study spots.
 */
public class StudySpotListPanel extends UiPart<Region> {
    private static final String FXML = "StudySpotListPanel.fxml";
    private static final String DEFAULT_MESSAGE = "Add a study spot here to get started!";
    private final Logger logger = LogsCenter.getLogger(StudySpotListPanel.class);

    @FXML
    private ListView<StudySpot> studySpotListView;

    @FXML
    private Label studySpotListCount;

    @FXML
    private Label studySpotListViewDefaultMessage;

    /**
     * Creates a {@code StudySpotListPanel} with the given {@code ObservableList} and {@code totalStudySpots}.
     */
    public StudySpotListPanel(ObservableList<StudySpot> filteredStudySpots, int totalStudySpots) {
        super(FXML);
        studySpotListView.setItems(filteredStudySpots);
        studySpotListView.setCellFactory(listView -> new StudySpotListViewCell());
        getInitialStudySpotCountDisplay(totalStudySpots);
    }

    /**
     * Updates {@code studySpotListCount} with the given {@code filteredStudySpots} and {@code totalStudySpots}.
     */
    public void updateStudySpotCountDisplay(int filteredStudySpots, int totalStudySpots) {
        if (totalStudySpots == 0) {
            studySpotListCount.setText("0");
            studySpotListViewDefaultMessage.setText(DEFAULT_MESSAGE);
        } else {
            studySpotListCount.setText(filteredStudySpots + "/" + totalStudySpots);
            studySpotListViewDefaultMessage.setText("");
        }
    }

    private void getInitialStudySpotCountDisplay(int studySpotCount) {
        updateStudySpotCountDisplay(studySpotCount, studySpotCount);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StudySpot} using a {@code StudySpotCard}.
     */
    class StudySpotListViewCell extends ListCell<StudySpot> {
        @Override
        protected void updateItem(StudySpot spot, boolean empty) {
            super.updateItem(spot, empty);

            if (empty || spot == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudySpotCard(spot, getIndex() + 1).getRoot());
            }
        }
    }

}
