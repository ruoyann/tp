package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.studyspot.StudySpot;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<StudySpot> PREDICATE_SHOW_ALL_STUDYSPOTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasStudySpot(StudySpot person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteStudySpot(StudySpot target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addStudySpot(StudySpot person);

    /**
     * Replaces the given person {@code target} with {@code editedStudySpot}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedStudySpot} must not be the same as another existing person in the address book.
     */
    void setStudySpot(StudySpot target, StudySpot editedStudySpot);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<StudySpot> getFilteredStudySpotList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudySpotList(Predicate<StudySpot> predicate);
}
