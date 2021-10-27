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

public class FavouritesListPanel extends UiPart<Region> {
    private static final String FXML = "FavouritesListPanel.fxml";
    private static final String DEFAULT_MESSAGE = "Add a favourite study spot!";
    private final Logger logger = LogsCenter.getLogger(FavouritesListPanel.class);

    @javafx.fxml.FXML
    private ListView<StudySpot> favouritesListView;

    @FXML
    private Label favouritesListCount;

    @FXML
    private Label favouritesListViewDefaultMessage;

    /**
     * Creates a {@code FavouritesListPanel} with the given {@code ObservableList} and {@code favouritesCount}.
     */
    public FavouritesListPanel(ObservableList<StudySpot> filteredStudySpots) {
        super(FXML);
        favouritesListView.setItems(filteredStudySpots);
        favouritesListView.setCellFactory(listView -> new FavouritesListViewCell());
        getInitialFavouritesCountDisplay(filteredStudySpots.size());
    }

    /**
     * Updates {@code favouritesListCount} with the given {@code favouritesCount}.
     */
    public void updateFavouritesCountDisplay(int favouritesCount) {
        if (favouritesCount == 0) {
            favouritesListCount.setText("0");
            favouritesListViewDefaultMessage.setText(DEFAULT_MESSAGE);
        } else {
            favouritesListCount.setText(String.valueOf(favouritesCount));
            favouritesListViewDefaultMessage.setText("");
        }
    }

    private void getInitialFavouritesCountDisplay(int favouritesCount) {
        updateFavouritesCountDisplay(favouritesCount);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code StudySpot} using a {@code FavouritesCard}.
     */
    class FavouritesListViewCell extends ListCell<StudySpot> {
        @Override
        protected void updateItem(StudySpot spot, boolean empty) {
            super.updateItem(spot, empty);

            if (empty || spot == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FavouritesCard(spot).getRoot());
            }
        }
    }
}
