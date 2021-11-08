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
public class UnfavouriteCommand extends Command {

    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes the specified study spot (case insensitive) "
            + "from favourites.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME  \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NAME + "tr3 ";

    public static final String MESSAGE_UNFAVOURITE_STUDYSPOT_SUCCESS = "Removed study spot from favourites: %1$s";
    public static final String MESSAGE_UNFAVOURITE_REPEATSTUDYSPOT_FAIL =
            "Study spot provided is not a favourite: %1$s";

    private static final Logger logger = LogsCenter.getLogger(UnfavouriteCommand.class);

    private final Name name;

    public UnfavouriteCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing Unfavourite Command...");
        requireNonNull(model);

        StudySpot studySpotToUnfavourite = model.findStudySpot(name);
        if (studySpotToUnfavourite == null) {
            throw new CommandException(MESSAGE_INVALID_NAME);
        }

        if (!studySpotToUnfavourite.isFavourite()) {
            throw new CommandException(String.format(MESSAGE_UNFAVOURITE_REPEATSTUDYSPOT_FAIL,
                    studySpotToUnfavourite.getName()));
        }

        StudySpot updatedStudySpot = model.removeStudySpotFromFavourites(studySpotToUnfavourite);
        return new CommandResult(String.format(MESSAGE_UNFAVOURITE_STUDYSPOT_SUCCESS, updatedStudySpot.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCommand // instanceof handles nulls
                && name.equals(((UnfavouriteCommand) other).name)); // state check
    }

}
