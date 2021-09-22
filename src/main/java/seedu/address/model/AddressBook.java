package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.studyspot.UniqueStudySpotList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameStudySpot comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueStudySpotList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueStudySpotList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the StudySpots in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setStudySpots(List<StudySpot> persons) {
        this.persons.setStudySpots(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setStudySpots(newData.getStudySpotList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasStudySpot(StudySpot person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addStudySpot(StudySpot p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedStudySpot}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedStudySpot} must not
     * be the same as another existing person in the address book.
     */
    public void setStudySpot(StudySpot target, StudySpot editedStudySpot) {
        requireNonNull(editedStudySpot);

        persons.setStudySpot(target, editedStudySpot);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeStudySpot(StudySpot key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<StudySpot> getStudySpotList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
