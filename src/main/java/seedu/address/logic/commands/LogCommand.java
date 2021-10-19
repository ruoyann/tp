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
    private final String flag;
    private final boolean isFlagPresent;

    public LogCommand(Name nameOfStudySpot, StudiedHours studiedHours) {
        this.nameOfStudySpot = nameOfStudySpot;
        this.studiedHours = studiedHours;
        this.flag = null;
        this.isFlagPresent = false;
    }

    public LogCommand(Name nameOfStudySpot, StudiedHours studiedHours, String flag) {
        this.nameOfStudySpot = nameOfStudySpot;
        this.studiedHours = studiedHours;
        this.flag = flag;
        if (flag == null) {
            isFlagPresent = false;
        } else {
            isFlagPresent = true;
        }
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
        StudiedHours newHours = null;
        CommandResult result = null;
        if (!isFlagPresent) {
            newHours = initialHours.addHours(studiedHours);
            result = new CommandResult(String.format(MESSAGE_SUCCESS_DEFAULT, studiedHours, studySpotToAddHours.getName()));
        } else {
            if (flag.equals("o")) {
                newHours = studiedHours;
                result = new CommandResult(String.format(MESSAGE_SUCCESS_OVERRIDE, studiedHours, studySpotToAddHours.getName()));
            }

            if (flag.equals("r")) {
                newHours = new StudiedHours("0");
                result = new CommandResult(String.format(MESSAGE_SUCCESS_RESET, studySpotToAddHours.getName()));
            }
        }
        assert newHours != null;
        assert result != null;
        StudySpot updatedStudySpot = addHoursToStudySpot(studySpotToAddHours, newHours);

        model.setStudySpot(studySpotToAddHours, updatedStudySpot);
        model.updateFilteredStudySpotList(Model.PREDICATE_SHOW_ALL_STUDYSPOTS);
        return result;
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
