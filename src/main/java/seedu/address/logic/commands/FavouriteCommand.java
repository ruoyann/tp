package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.StudySpot;

/**
 * Deletes a study spot identified using it's displayed index from the study tracker.
 */
public class FavouriteCommand extends Command {

    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the study spot (case insensitive) "
            + "to favourites.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME* \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "tr3 ";

    public static final String MESSAGE_FAVOURITE_STUDYSPOT_SUCCESS = "Added study spot to favourites: %1$s";
    public static final String MESSAGE_FAVOURITE_REPEATSTUDYSPOT_FAIL =
            "Study spot provided is already a favourite: %1$s";

    private static final Logger logger = LogsCenter.getLogger(FavouriteCommand.class);

    private final Name name;

    public FavouriteCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing Favourite Command...");
        requireNonNull(model);

        StudySpot studySpotToFavourite = model.findStudySpot(name);
        if (studySpotToFavourite == null) {
            throw new CommandException(MESSAGE_INVALID_NAME);
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


}
