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
 * A list of study spots that enforces uniqueness between its elements and does not allow nulls.
 * A study spot is considered unique by comparing using {@code StudySpot#isSameStudySpot(StudySpot)}.
 * As such, adding and updating of study spots uses StudySpot#isSameStudySpot(StudySpot) for equality
 * so as to ensure that the study spot being added or updated is unique in terms of identity in the UniqueStudySpotList.
 * However, the removal of a study spot uses StudySpot#equals(Object) so as to ensure that
 * the study spot with exactly the same fields will be removed.
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
     * Returns true if the list contains an equivalent study spot as the given argument.
     */
    public boolean contains(StudySpot toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameStudySpot);
    }

    /**
     * Adds a study spot to the list.
     * The study spot must not already exist in the list.
     */
    public void add(StudySpot toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStudySpotException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the study spot {@code target} in the list with {@code editedStudySpot}.
     * {@code target} must exist in the list.
     * The study spot identity of {@code editedStudySpot} must not be the same as
     * another existing study spot in the list.
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
     * Removes the equivalent study spot from the list.
     * The study spot must exist in the list.
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
     * Replaces the contents of this list with {@code studySpots}.
     * {@code studySpots} must not contain duplicate study spots.
     */
    public void setStudySpots(List<StudySpot> studySpots) {
        requireAllNonNull(studySpots);
        if (!studySpotsAreUnique(studySpots)) {
            throw new DuplicateStudySpotException();
        }

        internalList.setAll(studySpots);
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
     * Returns true if {@code studySpots} contains only unique study spots.
     */
    private boolean studySpotsAreUnique(List<StudySpot> studySpots) {
        for (int i = 0; i < studySpots.size() - 1; i++) {
            for (int j = i + 1; j < studySpots.size(); j++) {
                if (studySpots.get(i).isSameStudySpot(studySpots.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
