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
    public static final String MESSAGE_SUCCESS_DEFAULT = "Logged %1$S hours at %2$S!";
    public static final String MESSAGE_ONE_FLAG = "Please only use one flag!";
    public static final String MESSAGE_SUCCESS_RESET = "Reset hours at %1$S!";
    public static final String MESSAGE_SUCCESS_OVERRIDE = "Changed hours to %1$S at %2$S!";

    public static final String FLAG_RESET = "r";
    public static final String FLAG_OVERRIDE = "o";


    private final Name nameOfStudySpot;
    private final StudiedHours studiedHours;
    private final boolean isReset;
    private final boolean isOverride;


    public LogCommand(Name nameOfStudySpot, StudiedHours studiedHours, boolean isReset, boolean isOverride) {
        this.nameOfStudySpot = nameOfStudySpot;
        this.studiedHours = studiedHours;
        this.isReset = isReset;
        this.isOverride = isOverride;
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
        StudiedHours newHours;
        CommandResult result = null;
        if (isReset) {
            result = handleReset(model, studySpotToAddHours);
            return result;
        }
        if (isOverride) {
            result = handleOverride(model, studySpotToAddHours, studiedHours);
            return result;
        }
        newHours = initialHours.addHours(studiedHours);
        StudySpot updatedStudySpot = addHoursToStudySpot(studySpotToAddHours, newHours);

        model.setStudySpot(studySpotToAddHours, updatedStudySpot);
        model.updateFilteredStudySpotList(Model.PREDICATE_SHOW_ALL_STUDYSPOTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_DEFAULT, studiedHours, nameOfStudySpot));
    }

    private CommandResult handleReset(Model model, StudySpot studySpot) {
        StudiedHours newHours = new StudiedHours("0");
        StudySpot newStudySpot = addHoursToStudySpot(studySpot, newHours);
        model.setStudySpot(studySpot, newStudySpot);
        model.updateFilteredStudySpotList(Model.PREDICATE_SHOW_ALL_STUDYSPOTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_RESET, nameOfStudySpot));
    }

    private CommandResult handleOverride(Model model, StudySpot studySpot, StudiedHours studiedHours) {
        StudySpot newStudySpot = addHoursToStudySpot(studySpot, studiedHours);
        model.setStudySpot(studySpot, newStudySpot);
        model.updateFilteredStudySpotList(Model.PREDICATE_SHOW_ALL_STUDYSPOTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_OVERRIDE, studiedHours, nameOfStudySpot));
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
