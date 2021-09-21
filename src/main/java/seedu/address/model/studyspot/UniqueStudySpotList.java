package seedu.address.model.studyspot;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.studyspot.exceptions.DuplicateStudySpotException;
import seedu.address.model.studyspot.exceptions.StudySpotNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code StudySpot#isSameStudySpot(StudySpot)}. As such, adding and updating of
 * persons uses StudySpot#isSameStudySpot(StudySpot) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniqueStudySpotList. However, the removal of a person uses StudySpot#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see StudySpot#isSameStudySpot(StudySpot)
 */
public class UniqueStudySpotList implements Iterable<StudySpot> {

    private final ObservableList<StudySpot> internalList = FXCollections.observableArrayList();
    private final ObservableList<StudySpot> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(StudySpot toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStudySpot);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(StudySpot toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudySpotException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedStudySpot}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedStudySpot} must not be the same as another existing person in the list.
     */
    public void setStudySpot(StudySpot target, StudySpot editedStudySpot) {
        requireAllNonNull(target, editedStudySpot);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StudySpotNotFoundException();
        }

        if (!target.isSameStudySpot(editedStudySpot) && contains(editedStudySpot)) {
            throw new DuplicateStudySpotException();
        }

        internalList.set(index, editedStudySpot);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(StudySpot toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StudySpotNotFoundException();
        }
    }

    public void setStudySpots(UniqueStudySpotList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setStudySpots(List<StudySpot> persons) {
        requireAllNonNull(persons);
        if (!personsAreUnique(persons)) {
            throw new DuplicateStudySpotException();
        }

        internalList.setAll(persons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<StudySpot> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<StudySpot> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueStudySpotList // instanceof handles nulls
                        && internalList.equals(((UniqueStudySpotList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<StudySpot> persons) {
        for (int i = 0; i < persons.size() - 1; i++) {
            for (int j = i + 1; j < persons.size(); j++) {
                if (persons.get(i).isSameStudySpot(persons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
