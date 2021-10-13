package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_NAME_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudySpot;

/**
 * Deletes a study spot identified using it's displayed index from the study tracker.
 */
public class FavouriteCommand extends Command {

    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the study spot to favourites.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME (non-case sensitive) \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "tr3 ";

    public static final String MESSAGE_FAVOURITE_STUDYSPOT_SUCCESS = "Added study spot to favourites: %1$s";
    public static final String MESSAGE_FAVOURITE_REPEATSTUDYSPOT_FAIL =
            "Study spot provided is already a favourite: %1$s";

    private final Name name;

    public FavouriteCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<StudySpot> lastShownList = model.getFullList();

        boolean isPresent = false;
        StudySpot studySpotToFavourite = null;
        for (StudySpot current: lastShownList) {
            if (current.isSameName(name)) {
                studySpotToFavourite = current;
                isPresent = true;
                break;
            }
        }
        if (!isPresent) {
            throw new CommandException(MESSAGE_NAME_NOT_FOUND);
        }
        if (studySpotToFavourite.isFavourite()) {
            throw new CommandException(String.format(MESSAGE_FAVOURITE_REPEATSTUDYSPOT_FAIL,
                    studySpotToFavourite.getName()));
        }

        StudySpot updatedStudySpot = model.addStudySpotToFavourites(studySpotToFavourite);
        return new CommandResult(String.format(MESSAGE_FAVOURITE_STUDYSPOT_SUCCESS, updatedStudySpot.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && name.equals(((FavouriteCommand) other).name)); // state check
    }

    @Override
    public String toString() {
        return "Favourite";
    }

    @Override
    public String getCommandUsage() {
        return MESSAGE_USAGE;
    }
}
