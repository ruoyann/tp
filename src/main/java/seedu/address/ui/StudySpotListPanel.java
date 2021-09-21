package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.studyspot.StudySpot;

/**
 * Panel containing the list of persons.
 */
public class StudySpotListPanel extends UiPart<Region> {
    private static final String FXML = "StudySpotListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(StudySpotListPanel.class);

    @FXML
    private ListView<StudySpot> personListView;

    /**
     * Creates a {@code StudySpotListPanel} with the given {@code ObservableList}.
     */
    public StudySpotListPanel(ObservableList<StudySpot> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new StudySpotListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StudySpot} using a {@code StudySpotCard}.
     */
    class StudySpotListViewCell extends ListCell<StudySpot> {
        @Override
        protected void updateItem(StudySpot person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new StudySpotCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
