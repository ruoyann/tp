package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

public class VersionedStudyTracker extends StudyTracker {
    private final List<ReadOnlyStudyTracker> studyTrackerStateList;
    private int currentStatePointer;

    public VersionedStudyTracker(ReadOnlyStudyTracker toBeCopied) {
        super(toBeCopied);
        studyTrackerStateList = new ArrayList<>();
        studyTrackerStateList.add(toBeCopied);
        currentStatePointer = 0;
    }

    public void commit() {
        studyTrackerStateList.add(new StudyTracker(this));
        currentStatePointer++;
    }

    public void undo() {

    }

    public void redo() {}
}
