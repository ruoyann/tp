package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyStudyTracker;
import seedu.address.model.StudyTracker;
import seedu.address.model.studyspot.StudySpot;

/**
 * An Immutable StudyTracker that is serializable to JSON format.
 */
@JsonRootName(value = "studytracker")
class JsonSerializableStudyTracker {

    public static final String MESSAGE_DUPLICATE_STUDYSPOT = "StudySpots list contains duplicate study spot(s).";

    private final List<JsonAdaptedStudySpot> studySpots = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableStudyTracker} with the given study spots.
     */
    @JsonCreator
    public JsonSerializableStudyTracker(@JsonProperty("studySpots") List<JsonAdaptedStudySpot> studySpots) {
        this.studySpots.addAll(studySpots);
    }

    /**
     * Converts a given {@code ReadOnlyStudyTracker} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableStudyTracker}.
     */
    public JsonSerializableStudyTracker(ReadOnlyStudyTracker source) {
        studySpots.addAll(source.getStudySpotList().stream()
                .map(JsonAdaptedStudySpot::new).collect(Collectors.toList()));
    }

    /**
     * Converts this study tracker into the model's {@code StudyTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public StudyTracker toModelType() throws IllegalValueException {
        StudyTracker studyTracker = new StudyTracker();
        for (JsonAdaptedStudySpot jsonAdaptedStudySpot : studySpots) {
            StudySpot spot = jsonAdaptedStudySpot.toModelType();
            if (studyTracker.hasStudySpot(spot)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDYSPOT);
            }
            studyTracker.addStudySpot(spot);
            if (spot.isFavourite()) {
                studyTracker.addStudySpotToFavourites(spot);
            }
        }
        return studyTracker;
    }

}
