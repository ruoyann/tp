package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.*;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class LogCommand extends Command {
    public static final String COMMAND_WORD = "log";

    public static final String MESSAGE_USAGE = "FIll later";

    public static final String MESSAGE_SUCCESS = "Logged %1$S hours at %2$S!";

    private final Name nameOfStudySpot;
    private final StudiedHours studiedHours;

    public LogCommand(Name nameOfStudySpot, StudiedHours studiedHours) {
        this.nameOfStudySpot = nameOfStudySpot;
        this.studiedHours = studiedHours;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<StudySpot> lastShownList = model.getFullList();
        boolean isPresent = false;
        StudySpot studySpotToAddHours = null;
        for (StudySpot current: lastShownList) {
            if (current.isSameName(nameOfStudySpot)) {
                studySpotToAddHours = current;
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME);
        }

        StudiedHours initialHours = studySpotToAddHours.getStudiedHours();
        StudiedHours hoursAfterAddition = initialHours.addHours(studiedHours);
        StudySpot updatedStudySpot = addHoursToStudySpot(studySpotToAddHours, hoursAfterAddition);

        model.setStudySpot(studySpotToAddHours, updatedStudySpot);
        model.updateFilteredStudySpotList(Model.PREDICATE_SHOW_ALL_STUDYSPOTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, studiedHours, studySpotToAddHours));
    }

    private static StudySpot addHoursToStudySpot(StudySpot studySpotToAddHours,
                                                 StudiedHours hoursAfterAddition) {
        assert studySpotToAddHours != null;
        Name name= studySpotToAddHours.getName();
        Rating rating = studySpotToAddHours.getRating();
        OperatingHours operatingHours = studySpotToAddHours.getOperatingHours();
        Address address = studySpotToAddHours.getAddress();
        StudiedHours studiedHours = hoursAfterAddition;
        Set<Tag> tags = studySpotToAddHours.getTags();
        Set<Amenity> amenities = studySpotToAddHours.getAmenities();
        Favourite favourite = studySpotToAddHours.getFavourite();

        return new StudySpot(name, rating, operatingHours, address, studiedHours, favourite, tags, amenities);
    }
}
