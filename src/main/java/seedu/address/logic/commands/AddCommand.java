package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyspot.StudySpot;

/**
 * Adds a StudySpot to the Study Tracker.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a study spot to the study tracker.\n"
            + "You cannot add a study spot with the same name, different casing!\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME* "
            + PREFIX_RATING + "RATING* "
            + PREFIX_OPERATING_HOURS + "OPERATING HOURS "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_HOURS + "STUDIED HOURS "
            + "" + PREFIX_TAG + "TAG... "
            + "" + PREFIX_AMENITY + "AMENITY...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "COM1 "
            + PREFIX_RATING + "5 "
            + PREFIX_OPERATING_HOURS + "0900-2200, 0900-2200 "
            + PREFIX_HOURS + "5 "
            + PREFIX_ADDRESS + "SoC "
            + PREFIX_TAG + "cold "
            + PREFIX_TAG + "quiet "
            + PREFIX_AMENITY + "wifi";

    public static final String MESSAGE_SUCCESS = "New study spot added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDYSPOT = "This study spot already exists in the study tracker";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    private final StudySpot toAdd;

    /**
     * Creates an AddCommand to add the specified {@code StudySpot}
     */
    public AddCommand(StudySpot studySpot) {
        requireNonNull(studySpot);
        toAdd = studySpot;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing Add Command...");
        requireNonNull(model);

        if (model.hasStudySpot(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDYSPOT);
        }

        model.addStudySpot(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
