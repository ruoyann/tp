package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Favourite;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;


/**
 * Deals with commands that change the number of hours studied at a location.
 */
public class LogCommand extends Command {
    public static final String COMMAND_WORD = "log";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds to the studied hours at the "
            + "specified study spot (case-insensitive).\n"
            + "The -o flag will override the studied hours to the value provided\n"
            + "The -r will reset the studied hours to 0\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME* (case-insensitive) "
            + PREFIX_HOURS + "ADDED_HOURS* (required if -r is not input) "
            + "[-r] [-o]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "Starbucks" + " " + PREFIX_HOURS + "4 ";
    public static final String MESSAGE_SUCCESS_DEFAULT = "Logged %1$S hours at %2$s!";
    public static final String MESSAGE_ONE_FLAG = "Please only use one flag!";
    public static final String MESSAGE_SUCCESS_RESET = "Reset hours at %1$s!";
    public static final String MESSAGE_SUCCESS_OVERRIDE = "Changed hours to %1$S at %2$s!";

    public static final String FLAG_RESET = "r";
    public static final String FLAG_OVERRIDE = "o";


    private final Name nameOfStudySpot;
    private final StudiedHours studiedHours;
    private final boolean isReset;
    private final boolean isOverride;


    /**
     * @param nameOfStudySpot name of study spot to add hours to
     * @param studiedHours number of hours studied
     * @param isReset resets the number of hours to zero
     * @param isOverride changes the number of hours to studiedHours
     */
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
        CommandResult result;
        if (isReset) {
            result = handleReset(model, studySpotToAddHours);
            return result;
        }
        if (isOverride) {
            result = handleOverride(model, studySpotToAddHours, studiedHours);
            return result;
        }
        try {
            newHours = initialHours.addHours(studiedHours);
            StudySpot updatedStudySpot = addHoursToStudySpot(studySpotToAddHours, newHours);


            model.setStudySpot(studySpotToAddHours, updatedStudySpot);
            model.updateFilteredStudySpotList(Model.PREDICATE_SHOW_ALL_STUDYSPOTS);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS_DEFAULT, studiedHours, studySpotToAddHours.getName()),
                true, false, false
        );
    }

    private CommandResult handleReset(Model model, StudySpot studySpot) {
        StudiedHours newHours = new StudiedHours("0");
        StudySpot newStudySpot = addHoursToStudySpot(studySpot, newHours);
        model.setStudySpot(studySpot, newStudySpot);
        model.updateFilteredStudySpotList(Model.PREDICATE_SHOW_ALL_STUDYSPOTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_RESET, studySpot.getName()),
                true, false, false);
    }

    private CommandResult handleOverride(Model model, StudySpot studySpot, StudiedHours studiedHours) {
        StudySpot newStudySpot = addHoursToStudySpot(studySpot, studiedHours);
        model.setStudySpot(studySpot, newStudySpot);
        model.updateFilteredStudySpotList(Model.PREDICATE_SHOW_ALL_STUDYSPOTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS_OVERRIDE, studiedHours, studySpot.getName()),
                true, false, false);
    }

    private static StudySpot addHoursToStudySpot(StudySpot studySpotToAddHours,
                                                 StudiedHours hoursAfterAddition) {
        assert studySpotToAddHours != null;
        Name name = studySpotToAddHours.getName();
        Rating rating = studySpotToAddHours.getRating();
        OperatingHours operatingHours = studySpotToAddHours.getOperatingHours();
        Address address = studySpotToAddHours.getAddress();
        StudiedHours studiedHours = hoursAfterAddition;
        Set<Tag> tags = studySpotToAddHours.getTags();
        Set<Amenity> amenities = studySpotToAddHours.getAmenities();
        Favourite favourite = studySpotToAddHours.getFavourite();

        return new StudySpot(name, rating, operatingHours, address, studiedHours, favourite, tags, amenities);
    }

    public Name getName() {
        return this.nameOfStudySpot;
    }

    public StudiedHours getStudiedHours() {
        return this.studiedHours;
    }

    public boolean getIsReset() {
        return this.isReset;
    }

    public boolean getIsOverride() {
        return this.isOverride;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        } else {
            if (other instanceof LogCommand) {
                LogCommand e = (LogCommand) other;
                return getName().equals(e.getName())
                        && getStudiedHours().toString().equals(e.getStudiedHours().toString())
                        && getIsReset() == (e.getIsReset())
                        && getIsOverride() == (e.getIsOverride());
            }
        }
        return false;
    }
}
