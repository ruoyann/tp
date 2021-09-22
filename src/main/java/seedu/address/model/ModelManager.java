package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.studyspot.StudySpot;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<StudySpot> filteredStudySpots;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudySpots = new FilteredList<>(this.addressBook.getStudySpotList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasStudySpot(StudySpot studySpot) {
        requireNonNull(studySpot);
        return addressBook.hasStudySpot(studySpot);
    }

    @Override
    public void deleteStudySpot(StudySpot target) {
        addressBook.removeStudySpot(target);
    }

    @Override
    public void addStudySpot(StudySpot studySpot) {
        addressBook.addStudySpot(studySpot);
        updateFilteredStudySpotList(PREDICATE_SHOW_ALL_STUDYSPOTS);
    }

    @Override
    public void setStudySpot(StudySpot target, StudySpot editedStudySpot) {
        requireAllNonNull(target, editedStudySpot);

        addressBook.setStudySpot(target, editedStudySpot);
    }

    //=========== Filtered StudySpot List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code StudySpot} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<StudySpot> getFilteredStudySpotList() {
        return filteredStudySpots;
    }

    @Override
    public void updateFilteredStudySpotList(Predicate<StudySpot> predicate) {
        requireNonNull(predicate);
        filteredStudySpots.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredStudySpots.equals(other.filteredStudySpots);
    }

}
